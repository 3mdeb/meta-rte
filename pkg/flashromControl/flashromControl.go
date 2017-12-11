package flashromControl

import (
	"bytes"
	"errors"
	"fmt"
	"os"
	"os/exec"
	"regexp"
)

type FlashromControl struct {
	flashromBin string
	config      Config
	cmd         *exec.Cmd
	out         bytes.Buffer
}

type Config struct {
	Speed int
}

var conf = Config{
	Speed: 16000,
}

var ctrl = FlashromControl{
	flashromBin: "flashrom",
	cmd:         nil,
	config:      conf,
}

const (
	matchReading   = "Reading old flash chip contents"
	matchErasing   = "Erasing and writing flash chip"
	matchVerifying = "Verifying flash"
)

type State int

const (
	StateIdle State = iota
	StatePreparing
	StateReading
	StateErasing
	StateVerifying
	StateDone
	StateError
)

func (s State) String() string {
	switch s {
	case StateIdle:
		return "idle"
	case StatePreparing:
		return "preparing"
	case StateReading:
		return "reading"
	case StateErasing:
		return "erasing and flashing"
	case StateVerifying:
		return "verifying"
	case StateDone:
		return "done"
	case StateError:
		return "error"
	default:
		return "unknown"
	}
}

const programmerArg = "linux_spi:dev=/dev/spidev1.0,spispeed="

var errFlashFileDoesNotExist = errors.New("rom file doesn't exist")
var errProcessAlreadyStarted = errors.New("process already started")

func New(flashromPath string) (*FlashromControl, error) {
	if flashromPath != "" {
		ctrl.flashromBin = flashromPath
	}

	path, err := exec.LookPath(ctrl.flashromBin)
	if err != nil {
		return nil, err
	}

	ctrl.flashromBin = path

	return &ctrl, nil
}

func (*FlashromControl) Start(upgradeFile string, force bool) error {
	var err error

	programmer := fmt.Sprintf("%s%d", programmerArg, ctrl.config.Speed)

	_, err = os.Stat(upgradeFile)
	if os.IsNotExist(err) {
		return errFlashFileDoesNotExist
	}

	if ctrl.cmd != nil && ctrl.cmd.ProcessState == nil {
		return errProcessAlreadyStarted
	}

	ctrl.cmd = exec.Command(ctrl.flashromBin, "-w", upgradeFile, "-p", programmer)
	ctrl.cmd.Stdout = &ctrl.out

	err = ctrl.cmd.Start()
	if err != nil {
		return err
	}

	go func() {
		ctrl.cmd.Wait()
		fmt.Println(ctrl.out.String())
	}()

	return nil
}

func (*FlashromControl) GetState() State {

	s := StateIdle

	if ctrl.cmd == nil {
		return s
	}

	s = StatePreparing

	buf := ctrl.out.Bytes()
	if len(buf) == 0 {
		return s
	}

	if m, _ := regexp.Match(matchReading, buf); m {
		s = StateReading
	}
	if m, _ := regexp.Match(matchErasing, buf); m {
		s = StateErasing
	}
	if m, _ := regexp.Match(matchVerifying, buf); m {
		s = StateVerifying
	}

	if ctrl.cmd.ProcessState != nil {
		if ctrl.cmd.ProcessState.Success() {
			s = StateDone
		} else {
			s = StateError
		}
	}

	return s
}

func (*FlashromControl) SetConfig(newConf Config) {
	ctrl.config = newConf
}

func (*FlashromControl) GetConfig() Config {
	return ctrl.config
}

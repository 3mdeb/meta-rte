package flashromControl

import (
	"bytes"
	"errors"
	"fmt"
	"os"
	"os/exec"
	"regexp"
)

type Flashrom struct {
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

var ctrl = Flashrom{
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

var ErrFlashFileDoesNotExist = errors.New("rom file doesn't exist")
var ErrProcessAlreadyStarted = errors.New("process already started")

func New(flashromPath string) (*Flashrom, error) {
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

func (*Flashrom) Start(upgradeFile string) error {
	var err error

	programmer := fmt.Sprintf("%s%d", programmerArg, ctrl.config.Speed)

	_, err = os.Stat(upgradeFile)
	if os.IsNotExist(err) {
		return ErrFlashFileDoesNotExist
	}

	if ctrl.cmd != nil && ctrl.cmd.ProcessState == nil {
		return ErrProcessAlreadyStarted
	}

	ctrl.cmd = exec.Command(ctrl.flashromBin, "-w", upgradeFile, "-p", programmer)
	ctrl.out.Reset()
	ctrl.cmd.Stdout = &ctrl.out

	err = ctrl.cmd.Start()
	if err != nil {
		return err
	}

	go func() {
		ctrl.cmd.Wait()
	}()

	return nil
}

func (*Flashrom) GetState() State {

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

func (*Flashrom) SetConfig(newConf Config) {
	ctrl.config = newConf
}

func (*Flashrom) GetConfig() Config {
	return ctrl.config
}

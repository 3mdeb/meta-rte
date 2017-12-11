package gpioControl

import (
	"3mdeb/RteCtrl/pkg/config"
	"fmt"
	"io/ioutil"
	"os"
)

type pin struct {
	sysNum      uint
	description string
}

type Gpio struct {
	sysGpioPath string
	gpios       map[int]pin
}

var ctrl = Gpio{
	sysGpioPath: "/sys/class/gpio",
}

func exportGpio(sysGpio uint) error {
	target := fmt.Sprintf("%s/export", ctrl.sysGpioPath)
	d := fmt.Sprintf("%d\n", sysGpio)
	err := ioutil.WriteFile(target, []byte(d), 0644)
	return err
}

func checkGpio(sysGpio uint) bool {
	target := fmt.Sprintf("%s/gpio%d", ctrl.sysGpioPath, sysGpio)
	if _, err := os.Stat(target); err == nil {
		return true
	}

	return false
}

func New(gpioPath string, cfg []config.PinConfig) (*Gpio, error) {
	if gpioPath != "" {
		ctrl.sysGpioPath = gpioPath
	}
	var err error

	ctrl.gpios = make(map[int]pin)

	for _, val := range cfg {
		newPin := pin{sysNum: val.SysGpio, description: val.Description}
		ctrl.gpios[val.ID] = newPin

		if !checkGpio(ctrl.gpios[val.ID].sysNum) {
			err = exportGpio(ctrl.gpios[val.ID].sysNum)
			if err != nil {
				return nil, err
			}
		}

		err = ctrl.SetDirection(val.ID, val.Direction)
		if err != nil {
			return nil, err
		}

		err = ctrl.SetState(val.ID, val.InitValue)
		if err != nil {
			return nil, err
		}

	}

	return &ctrl, nil
}

func (ctrl *Gpio) SetDirection(id int, direction string) error {
	target := fmt.Sprintf("%s/gpio%d/direction", ctrl.sysGpioPath, ctrl.gpios[id].sysNum)
	var d string
	switch direction {
	case "out", "o":
		d = "out\n"
	case "in", "i":
		fallthrough
	default:
		d = "in\n"
	}

	err := ioutil.WriteFile(target, []byte(d), 0644)

	return err
}

func (ctrl *Gpio) GetDirection(id int) (string, error) {
	target := fmt.Sprintf("%s/gpio%d/direction", ctrl.sysGpioPath, ctrl.gpios[id].sysNum)
	dat, err := ioutil.ReadFile(target)
	if err != nil {
		return "", err
	}

	var val string

	_, err = fmt.Sscanln(string(dat), &val)
	if err != nil {
		return "", err
	}

	return val, err
}

func (ctrl *Gpio) SetState(id int, state uint) error {
	dir, err := ctrl.GetDirection(id)
	if err != nil {
		return err
	}

	if dir == "in" {
		return nil
	}

	target := fmt.Sprintf("%s/gpio%d/value", ctrl.sysGpioPath, ctrl.gpios[id].sysNum)
	var d string
	if state == 0 {
		d = "0\n"
	} else {
		d = "1\n"
	}

	err = ioutil.WriteFile(target, []byte(d), 0644)

	return err
}

func (ctrl *Gpio) GetState(id int) (uint, error) {
	target := fmt.Sprintf("%s/gpio%d/value", ctrl.sysGpioPath, ctrl.gpios[id].sysNum)
	dat, err := ioutil.ReadFile(target)
	if err != nil {
		return 0, err
	}

	var val int

	_, err = fmt.Sscanln(string(dat), &val)
	if err != nil {
		return 0, err
	}

	if val == 1 {
		return 1, nil
	}

	return 0, nil
}

func (ctrl *Gpio) GetNumberOfGpios() int {
	return len(ctrl.gpios)
}

func (ctrl *Gpio) GetDescription(id int) string {
	return ctrl.gpios[id].description
}

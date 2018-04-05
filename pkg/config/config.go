package config

import (
	"encoding/json"
	"io/ioutil"
)

type PinConfig struct {
	ID          int    `json:"id"`
	Description string `json:"description"`
	Direction   string `json:"direction"`
	InitValue   uint   `json:"init_value"`
	SysGpio     uint   `json:"sys_gpio"`
}

type CmdIds struct {
	Power int `json:"power"`
	Reset int `json:"reset"`
	Relay int `json:"relay"`
}

type Config struct {
	ServerAddress string      `json:"server_address"`
	WebDir        string      `json:"web_directory"`
	FlashromBin   string      `json:"flashrom_bin"`
	SysGpioPath   string      `json:"sys_gpio_path"`
	CommandIDs    CmdIds      `json:"cmd_id"`
	Gpios         []PinConfig `json:"gpios"`
}

var cfg Config

func NewConfig(filePath string) (*Config, error) {

	configFile, err := ioutil.ReadFile(filePath)
	if err != nil {
		return nil, err
	}

	err = json.Unmarshal(configFile, &cfg)
	if err != nil {
		return nil, err
	}

	return &cfg, nil
}

package main

import (
	"3mdeb/RteCtrl/pkg/config"
	"3mdeb/RteCtrl/pkg/flashromControl"
	"3mdeb/RteCtrl/pkg/gpioControl"
	"3mdeb/RteCtrl/pkg/restServer"
	"flag"
	"log"
)

func main() {
	configFilePath := flag.String("c", "/etc/rteCtrl/rteCtrl.cfg", "path to config file")
	flag.Parse()

	log.Println("reading", *configFilePath)

	cfg, err := config.NewConfig(*configFilePath)
	if err != nil {
		log.Fatal(err)
	}

	gpio, err := gpioControl.New(cfg.SysGpioPath, cfg.Gpios)
	if err != nil {
		log.Fatal(err)
	}

	flash, err := flashromControl.New(cfg.FlashromBin)
	if err != nil {
		log.Fatal(err)
	}

	log.Println("starting server on", cfg.ServerAddress)
	restServer.Start(cfg.ServerAddress, cfg.WebDir, gpio, flash)
}

package main

import (
	"3mdeb/RteCtrl/pkg/config"
	"3mdeb/RteCtrl/pkg/flashromControl"
	"3mdeb/RteCtrl/pkg/gpioControl"
	"3mdeb/RteCtrl/pkg/restServer"
	"flag"
	"log"
	"os"
	"time"
)

// Flags
var (
	configFilePath  = flag.String("c", "/etc/RteCtrl/RteCtrl.cfg", "path to config file")
	toggleRelayFlag = flag.Bool("rel", false, "toggle relay")
	powerOffFlag    = flag.Bool("poff", false, "power off the DUT")
	powerOnFlag     = flag.Bool("pon", false, "power on the DUT")
	resetFlag       = flag.Bool("reset", false, "reset DUT")
)

func pressButton(g *gpioControl.Gpio, id int, t time.Duration) {
	g.SetDirection(id, "out")
	g.SetState(id, 1)
	time.Sleep(t)
	g.SetState(id, 0)
}

func toggleButton(g *gpioControl.Gpio, id int) {
	g.SetDirection(id, "out")
	val, _ := g.GetState(id)
	if val == 0 {
		g.SetState(id, 1)
	} else {
		g.SetState(id, 0)
	}
}

func main() {

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

	switch {
	case *powerOnFlag:
		log.Println("powering on")
		pressButton(gpio, cfg.CommandIDs.Power, 100*time.Millisecond)
		os.Exit(0)
	case *powerOffFlag:
		log.Println("powering off")
		pressButton(gpio, cfg.CommandIDs.Power, 5*time.Second)
		os.Exit(0)
	case *resetFlag:
		log.Println("resetting")
		pressButton(gpio, cfg.CommandIDs.Reset, 100*time.Millisecond)
		os.Exit(0)
	case *toggleRelayFlag:
		log.Println("toggling relay")
		toggleButton(gpio, cfg.CommandIDs.Relay)
		os.Exit(0)
	}

	flash, err := flashromControl.New(cfg.FlashromBin)
	if err != nil {
		log.Fatal(err)
	}

	log.Println("starting server on", cfg.ServerAddress)
	restServer.Start(cfg.ServerAddress, cfg.WebDir, gpio, flash)
}

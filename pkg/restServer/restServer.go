package restServer

import (
	"3mdeb/RteCtrl/pkg/flashromControl"
	"3mdeb/RteCtrl/pkg/gpioControl"
	"crypto/md5"
	"encoding/json"
	"fmt"
	"io"
	"io/ioutil"
	"log"
	"net/http"
	"os"
	"reflect"
	"runtime"
	"strconv"
	"strings"
	"time"

	"github.com/gorilla/mux"
)

const restPrefix = "/api/v1"
const romFilename = "rte_romfile.rom"

var gpio *gpioControl.Gpio
var flash *flashromControl.Flashrom
var tempRomFile string

type errorStatus struct {
	Error string `json:"error"`
}

/* error status strings */
const (
	errCantReadGpioStates      = "can't read GPIO states"
	errGpioIDNotFound          = "id not found"
	errBadParams               = "bad parameters"
	errCantUploadFile          = "can't upload file"
	errNoFileUploaded          = "no file uploaded"
	errChecksumMismatch        = "checksum mismatch"
	errNoFlashingPerformed     = "no flashing operation performed"
	errFlashVerificationFailed = "flash verification failed"
	errInternalError           = "internal error"
)

type gpioState struct {
	ID          int    `json:"id"`
	State       uint   `json:"state"`
	Description string `json:"description"`
	Direction   string `json:"direction"`
}

type gpioStateRequest struct {
	State     uint   `json:"state"`
	Direction string `json:"direction"`
	Time      uint   `json:"time"`
}

type status struct {
	Status  string `json:"status"`
	Percent uint   `json:"percent,omitempty"`
}

const (
	statStarting           = "starting"
	statPreparing          = "preparing"
	statReadingOldContents = "reading old contents"
	statErasingAndWriting  = "erasing flash and writing"
	statVerifying          = "verifying"
	statDone               = "done"
	statOk                 = "ok"
)

type flasherConfig struct {
	Speed int `json:"speed"`
}

type fileDetails struct {
	Checksum string `json:"file_md5"`
	Size     int64  `json:"file_size,omitempty"`
}

var currentFileDetails = fileDetails{
	Checksum: "",
	Size:     0,
}

func listAllGpios(w http.ResponseWriter, r *http.Request) {
	gpios := make([]gpioState, gpio.GetNumberOfGpios())
	var err error
	out := json.NewEncoder(w)

	for i := range gpios {
		gpios[i].ID = i
		gpios[i].Description = gpio.GetDescription(i)
		gpios[i].Direction, err = gpio.GetDirection(i)
		if err != nil {
			break
		}
		gpios[i].State, err = gpio.GetState(i)
		if err != nil {
			break
		}
	}

	if err != nil {
		log.Println(err)
		w.WriteHeader(http.StatusNotFound)
		out.Encode(errorStatus{Error: errCantReadGpioStates})
		return
	}

	out.Encode(gpios)
}

func getGpioState(w http.ResponseWriter, r *http.Request) {
	out := json.NewEncoder(w)

	params := mux.Vars(r)

	log.Println("getGpioState:", params["id"])

	id, err := strconv.Atoi(params["id"])
	if err != nil {
		log.Println(err)
		w.WriteHeader(http.StatusNotFound)
		out.Encode(errorStatus{Error: errGpioIDNotFound})
		return
	}

	var g gpioState

	g.ID = id
	g.Description = gpio.GetDescription(id)
	g.Direction, err = gpio.GetDirection(id)
	if err != nil {
		log.Println(err)
		w.WriteHeader(http.StatusNotFound)
		out.Encode(errorStatus{Error: errCantReadGpioStates})
		return
	}
	g.State, err = gpio.GetState(id)
	if err != nil {
		log.Println(err)
		w.WriteHeader(http.StatusNotFound)
		out.Encode(errorStatus{Error: errCantReadGpioStates})
		return
	}

	out.Encode(g)
}

func setGpioState(w http.ResponseWriter, r *http.Request) {
	out := json.NewEncoder(w)
	params := mux.Vars(r)

	log.Println("setGpioState:", params["id"])

	id, err := strconv.Atoi(params["id"])
	if err != nil {
		log.Println(err)
		w.WriteHeader(http.StatusNotFound)
		out.Encode(errorStatus{Error: errGpioIDNotFound})
		return
	}

	var g gpioStateRequest

	in := json.NewDecoder(r.Body)
	err = in.Decode(&g)
	if err != nil {
		log.Println(err)
		w.WriteHeader(http.StatusForbidden)
		out.Encode(errorStatus{Error: errBadParams})
		return
	}

	if g.Direction != "" {
		err = gpio.SetDirection(id, g.Direction)
		if err != nil {
			log.Println(err)
			w.WriteHeader(http.StatusNotFound)
			out.Encode(errorStatus{Error: errCantReadGpioStates})
			return
		}
	}

	err = gpio.SetState(id, g.State)
	if err != nil {
		log.Println(err)
		w.WriteHeader(http.StatusNotFound)
		out.Encode(errorStatus{Error: errCantReadGpioStates})
		return
	}

	if g.Time != 0 {
		timer := time.NewTimer(time.Second * time.Duration(g.Time))
		log.Println("setGpioState: timer started")
		go func() {
			<-timer.C
			log.Println("setGpioState: timer fired")
			if g.State == 0 {
				gpio.SetState(id, 1)
			} else {
				gpio.SetState(id, 0)
			}
		}()
	}
	getGpioState(w, r)
}

func uploadFile(w http.ResponseWriter, r *http.Request) {
	out := json.NewEncoder(w)
	rf, _, err := r.FormFile("file")
	if err != nil {
		log.Println(err)
		w.WriteHeader(http.StatusNotFound)
		out.Encode(errorStatus{Error: errCantUploadFile})
		return
	}
	defer rf.Close()

	f, err := os.OpenFile(tempRomFile, os.O_CREATE|os.O_RDWR, 0666)
	if err != nil {
		log.Println(err)
		w.WriteHeader(http.StatusNotFound)
		out.Encode(errorStatus{Error: errCantUploadFile})
		return
	}
	defer f.Close()

	h := md5.New()
	mw := io.MultiWriter(f, h)

	written, err := io.Copy(mw, rf)
	if err != nil {
		log.Println(err)
		w.WriteHeader(http.StatusNotFound)
		out.Encode(errorStatus{Error: errCantUploadFile})
		return
	}

	currentFileDetails.Size = written
	currentFileDetails.Checksum = fmt.Sprintf("%x", h.Sum(nil))

	out.Encode(currentFileDetails)
}

func getFileDetails(w http.ResponseWriter, r *http.Request) {
	out := json.NewEncoder(w)

	if currentFileDetails.Checksum == "" {
		log.Println("no file uploaded")
		w.WriteHeader(http.StatusNotFound)
		out.Encode(errorStatus{Error: errNoFileUploaded})
		return
	}

	out.Encode(currentFileDetails)
}

func removeFile(w http.ResponseWriter, r *http.Request) {
	out := json.NewEncoder(w)

	if currentFileDetails.Checksum == "" {
		log.Println("no file uploaded")
		w.WriteHeader(http.StatusNotFound)
		out.Encode(errorStatus{Error: errNoFileUploaded})
		return
	}

	os.Remove(tempRomFile)
	currentFileDetails.Checksum = ""
	currentFileDetails.Size = 0

	out.Encode(status{Status: statOk})
}

func getFlasherConfig(w http.ResponseWriter, r *http.Request) {
	out := json.NewEncoder(w)
	cfg := flash.GetConfig()

	out.Encode(flasherConfig{Speed: cfg.Speed})
}

func setFlasherConfig(w http.ResponseWriter, r *http.Request) {
	out := json.NewEncoder(w)
	in := json.NewDecoder(r.Body)
	var cfg flasherConfig

	err := in.Decode(&cfg)
	if err != nil {
		log.Println(err)
		w.WriteHeader(http.StatusForbidden)
		out.Encode(errorStatus{Error: errBadParams})
		return
	}

	flash.SetConfig(flashromControl.Config{Speed: cfg.Speed})

	getFlasherConfig(w, r)
}

func startFlashing(w http.ResponseWriter, r *http.Request) {
	out := json.NewEncoder(w)
	in := json.NewDecoder(r.Body)

	if currentFileDetails.Checksum == "" {
		log.Println("no file uploaded")
		w.WriteHeader(http.StatusNotFound)
		out.Encode(errorStatus{Error: errNoFileUploaded})
		return
	}

	var fDetails fileDetails
	err := in.Decode(&fDetails)
	if err != nil || fDetails.Checksum == "" {
		log.Println(err)
		w.WriteHeader(http.StatusForbidden)
		out.Encode(errorStatus{Error: errBadParams})
		return
	}

	if fDetails.Checksum != currentFileDetails.Checksum {
		log.Println(err)
		w.WriteHeader(http.StatusForbidden)
		out.Encode(errorStatus{Error: errChecksumMismatch})
		return
	}

	err = flash.Start(tempRomFile)
	if err == flashromControl.ErrProcessAlreadyStarted {
		getFlashingState(w, r)
		return
	} else if err != nil {
		log.Println(err)
		w.WriteHeader(http.StatusInternalServerError)
		out.Encode(errorStatus{Error: errInternalError})
		return
	}

	out.Encode(status{Status: statStarting, Percent: 1})
}

func getFlashingState(w http.ResponseWriter, r *http.Request) {
	out := json.NewEncoder(w)

	state := flash.GetState()

	var s status

	switch state {
	case flashromControl.StateIdle:
		out.Encode(errorStatus{Error: errNoFlashingPerformed})
		return
	case flashromControl.StatePreparing:
		s.Status = statPreparing
		s.Percent = 20
	case flashromControl.StateReading:
		s.Status = statReadingOldContents
		s.Percent = 40
	case flashromControl.StateErasing:
		s.Status = statErasingAndWriting
		s.Percent = 60
	case flashromControl.StateVerifying:
		s.Status = statVerifying
		s.Percent = 80
	case flashromControl.StateDone:
		s.Status = statDone
		s.Percent = 100
	case flashromControl.StateError:
		out.Encode(errorStatus{Error: errFlashVerificationFailed})
		return
	default:
		out.Encode(errorStatus{Error: errBadParams})
		return
	}

	out.Encode(s)
}

func logFunc(f func(http.ResponseWriter, *http.Request)) func(http.ResponseWriter, *http.Request) {

	return func(w http.ResponseWriter, r *http.Request) {
		fName := runtime.FuncForPC(reflect.ValueOf(f).Pointer()).Name()
		log.Printf("request [%s]: %s", r.Method, strings.Split(fName, ".")[1])
		f(w, r)
	}
}

func Start(address, webDir string, g *gpioControl.Gpio, f *flashromControl.Flashrom) {

	gpio = g
	flash = f

	tempRomFile = fmt.Sprintf("%s/%s", os.TempDir(), romFilename)

	fs := http.FileServer(http.Dir("web"))

	files, err := ioutil.ReadDir("web")
	if err != nil {
		log.Fatal(err)
	}

	router := mux.NewRouter()
	router.Handle("/", fs).Methods("GET")
	for _, file := range files {
		router.Handle("/"+file.Name(), fs).Methods("GET")
	}

	router.HandleFunc(restPrefix+"/gpio", logFunc(listAllGpios)).Methods("GET")
	router.HandleFunc(restPrefix+"/gpio/{id}", logFunc(getGpioState)).Methods("GET")
	router.HandleFunc(restPrefix+"/gpio/{id}", logFunc(setGpioState)).Methods("PATCH")

	router.HandleFunc(restPrefix+"/flash/file", logFunc(uploadFile)).Methods("PUT")
	router.HandleFunc(restPrefix+"/flash/file", logFunc(getFileDetails)).Methods("GET")
	router.HandleFunc(restPrefix+"/flash/file", logFunc(removeFile)).Methods("DELETE")

	router.HandleFunc(restPrefix+"/flash/config", logFunc(getFlasherConfig)).Methods("GET")
	router.HandleFunc(restPrefix+"/flash/config", logFunc(setFlasherConfig)).Methods("PATCH")
	router.HandleFunc(restPrefix+"/flash", logFunc(startFlashing)).Methods("PUT")
	router.HandleFunc(restPrefix+"/flash", logFunc(getFlashingState)).Methods("GET")

	http.ListenAndServe(address, router)
}

package restServer

import (
	"3mdeb/RteCtrl/pkg/gpioControl"
	"encoding/json"
	"log"
	"net/http"
	"strconv"
	"time"

	"github.com/gorilla/mux"
)

const restPrefix = "/api/v1"

var gpio *gpioControl.GpioControl

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

type flashingStatus struct {
	Status  string `json:"status"`
	Percent uint   `json:"percent"`
}

const (
	statStarting           = "starting"
	statPreparing          = "preparing"
	statReadingOldContents = "reading old contents"
	statErasingAndWriting  = "erasing flash and writing"
	statVerifying          = "verifying"
	statDone               = "done"
)

type flasherConfig struct {
	Speed uint `json:"speed"`
}

type fileDetails struct {
	Checksum string `json:"file_md5"`
	Size     uint   `json:"file_size,omitempty"`
}

func listAllGpios(w http.ResponseWriter, r *http.Request) {
	gpios := make([]gpioState, gpio.GetNumberOfGpios())
	var err error
	out := json.NewEncoder(w)

	log.Println("listAllGpios")

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
		w.WriteHeader(404)
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
		w.WriteHeader(404)
		out.Encode(errorStatus{Error: errGpioIDNotFound})
		return
	}

	var g gpioState

	g.ID = id
	g.Description = gpio.GetDescription(id)
	g.Direction, err = gpio.GetDirection(id)
	if err != nil {
		log.Println(err)
		w.WriteHeader(404)
		out.Encode(errorStatus{Error: errCantReadGpioStates})
		return
	}
	g.State, err = gpio.GetState(id)
	if err != nil {
		log.Println(err)
		w.WriteHeader(404)
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
		w.WriteHeader(404)
		out.Encode(errorStatus{Error: errGpioIDNotFound})
		return
	}

	var g gpioStateRequest

	in := json.NewDecoder(r.Body)
	err = in.Decode(&g)
	if err != nil {
		log.Println(err)
		w.WriteHeader(403)
		out.Encode(errorStatus{Error: errBadParams})
		return
	}

	if g.Direction != "" {
		err = gpio.SetDirection(id, g.Direction)
		if err != nil {
			log.Println(err)
			w.WriteHeader(404)
			out.Encode(errorStatus{Error: errCantReadGpioStates})
			return
		}
	}

	err = gpio.SetState(id, g.State)
	if err != nil {
		log.Println(err)
		w.WriteHeader(404)
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

}

func getFileDetails(w http.ResponseWriter, r *http.Request) {

}

func removeFile(w http.ResponseWriter, r *http.Request) {

}

func getFlasherConfig(w http.ResponseWriter, r *http.Request) {

}

func setFlasherConfig(w http.ResponseWriter, r *http.Request) {

}

func startFlashing(w http.ResponseWriter, r *http.Request) {

}

func getFlashingState(w http.ResponseWriter, r *http.Request) {

}

func Start(g *gpioControl.GpioControl) {

	gpio = g

	router := mux.NewRouter()
	router.HandleFunc(restPrefix+"/gpio", listAllGpios).Methods("GET")
	router.HandleFunc(restPrefix+"/gpio/{id}", getGpioState).Methods("GET")
	router.HandleFunc(restPrefix+"/gpio/{id}", setGpioState).Methods("PATCH")

	router.HandleFunc(restPrefix+"/flash/file", uploadFile).Methods("PUT")
	router.HandleFunc(restPrefix+"/flash/file", getFileDetails).Methods("GET")
	router.HandleFunc(restPrefix+"/flash/file", removeFile).Methods("DELETE")

	router.HandleFunc(restPrefix+"/flash/config", getFlasherConfig).Methods("GET")
	router.HandleFunc(restPrefix+"/flash/config", setFlasherConfig).Methods("PATCH")
	router.HandleFunc(restPrefix+"/flash", startFlashing).Methods("PUT")
	router.HandleFunc(restPrefix+"/flash", getFlashingState).Methods("GET")

	http.ListenAndServe(":8000", router)
}

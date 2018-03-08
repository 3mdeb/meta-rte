RTE controller
==============

## General architecture

```
+------+     +-----+       +-------+
|      |     |     |       |       |
| rest |     | web |       | shell |
|      |     |     |       |       |
+------+--+--+-----+       +---+---+
          |                    |
          |                    |
      +---v---+                |
      |       |                |
      |  mux  |                |
      |       |                |
      +---+---+                |
          |                    |
    +-----v----+               |
    |          |       +-------v------+
    |  HTTP    |       |              |
    |  server  +------->  controller  |
    |          |       |              |
    +----------+       +-------+------+
                               |
               +--------------------------------+
               |               |                |
           +---v--+     +------v----+      +----v--+
           |      |     |           |      |       |
           | file +.....+ flashrom  |      | GPIO  |
           |      |     |           |      |       |
           +------+     +-----------+      +-------+
```

## Dependencies

* gorilla mux

```
go get github.com/gorilla/mux
```

## Building

```
mkdir -p ~/go/src/3mdeb && cd ~/go/src/3mdeb
git clone git@gitlab.com:3mdeb_rte/RteCtrl.git
cd RteCtrl
```

> if $GOPATH is set, change `~/go` to `$GOPATH`

For host system:

```
go build
```

For arm system:

```
GOARCH=arm go build
```

## Usage

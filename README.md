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

For host system:

```
go build
```

For arm system:

```
GOARCH=arm go build
```

## Usage

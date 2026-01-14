# Application name
TARGET = aesdsocket

# Source files
SRC = aesdsocket.c

CFLAGS ?= -Wall -Werror -g
LDFLAGS ?=
LDLIBS = -lpthread
CC ?= $(CROSS_COMPILE)gcc

.PHONY: all default
all: $(TARGET)
default: $(TARGET)

$(TARGET): $(SRC)
	$(CC) $(CFLAGS) $< -o $@ $(LDFLAGS) $(LDLIBS)

.PHONY: clean
clean:
	rm -f $(TARGET) *.o

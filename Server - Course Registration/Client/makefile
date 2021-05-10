CFLAGS:=-c -Wall -Weffc++ -g -std=c++11 -Iinclude
LDFLAGS:=-lboost_system -pthread

all: BGRSclient
	g++ -o bin/BGRSclient bin/connectionHandler.o bin/MessagesEncoderDecoder.o bin/BGRSClient.o bin/ReadKeyBoard.o bin/ReadSocket.o $(LDFLAGS) 

BGRSclient: bin/connectionHandler.o bin/MessagesEncoderDecoder.o bin/BGRSClient.o bin/ReadKeyBoard.o bin/ReadSocket.o
	
bin/connectionHandler.o: src/connectionHandler.cpp
	g++ $(CFLAGS) -o bin/connectionHandler.o src/connectionHandler.cpp

bin/MessagesEncoderDecoder.o: src/MessagesEncoderDecoder.cpp
	g++ $(CFLAGS) -o bin/MessagesEncoderDecoder.o src/MessagesEncoderDecoder.cpp

bin/BGRSClient.o: src/BGRSClient.cpp
	g++ $(CFLAGS) -o bin/BGRSClient.o src/BGRSClient.cpp

bin/ReadKeyBoard.o: src/ReadKeyBoard.cpp
	g++ $(CFLAGS) -o bin/ReadKeyBoard.o src/ReadKeyBoard.cpp

bin/ReadSocket.o: src/ReadSocket.cpp
	g++ $(CFLAGS) -o bin/ReadSocket.o src/ReadSocket.cpp

.PHONY: clean
clean:
	rm -f bin/*

#include <iostream>
#include <boost/scoped_array.hpp>
#include "../include/connectionHandler.h"
#include "../include/ReadSocket.h"

ReadSocket::ReadSocket(ConnectionHandler& c, std::mutex& mutex, std::condition_variable& conditionVariable): connectionHandler(c), mtx(mutex), cv(conditionVariable){}

void ReadSocket::run() {
    while (!connectionHandler.isDone()) {
        std::string answer;
        connectionHandler.getLine(answer);
        if(answer.compare("ACK 4")==0){
            connectionHandler.finish();
            cv.notify_all();
        }
    }
    std::cout << "ReadSocket - Disconnected. Exiting...\n" << std::endl;
}
#include <iostream>
#include <boost/scoped_array.hpp>
#include "../include/connectionHandler.h"
#include "../include/ReadKeyBoard.h"
#include <mutex>
#include <condition_variable>

ReadKeyBoard::ReadKeyBoard(ConnectionHandler& c, std::mutex& mutex,std::condition_variable& conditionVariable): connectionHandler(c), mtx(mutex), cv(conditionVariable){}

void ReadKeyBoard::run() {
    while (!connectionHandler.isDone()) {
        const short bufsize = 1<<10;
        char buf[bufsize];
        std::cin.getline(buf, bufsize);
        std::string line(buf);
        if(line=="LOGOUT"){
            connectionHandler.sendLine(line);
            std::unique_lock<std::mutex> lck(mtx);
            cv.wait(lck);
        }
        else
            connectionHandler.sendLine(line);
    }
    std::cout << "ReadKeyBoard - Disconnected. Exiting...\n" << std::endl;
}
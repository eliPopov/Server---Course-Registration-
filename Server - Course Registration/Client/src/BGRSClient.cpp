#include <iostream>
#include <thread>
#include <boost/scoped_array.hpp>
#include "../include/connectionHandler.h"
#include "../include/ReadKeyBoard.h"
#include "../include/ReadSocket.h"
#include <mutex>
#include <condition_variable>
std::mutex mutex;
std::condition_variable conditionVariable;
int main (int argc, char *argv[]) {
    if (argc < 3) {
        std::cerr << "Usage: " << argv[0] << " host port" << std::endl << std::endl;
        return -1;
    }
    std::string host = argv[1];
    short port = atoi(argv[2]);
    ConnectionHandler connectionHandler(host, port);
    if (!connectionHandler.connect()) {
        std::cerr << "Cannot connect to " << host << ":" << port << std::endl;
        return 1;
    }
    ReadKeyBoard rkb(connectionHandler, mutex, conditionVariable);
    ReadSocket rs(connectionHandler, mutex, conditionVariable);
    std::thread th1(&ReadKeyBoard::run, &rkb);
    std::thread th2(&ReadSocket::run, &rs);
    th1.join();
    th2.detach();
    return 0;
}
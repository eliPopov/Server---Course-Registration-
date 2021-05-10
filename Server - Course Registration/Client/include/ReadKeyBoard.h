#ifndef BOOST_ECHO_CLIENT_READKEYBOARD_H
#define BOOST_ECHO_CLIENT_READKEYBOARD_H
#include <iostream>
#include <mutex>
#include <condition_variable>
class ReadKeyBoard {
private:
    ConnectionHandler& connectionHandler;
    std::mutex& mtx;
    std::condition_variable& cv;
public:
    ReadKeyBoard(ConnectionHandler& c, std::mutex& mutex, std::condition_variable& cv);
    void run();
};

#endif //BOOST_ECHO_CLIENT_READKEYBOARD_H

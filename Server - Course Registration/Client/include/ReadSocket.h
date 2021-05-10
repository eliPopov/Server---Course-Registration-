#ifndef BOOST_ECHO_CLIENT_READSOCKET_H
#define BOOST_ECHO_CLIENT_READSOCKET_H
#include <mutex>
#include <condition_variable>
class ReadSocket {
private:
    ConnectionHandler& connectionHandler;
    std::mutex& mtx;
    std::condition_variable& cv;
public:
    ReadSocket(ConnectionHandler& c, std::mutex& mutex, std::condition_variable& cv);
    void run();
};

#endif //BOOST_ECHO_CLIENT_READSOCKET_H

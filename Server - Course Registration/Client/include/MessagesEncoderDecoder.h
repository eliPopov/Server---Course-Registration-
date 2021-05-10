#ifndef BOOST_ECHO_CLIENT_MESSAGESENCODERDECODER_H
#define BOOST_ECHO_CLIENT_MESSAGESENCODERDECODER_H

#include <vector>
#include "connectionHandler.h"

using namespace std;

class MessagesEncoderDecoder {
private:
    std::vector<char> message;
public:
    MessagesEncoderDecoder();
    const vector<char>& encode(string message);
    bool decode(ConnectionHandler& handler, string& line);
};


#endif //BOOST_ECHO_CLIENT_MESSAGESENCODERDECODER_H

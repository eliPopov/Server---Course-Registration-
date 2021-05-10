#include "../include/MessagesEncoderDecoder.h"

MessagesEncoderDecoder::MessagesEncoderDecoder():message() {}

const vector<char> & MessagesEncoderDecoder::encode(string line) {
    int idx = line.find_first_of(' ');
    string commandName = line.substr(0, idx);
    string commandData = line.substr(idx + 1, line.length());
    if(commandName=="ADMINREG"){
        message.push_back('0');
        message.push_back('1');
        for(char c:commandData){
            if(c!=' ')
                message.push_back(c);
            else
                message.push_back('\0');
        }
        message.push_back('\0');
    }
    else if(commandName=="STUDENTREG"){
        message.push_back('0');
        message.push_back('2');
        for(char c:commandData){
            if(c!=' ')
                message.push_back(c);
            else
                message.push_back('\0');
        }
        message.push_back('\0');
    }
    else if(commandName=="LOGIN"){
        message.push_back('0');
        message.push_back('3');
        for(char c:commandData){
            if(c!=' ')
                message.push_back(c);
            else
                message.push_back('\0');
        }
        message.push_back('\0');
    }
    else if(commandName=="LOGOUT") {
        message.push_back('0');
        message.push_back('4');
    }
    else if(commandName=="COURSEREG") {
        message.push_back('0');
        message.push_back('5');
        std::stringstream convert(commandData);
        short courseNum = 0;
        convert >> courseNum;
        message.push_back((courseNum >> 8) & 0xFF);
        message.push_back(courseNum & 0xFF);
    }
    else if(commandName=="KDAMCHECK") {
        message.push_back('0');
        message.push_back('6');
        std::stringstream convert(commandData);
        short courseNum = 0;
        convert >> courseNum;
        message.push_back((courseNum >> 8) & 0xFF);
        message.push_back(courseNum & 0xFF);
    }
    else if(commandName=="COURSESTAT") {
        message.push_back('0');
        message.push_back('7');
        std::stringstream convert(commandData);
        short courseNum = 0;
        convert >> courseNum;
        message.push_back((courseNum >> 8) & 0xFF);
        message.push_back(courseNum & 0xFF);
    }
    else if(commandName=="STUDENTSTAT") {
        message.push_back('0');
        message.push_back('8');
        for(char c:commandData)
            message.push_back(c);
        message.push_back('\0');
    }
    else if(commandName=="ISREGISTERED") {
        message.push_back('0');
        message.push_back('9');
        std::stringstream convert(commandData);
        short courseNum = 0;
        convert >> courseNum;
        message.push_back((courseNum >> 8) & 0xFF);
        message.push_back(courseNum & 0xFF);
    }
    else if(commandName=="UNREGISTER") {
        message.push_back('1');
        message.push_back('0');
        std::stringstream convert(commandData);
        short courseNum = 0;
        convert >> courseNum;
        message.push_back((courseNum >> 8) & 0xFF);
        message.push_back(courseNum & 0xFF);
    }
    else if(commandName=="MYCOURSES") {
        message.push_back('1');
        message.push_back('1');
    }
    else
        throw std::invalid_argument("Wrong command - "+commandName);
    return message;
}

bool MessagesEncoderDecoder::decode(ConnectionHandler &handler, string &line) {
    std::vector<char> opCodeBytes;
    opCodeBytes.push_back('0');
    if (!handler.getBytes(&opCodeBytes[0], 2))
        return false;
    short opCode = (opCodeBytes[0] - '0') * 10 + (opCodeBytes[1] - '0');
    std::vector<char> messageBytes;
    messageBytes.push_back('0');
    if (!handler.getBytes(&messageBytes[0], 2))
        return false;
    short messageShort = (messageBytes[0] - '0') * 10 + (messageBytes[1] - '0');
    if (opCode == 12) {
        line = "ACK " + std::to_string(messageShort);
        if (messageShort == 4) {
            handler.finish();
            std::cout << line << std::endl;
            return true;
        } else if ((messageShort > 5 && messageShort < 9) || messageShort == 11) {
            line+="\n";
            char add = '0';
            handler.getBytes(&add, 1);
            while (add != ']') {
                line += add;
                if (!handler.getBytes(&add, 1)) return false;
            }
            line = line + add + "\n";
        }
        else if(messageShort==9) {
            char add = '0';
            handler.getBytes(&add, 1);
            while (add != 'D') {
                line += add;
                if (!handler.getBytes(&add, 1)) return false;
            }
            line = line + add + "\n";
        }
    } else if (opCode == 13) {
        line = "ERROR " + std::to_string(messageShort) + "\n";
    }
    else {
        line = "ERROR encoding line - opCode = " + std::to_string(opCode);
        return false;
    }
    std::cout << line << std::endl;
    return true;
}

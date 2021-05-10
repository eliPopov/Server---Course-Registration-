package bgu.spl.net.impl.api.Protocols;

import bgu.spl.net.impl.api.Messages.LogInMessage;
import bgu.spl.net.impl.api.Messages.Message;
import bgu.spl.net.impl.api.Messages.ResponseMessage;
import bgu.spl.net.impl.obj.Database;
import bgu.spl.net.impl.obj.User;

public class LoginProtocol implements MessagingProtocol<Message>{
    ResponseMessage response = new ResponseMessage();
    Database database = Database.getInstance();
    @Override
    public Message process(Message msg){
        LogInMessage currMsg = (LogInMessage) msg;
        User msgCaller = msg.getUser();
        response.setMsgCode(msg.getOpCode());
        if(msgCaller!=null|| !database.hasRegisteredToServer(currMsg.getUserName()) || database.getUser(currMsg.getUserName()).isLogged() ||
                !database.getUserPassword(currMsg.getUserName()).equals(currMsg.getPassword())) {
            //checking: 1. is the user(that sends the message) already logged in? 2. has the user registered to the server?
            //3. is the username already logged in the database? 4. does the password match?
            response.setOpCode(13);
        }
        else {
            response.setOpCode(12);
            database.LoginUser(currMsg.getUserName());
        }
        return response;
    }

    @Override
    public boolean shouldTerminate() {return false;}
}

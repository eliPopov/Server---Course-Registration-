package bgu.spl.net.impl.api.Protocols;

import bgu.spl.net.impl.api.Messages.Message;
import bgu.spl.net.impl.api.Messages.ResponseMessage;
import bgu.spl.net.impl.obj.Database;
import bgu.spl.net.impl.obj.User;

public class LogoutProtocol implements MessagingProtocol<Message>{
    ResponseMessage response = new ResponseMessage();
    Database database = Database.getInstance();
    boolean shouldTerminate = false;
    @Override
    public Message process(Message msg) {
        response.setMsgCode(msg.getOpCode());
        User msgCaller = (msg.getUser());
        if(!database.getUser(msgCaller.getUserName()).isLogged())
            response.setOpCode(13);
        response.setOpCode(12);
        database.LogoutUser(msgCaller.getUserName());
        shouldTerminate = true;
        return response;
    }

    @Override
    public boolean shouldTerminate() {
        return shouldTerminate;
    }
}

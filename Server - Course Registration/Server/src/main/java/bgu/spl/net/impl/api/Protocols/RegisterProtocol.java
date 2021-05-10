package bgu.spl.net.impl.api.Protocols;

import bgu.spl.net.impl.api.Messages.Message;
import bgu.spl.net.impl.api.Messages.ResponseMessage;
import bgu.spl.net.impl.obj.Admin;
import bgu.spl.net.impl.obj.Database;
import bgu.spl.net.impl.obj.User;

public class RegisterProtocol implements MessagingProtocol<Message>{
    ResponseMessage response = new ResponseMessage();
    Database database = Database.getInstance();
    @Override
    public Message process(Message msg) {
        response.setMsgCode(msg.getOpCode());
        User msgCaller = msg.getUser();
        if(database.hasRegisteredToServer(msgCaller.getUserName()))
            response.setOpCode(13);
        else {
            response.setOpCode(12);
            if(msgCaller.getClass() == Admin.class) database.addAdmin(msgCaller.getUserName(), msgCaller.getPassword());
            else database.addStudent(msgCaller.getUserName(), msgCaller.getPassword());
        }
        return response;
    }

    @Override
    public boolean shouldTerminate() { return false; }
}

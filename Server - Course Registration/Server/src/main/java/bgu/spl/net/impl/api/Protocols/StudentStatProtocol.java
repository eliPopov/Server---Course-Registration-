package bgu.spl.net.impl.api.Protocols;

import bgu.spl.net.impl.api.Messages.Message;
import bgu.spl.net.impl.api.Messages.ResponseMessage;
import bgu.spl.net.impl.api.Messages.StudentStatMessage;
import bgu.spl.net.impl.obj.Admin;
import bgu.spl.net.impl.obj.Database;
import bgu.spl.net.impl.obj.User;


public class StudentStatProtocol implements MessagingProtocol<Message>{
    Database database = Database.getInstance();
    ResponseMessage response = new ResponseMessage();
    @Override
    public Message process(Message msg) {
        StudentStatMessage currMessage = (StudentStatMessage) msg;
        response.setMsgCode(msg.getOpCode());
        User msgCaller = msg.getUser();
        if(!database.hasRegisteredToServer(msgCaller.getUserName()) || !msgCaller.isLogged() ||
                msgCaller.getClass() != Admin.class || !database.hasRegisteredToServer(currMessage.getUserName())){
            //checking: has the client(sending the message) registered to the server? 2. is the client logged in? 3. is the client a student?
            // 4. has the student in question registered?
            response.setOpCode(13);
            return  response;
        }
        response.addStatInfo(database.studentStat(((StudentStatMessage) msg).getUserName()));
        response.setOpCode(12);
        return response;
    }

    @Override
    public boolean shouldTerminate() {
        return false;
    }
}

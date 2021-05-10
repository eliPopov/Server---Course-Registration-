package bgu.spl.net.impl.api.Protocols;

import bgu.spl.net.impl.api.Messages.Message;
import bgu.spl.net.impl.api.Messages.ResponseMessage;
import bgu.spl.net.impl.obj.Admin;
import bgu.spl.net.impl.obj.Database;
import bgu.spl.net.impl.obj.User;

public class MyCoursesProtocol implements MessagingProtocol<Message>{
    ResponseMessage response = new ResponseMessage();
    Database database = Database.getInstance();
    @Override
    public Message process(Message msg) {
        response.setMsgCode(msg.getOpCode());
        User msgCaller = msg.getUser();
        if(msgCaller.getClass() == Admin.class){
            response.setOpCode(13);
            return response;
        }
        response.setOpCode(12);
        response.addInfo(database.getUserCourses(msgCaller.getUserName()).toString());
        return response;
    }

    @Override
    public boolean shouldTerminate() {
        return false;
    }
}

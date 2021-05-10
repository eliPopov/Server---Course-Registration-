package bgu.spl.net.impl.api.Protocols;

import bgu.spl.net.impl.api.Messages.Message;
import bgu.spl.net.impl.api.Messages.ResponseMessage;
import bgu.spl.net.impl.api.Messages.courseNumberMessage;
import bgu.spl.net.impl.obj.Admin;
import bgu.spl.net.impl.obj.Database;
import bgu.spl.net.impl.obj.User;

public class isRegisteredProtocol implements MessagingProtocol<Message>{
    ResponseMessage response = new ResponseMessage();
    Database database = Database.getInstance();
    @Override
    public Message process(Message msg) {
        courseNumberMessage currMessage = (courseNumberMessage) msg;
        response.setMsgCode(msg.getOpCode());
        User msgCaller = msg.getUser();
        if(msgCaller.getClass() == Admin.class || !database.doesCourseExists(currMessage.getCourseNumber())){
            //checking: is the user a student? does the course exist?
            response.setOpCode(13);
            return response;
        }
        response.setOpCode(12);
        String answer;
        if(database.isRegisteredToCourse(msgCaller.getUserName(), currMessage.getCourseNumber()))
            answer = "REGISTERED";
        else
            answer = "NOT REGISTERED";
        response.addInfo(answer);
        return response;
    }

    @Override
    public boolean shouldTerminate() {
        return false;
    }
}

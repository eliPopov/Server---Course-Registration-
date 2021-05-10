package bgu.spl.net.impl.api.Protocols;

import bgu.spl.net.impl.api.Messages.Message;
import bgu.spl.net.impl.api.Messages.ResponseMessage;
import bgu.spl.net.impl.api.Messages.courseNumberMessage;
import bgu.spl.net.impl.obj.Admin;
import bgu.spl.net.impl.obj.Database;
import bgu.spl.net.impl.obj.Student;
import bgu.spl.net.impl.obj.User;

public class UnregisterProtocol implements MessagingProtocol<Message>{
    ResponseMessage response = new ResponseMessage();
    Database database = Database.getInstance();
    @Override
    public Message process(Message msg) {
        response.setMsgCode(msg.getOpCode());
        courseNumberMessage currMessage = (courseNumberMessage) msg;
        User msgCaller = msg.getUser();
        if(msgCaller.getClass() == Admin.class || !database.doesCourseExists(currMessage.getCourseNumber())
        || !database.isRegisteredToCourse(msgCaller.getUserName(), ((courseNumberMessage) msg).getCourseNumber())){
            //checking: 1. is the user a student? 2. does the course exist? 3. has the student registered to the course?
            response.setOpCode(13);
            return response;
        }
        response.setOpCode(12);
        database.UnregisterStudent(currMessage.getCourseNumber(), (Student)msgCaller);
        return response;
    }

    @Override
    public boolean shouldTerminate() {
        return false;
    }
}

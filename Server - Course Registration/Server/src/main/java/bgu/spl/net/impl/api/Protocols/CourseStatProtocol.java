package bgu.spl.net.impl.api.Protocols;

import bgu.spl.net.impl.api.Messages.Message;
import bgu.spl.net.impl.api.Messages.ResponseMessage;
import bgu.spl.net.impl.api.Messages.courseNumberMessage;
import bgu.spl.net.impl.obj.Admin;
import bgu.spl.net.impl.obj.Database;
import bgu.spl.net.impl.obj.User;

public class CourseStatProtocol implements MessagingProtocol<Message>{
    Database database = Database.getInstance();
    ResponseMessage response = new ResponseMessage();
    @Override
    public Message process(Message msg) {
        courseNumberMessage currMessage = (courseNumberMessage) msg;
        response.setMsgCode(msg.getOpCode());
        User msgCaller = msg.getUser();
        if(msgCaller.getClass() != Admin.class || !database.doesCourseExists(currMessage.getCourseNumber())) {
            //checking: 1. is the user an admin? 2. does the course exist?
            response.setOpCode(13);
            return response;
        }
        response.setOpCode(12);
        response.addStatInfo(database.courseStat(currMessage.getCourseNumber()));
        return response;
    }

    @Override
    public boolean shouldTerminate() { return false; }
}

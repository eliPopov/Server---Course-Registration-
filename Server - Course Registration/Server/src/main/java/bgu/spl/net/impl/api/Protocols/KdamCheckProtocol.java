package bgu.spl.net.impl.api.Protocols;

import bgu.spl.net.impl.api.Messages.Message;
import bgu.spl.net.impl.api.Messages.ResponseMessage;
import bgu.spl.net.impl.api.Messages.courseNumberMessage;
import bgu.spl.net.impl.obj.Admin;
import bgu.spl.net.impl.obj.Database;
import bgu.spl.net.impl.obj.User;
import java.util.ArrayList;
import java.util.Comparator;

public class KdamCheckProtocol implements MessagingProtocol<Message>{
    ResponseMessage response = new ResponseMessage();
    Database database = Database.getInstance();
    @Override
    public Message process(Message msg) {
        response.setMsgCode(msg.getOpCode());
        courseNumberMessage currCourse = (courseNumberMessage) msg;
        User msgCaller = msg.getUser();
        if(msgCaller.getClass() == Admin.class || !database.doesCourseExists(currCourse.getCourseNumber())) {
            //checking: 1. is the client a student? 2. does the course exist?
            response.setOpCode(13);
            return response;
        }
        response.setOpCode(12);
        ArrayList<Short> kdam = database.getKdam(currCourse.getCourseNumber());
        kdam.sort(new Comparator<Short>() {
            @Override
            public int compare(Short o1, Short o2) {
                return database.getCourse(o1).getLine()-database.getCourse(o2).getLine();
            }
        });
        response.addInfo(kdam.toString());
        return response;
    }

    @Override
    public boolean shouldTerminate() {
        return false;
    }
}

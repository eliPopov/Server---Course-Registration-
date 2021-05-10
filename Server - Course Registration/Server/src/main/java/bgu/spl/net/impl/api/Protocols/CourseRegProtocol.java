package bgu.spl.net.impl.api.Protocols;

import bgu.spl.net.impl.api.Messages.Message;
import bgu.spl.net.impl.api.Messages.ResponseMessage;
import bgu.spl.net.impl.api.Messages.courseNumberMessage;
import bgu.spl.net.impl.obj.Admin;
import bgu.spl.net.impl.obj.Database;
import bgu.spl.net.impl.obj.Student;
import bgu.spl.net.impl.obj.User;

public class CourseRegProtocol implements MessagingProtocol<Message>{
    ResponseMessage response = new ResponseMessage();
    Database database = Database.getInstance();
    @Override
    public Message process(Message msg) {
        response.setMsgCode(msg.getOpCode());
        courseNumberMessage courseMsg = (courseNumberMessage) msg;
        short courseNum = courseMsg.getCourseNumber();
        User msgCaller = msg.getUser();
        if(msgCaller.getClass() == Admin.class || !database.doesCourseExists(courseNum) ||
                !(database.getCourse(courseNum).freeSeats()>0) || !database.hasAllKdams(msgCaller, courseNum) ||
                !(database.RegisterStudent(courseNum, (Student)msgCaller))) {
            //checking: 1. is the client a student? 2. does the course exist? 3. does the course have free seats?
            // 4. does the student have all the kdam courses? 5. is it possible to register the student(did the student already registered to the course?
            response.setOpCode(13);
            return response;
        }
        response.setOpCode(12);
        return response;
    }

    @Override
    public boolean shouldTerminate() {
        return false;
    }
}

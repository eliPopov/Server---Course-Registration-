package bgu.spl.net.impl.api.Messages;

import bgu.spl.net.impl.obj.Student;
import bgu.spl.net.impl.obj.User;

public class StudentRegMessage extends RegisterLoginMessage{

    private final Short opCode;
    private Student student;

    public StudentRegMessage(Short opCode) {
        this.opCode = opCode;
        this.student=new Student();
    }

    public void setUserName(String userName) {
        student.setUserName(userName);
    }

    public void setPassword(String password) {
        student.setPassword(password);
    }

    public Short getOpCode() {
        return opCode;
    }

    @Override
    public User getUser() {return student;}

    @Override
    public void setUser(User user) { }

    public String getUserName() {
        return student.getUserName();
    }
}
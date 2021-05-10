package bgu.spl.net.impl.api.Messages;

import bgu.spl.net.impl.obj.User;

public class courseNumberMessage implements Message{
    private Short opCode;
    private Short courseNumber;
    private User user;
    public courseNumberMessage(Short opCode){
        this.opCode=opCode;
    }

    public void setCourseNumber(Short courseNumber) {
        this.courseNumber = courseNumber;
    }

    public Short getOpCode() {
        return opCode;
    }

    @Override
    public User getUser() {return user;}

    @Override
    public void setUser(User user) {
        this.user=user;
    }

    public Short getCourseNumber() {
        return courseNumber;
    }
}

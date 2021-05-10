package bgu.spl.net.impl.api.Messages;

import bgu.spl.net.impl.obj.*;

public class StudentStatMessage implements Message{
    private final Short opCode;
    private String userName;
    private User user;

    public StudentStatMessage(Short opCode){
        this.opCode = opCode;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Short getOpCode() {
        return opCode;
    }

    @Override
    public User getUser() {return user; }

    @Override
    public void setUser(User user) {this.user=user; }

    public String getUserName() {
        return userName;
    }
}

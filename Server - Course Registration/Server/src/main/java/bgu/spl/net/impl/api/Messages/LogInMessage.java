package bgu.spl.net.impl.api.Messages;

import bgu.spl.net.impl.obj.User;

public class LogInMessage extends RegisterLoginMessage{

    private final Short opCode;
    private User user;
    private String userName;
    private String password;

    public LogInMessage(Short opCode) {
        this.opCode = opCode;
    }

    public Short getOpCode() {
        return opCode;
    }

    public void setUserName(String userName) { this.userName=userName; }

    public void setPassword(String password) {this.password=password; }

    @Override
    public User getUser() {return user; }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    public String getUserName() { return userName; }
    public String getPassword(){ return password; }
}
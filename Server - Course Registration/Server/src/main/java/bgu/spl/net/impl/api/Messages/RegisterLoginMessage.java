package bgu.spl.net.impl.api.Messages;

import bgu.spl.net.impl.obj.User;

public abstract class RegisterLoginMessage implements Message{
    private short opCode;
    private User user;

    @Override
    public Short getOpCode() {
        return null;
    }

    @Override
    public User getUser() {
        return null;
    }

    @Override
    public void setUser(User user) {

    }

    public abstract String getUserName();

    public abstract void setUserName(String userName);

    public abstract void setPassword(String password);
}

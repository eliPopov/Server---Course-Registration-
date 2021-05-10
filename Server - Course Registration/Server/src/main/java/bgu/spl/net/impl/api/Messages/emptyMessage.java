package bgu.spl.net.impl.api.Messages;

import bgu.spl.net.impl.obj.User;

public class emptyMessage implements Message{
    private Short opCode;
    User user;
    public emptyMessage(Short opCode){
        this.opCode=opCode;
    }

    public Short getOpCode() {
        return opCode;
    }

    @Override
    public User getUser() {
        return user;
    }
    public void setUser(User user){this.user=user;}
}

package bgu.spl.net.impl.api.Messages;

import bgu.spl.net.impl.obj.User;

public interface Message {
    Short opCode = 0;
    public Short getOpCode();

    User getUser();
    void setUser(User user);
}

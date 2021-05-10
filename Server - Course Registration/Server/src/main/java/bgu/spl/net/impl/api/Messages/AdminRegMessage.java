package bgu.spl.net.impl.api.Messages;

import bgu.spl.net.impl.obj.Admin;
import bgu.spl.net.impl.obj.Database;
import bgu.spl.net.impl.obj.User;

public class AdminRegMessage extends RegisterLoginMessage{

    private final Short opCode;
    private Admin admin;

    public AdminRegMessage(Short opCode) {
        this.opCode = opCode;
        admin=new Admin();
    }

    public void setUserName(String userName) {
        admin.setUserName(userName);
    }

    public void setPassword(String password) {
        admin.setPassword(password);
    }

    public Short getOpCode() {
        return opCode;
    }

    @Override
    public User getUser() {return admin; }

    @Override
    public void setUser(User user) { }

    public String getUserName() {
        return admin.getUserName();
    }
    public String getPassword(){return admin.getPassword();}
}

package bgu.spl.net.impl.api.Messages;

import bgu.spl.net.impl.obj.User;

public class ResponseMessage implements Message {
    private Short OpCode;
    private Short msgCode;
    private String additionalInfo;
    public void setMsgCode(int msgCode) {
        this.msgCode = (short) msgCode;
    }

    public ResponseMessage() {}

    public ResponseMessage(int opcode) {
        this.OpCode = (short) opcode;
    }

    public void setOpCode(int opCode) {
        OpCode = (short) opCode;
    }

    public Short getMsgCode() {
        return msgCode;
    }

    @Override
    public Short getOpCode() {
        return OpCode;
    }

    @Override
    public User getUser() {
        return null;
    }

    @Override
    public void setUser(User user) {
        return;
    }
    public void addInfo(String info) {
        additionalInfo = info;
        additionalInfo = additionalInfo.replaceAll("\\s+", "");
    }

    public void addStatInfo(String info){
        additionalInfo = info;
    }

    public String toString() {
        if (additionalInfo == null) {
            if (msgCode < 10)
                return OpCode + "0" + msgCode;
            return OpCode + "" + msgCode;
        }
        if (msgCode < 10)
            return OpCode + "0" + msgCode + "" + additionalInfo;
        return OpCode + "" + msgCode + "" + additionalInfo;
    }
}

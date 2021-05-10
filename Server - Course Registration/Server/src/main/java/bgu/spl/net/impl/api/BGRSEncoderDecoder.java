package bgu.spl.net.impl.api;

import bgu.spl.net.impl.api.Messages.*;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class BGRSEncoderDecoder implements MessageEncoderDecoder<Message> {
    private short opCode;
    private byte[] bytes = new byte[1 << 10]; //start with 1k
    private int len = 0;
    private Message message;
    @Override
    public Message decodeNextByte(byte nextByte) {
        pushByte(nextByte);
        if (len == 2) {
            this.opCode = (short) (((bytes[0] - '0') * 10) + (bytes[1] - '0'));
            switch (opCode) {
                case 1:
                    message = new AdminRegMessage(opCode);
                    break;
                case 2:
                    message = new StudentRegMessage(opCode);
                    break;
                case 3:
                    message = new LogInMessage(opCode);
                    break;
                case 4:
                case 11:
                    len=0;
                    message = new emptyMessage(opCode);
                    return message;
                case 5:
                case 6:
                case 7:
                case 9:
                case 10:
                    message = new courseNumberMessage(opCode);
                    break;
                case 8:
                    message = new StudentStatMessage(opCode);
                    break;
            }
        }
        if (len > 2 && nextByte == 0 && message.getClass() != courseNumberMessage.class) {
            if (opCode == 8) {
                len = 0;
                return decodeStudentStat((StudentStatMessage) message);
            } else
                return decodeRegisterandLogin((RegisterLoginMessage) message);
        }
        if (len == 4 && message.getClass() == courseNumberMessage.class) {
            courseNumberMessage result = (courseNumberMessage) decodeCourseNumber((courseNumberMessage) message);
            len = 0;
            return result;
        }
        return null;
    }

    private Message decodeCourseNumber(courseNumberMessage msg){ //for messages that require course number.
        byte[] a=new byte[2];
        a[0]=bytes[2];
        a[1]=bytes[3];
        short courseNum = ByteBuffer.wrap(a).getShort();
        msg.setCourseNumber(courseNum);
        len=0;
        return msg;
    }

    @Override
    public byte[] encode(Message message) {
        ResponseMessage response = (ResponseMessage) message;
        return response.toString().getBytes(StandardCharsets.UTF_8);
    }

    private void pushByte(byte nextByte) {
        if (len >= bytes.length) {
            bytes = Arrays.copyOf(bytes, len * 2);
        }
        bytes[len++] = nextByte;
    }

    private Message decodeRegisterandLogin(RegisterLoginMessage message) {
        int i=2;
        while(!String.valueOf(bytes[i]).equals("0")){
            i++;
        }
        if(message.getUserName() == null) {
            String userName = new String(bytes, 2, i-2, StandardCharsets.UTF_8);
            message.setUserName(userName);
            pushByte((byte) 0);
            return null;
        }
        else{
            int j=i+2;
            while(!String.valueOf(bytes[j]).equals("0")){
                j++;
            }
            String password = new String(bytes, i+2, j-i-1, StandardCharsets.UTF_8);
            message.setPassword(password);
            len=0;
            return message;
        }
    }


    private Message decodeStudentStat(StudentStatMessage message){
        int i=2;
        while(!String.valueOf(bytes[i]).equals("0")){
            i++;
        }
        String userName = new String(bytes, 2, i-2, StandardCharsets.UTF_8);
        message.setUserName(userName);
        len=0;
        return message; //for studentSTAT

    }
}

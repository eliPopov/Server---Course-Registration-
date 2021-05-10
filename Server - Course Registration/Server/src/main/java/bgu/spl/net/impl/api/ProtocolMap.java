package bgu.spl.net.impl.api;

import bgu.spl.net.impl.api.Messages.Message;
import bgu.spl.net.impl.api.Protocols.*;


import java.util.HashMap;

public class ProtocolMap {
    private HashMap<Short, MessagingProtocol<Message>> ProtocolMap;
    private ProtocolMap(){
        ProtocolMap = new HashMap<>();
        ProtocolMap.put((short)1,new RegisterProtocol());
        ProtocolMap.put((short)2,new RegisterProtocol());
        ProtocolMap.put((short)3,new LoginProtocol());
        ProtocolMap.put((short)4,new LogoutProtocol());
        ProtocolMap.put((short)5,new CourseRegProtocol());
        ProtocolMap.put((short)6,new KdamCheckProtocol());
        ProtocolMap.put((short)7,new CourseStatProtocol());
        ProtocolMap.put((short)8,new StudentStatProtocol());
        ProtocolMap.put((short)9,new isRegisteredProtocol());
        ProtocolMap.put((short)10,new UnregisterProtocol());
        ProtocolMap.put((short)11,new MyCoursesProtocol());
    }
    public static ProtocolMap getInstance(){
        return ProtocolMapHolder.protocolMap;
    }
    private static class ProtocolMapHolder{
        private static ProtocolMap protocolMap = new ProtocolMap();
    }
    public MessagingProtocol<Message> getProtocol(Short code){
        return ProtocolMap.get(code);
    }
}

package bgu.spl.net.impl.api.Protocols;


import bgu.spl.net.impl.api.Messages.LogInMessage;
import bgu.spl.net.impl.api.Messages.Message;
import bgu.spl.net.impl.api.Messages.ResponseMessage;
import bgu.spl.net.impl.api.ProtocolMap;
import bgu.spl.net.impl.obj.*;

public class BGRSProtocol implements MessagingProtocol<Message> {
    ResponseMessage response = new ResponseMessage();
    Database database = Database.getInstance();
    private boolean shouldTerminate = false;
    public User client;

    public Message process(Message msg) {
        if (msg.getOpCode() > 3) { //checking to see if the user is registered and logged in
            msg.setUser(client);
            if (client == null || !database.hasRegisteredToServer(client.getUserName()) || !client.isLogged()) {
                response.setOpCode(13);
                response.setMsgCode(msg.getOpCode());
                return response;
            }
        }
        if (msg.getOpCode() < 4 && client != null && client.isLogged()) { //checking to see if a client which is already logged in trying to login\register
            response.setOpCode(13);
            response.setMsgCode(msg.getOpCode());
            return response;
        }
        ResponseMessage response = (ResponseMessage) ProtocolMap.getInstance().getProtocol(msg.getOpCode()).process(msg);
        shouldTerminate = ProtocolMap.getInstance().getProtocol(msg.getOpCode()).shouldTerminate();
        if (msg.getOpCode() == 3) {  //initializing the field 'client' to know which user sends the requests.
            client = database.getUser(((LogInMessage)msg).getUserName());
            msg.setUser(client);
        }
        return response;
    }

    public boolean shouldTerminate() {
        return shouldTerminate;
    }
}

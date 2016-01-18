package com.lamfire.paladin;

import com.lamfire.hydra.Message;
import com.lamfire.hydra.MessageFactory;
import com.lamfire.hydra.Session;
import com.lamfire.jspp.JSPPUtils;
import com.lamfire.jspp.SERVICE;

import java.net.SocketAddress;


public class Context {
    private final Session session;
    private final Message message;
    Context(Session session,Message message){
        this.session = session;
        this.message = message;
    }

    public SocketAddress getRemoveAddr(){
        return session.getRemoteAddress();
    }

    public synchronized void send(SERVICE service){
        byte[] bytes = JSPPUtils.encode(service);
        message.content(bytes);
        message.header().contentLength(bytes.length);
        session.send(message);
    }

    public byte[] getRequestAsBytes(){
        return message.content();
    }
}

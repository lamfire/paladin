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
    private final PaladinSerializer serializer;

    Context(Session session,Message message,PaladinSerializer serializer){
        this.session = session;
        this.message = message;
        this.serializer = serializer;
    }

    public SocketAddress getRemoteAddr(){
        return session.getRemoteAddress();
    }

    public synchronized void send(SERVICE service){
        byte[] bytes = this.serializer.encode(service);
        message.content(bytes);
        message.header().contentLength(bytes.length);
        session.send(message);
    }

    public byte[] getRequestAsBytes(){
        return message.content();
    }
}

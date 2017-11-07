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
        Message m = this.serializer.encode(session,service);
        m.header().id(this.message.getId());
        m.header().option(this.message.getOption());
        session.send(m);
    }

    public byte[] getRequestAsBytes(){
        return message.content();
    }
}

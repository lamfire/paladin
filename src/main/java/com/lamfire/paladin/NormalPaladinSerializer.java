package com.lamfire.paladin;

import com.lamfire.hydra.Message;
import com.lamfire.hydra.MessageFactory;
import com.lamfire.hydra.Session;
import com.lamfire.jspp.JSPP;
import com.lamfire.jspp.JSPPUtils;
import com.lamfire.jspp.ProtocolType;
import com.lamfire.jspp.SERVICE;

/**
 * Created by lamfire on 16/3/28.
 */
public class NormalPaladinSerializer implements PaladinSerializer {
    @Override
    public Message encode(Session session, SERVICE service) {
        return MessageFactory.message(0,0,encode(service));
    }

    @Override
    public SERVICE decode(Session session,Message message) {
        return decode(message.content());
    }

    public byte[] encode(SERVICE service){
        byte[] body =  JSPPUtils.encode(service);
        return body;
    }
    public SERVICE decode(byte[] bytes){
        JSPP jspp = JSPPUtils.decode(bytes);
        if(JSPPUtils.getProtocolType(jspp) != ProtocolType.SERVICE){
            return null;
        }
        return (SERVICE)jspp;
    }
}

package com.lamfire.paladin;

import com.lamfire.code.AES;
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
public class AESPaladinSerializer implements PaladinSerializer {
    public static final String SESSION_AES_KEY = "_SESSION_AES_KEY_";

    @Override
    public Message encode(Session session,SERVICE service) {
        byte[] aesKey = (byte[])session.attr(SESSION_AES_KEY);
        byte[] bytes =  JSPPUtils.encode(service);
        if(aesKey != null) {
            bytes =  AES.encode(bytes, aesKey);
        }
        return MessageFactory.message(0,0,bytes);
    }

    @Override
    public SERVICE decode(Session session, Message message) {
        byte[] aesKey = (byte[])session.attr(SESSION_AES_KEY);
        byte[]  bytes = message.content();
        if(aesKey != null){
            bytes = AES.decode(bytes,aesKey);
        }

        JSPP jspp = JSPPUtils.decode(bytes);
        if(JSPPUtils.getProtocolType(jspp) != ProtocolType.SERVICE){
            return null;
        }
        return (SERVICE)jspp;
    }
}

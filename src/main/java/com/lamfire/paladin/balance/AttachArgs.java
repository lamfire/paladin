package com.lamfire.paladin.balance;

import com.lamfire.jspp.SERVICE;

/**
 * Created with IntelliJ IDEA.
 * User: linfan
 * Date: 16-1-19
 * Time: 下午4:24
 * To change this template use File | Settings | File Templates.
 */
public class AttachArgs {
    public static final String MESSAGE_ID = "_MID";
    public static final String SESSION_ID="_SID";

    public static void attach(SERVICE service ,long sessionId,int messageId) {
        service.put(AttachArgs.SESSION_ID,sessionId);
        service.put(AttachArgs.MESSAGE_ID,messageId);
    }

    public static void remove(SERVICE service){
        service.remove(AttachArgs.SESSION_ID);
        service.remove(AttachArgs.MESSAGE_ID);
    }

    public static int messageId(SERVICE service){
        return service.getInteger(MESSAGE_ID);
    }

    public static long sessionId(SERVICE service){
        return service.getLong(SESSION_ID);
    }
}

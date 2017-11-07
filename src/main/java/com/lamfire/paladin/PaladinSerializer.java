package com.lamfire.paladin;

import com.lamfire.hydra.Message;
import com.lamfire.hydra.Session;
import com.lamfire.jspp.SERVICE;

/**
 * 消息序例化接口
 */
public interface PaladinSerializer {
    Message encode(Session session, SERVICE service);
    SERVICE decode(Session session,Message message);

}

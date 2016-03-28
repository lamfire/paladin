package com.lamfire.paladin;

import com.lamfire.jspp.SERVICE;

/**
 * 消息序例化接口
 */
public interface PaladinSerializer {
    public byte[] encode(SERVICE service);
    public SERVICE decode(byte[] bytes);

}

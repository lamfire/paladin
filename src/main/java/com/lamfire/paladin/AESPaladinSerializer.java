package com.lamfire.paladin;

import com.lamfire.code.AES;
import com.lamfire.jspp.JSPP;
import com.lamfire.jspp.JSPPUtils;
import com.lamfire.jspp.ProtocolType;
import com.lamfire.jspp.SERVICE;

/**
 * Created by lamfire on 16/3/28.
 */
public class AESPaladinSerializer implements PaladinSerializer {

    private byte[] keyBytes;

    public AESPaladinSerializer(byte[] keyBytes) {
        this.keyBytes = keyBytes;
    }

    @Override
    public byte[] encode(SERVICE service) {
        byte[] bytes =  JSPPUtils.encode(service);

        return AES.encode(bytes,keyBytes);
    }

    @Override
    public SERVICE decode(byte[] bytes) {
        bytes = AES.decode(bytes,keyBytes);
        JSPP jspp = JSPPUtils.decode(bytes);
        if(JSPPUtils.getProtocolType(jspp) != ProtocolType.SERVICE){
            return null;
        }
        return (SERVICE)jspp;
    }
}

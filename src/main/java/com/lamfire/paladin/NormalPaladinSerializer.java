package com.lamfire.paladin;

import com.lamfire.jspp.JSPP;
import com.lamfire.jspp.JSPPUtils;
import com.lamfire.jspp.ProtocolType;
import com.lamfire.jspp.SERVICE;

/**
 * Created by lamfire on 16/3/28.
 */
public class NormalPaladinSerializer implements PaladinSerializer {
    @Override
    public byte[] encode(SERVICE service) {
        return JSPPUtils.encode(service);
    }

    @Override
    public SERVICE decode(byte[] bytes) {
        JSPP jspp = JSPPUtils.decode(bytes);
        if(JSPPUtils.getProtocolType(jspp) != ProtocolType.SERVICE){
            return null;
        }
        return (SERVICE)jspp;
    }
}

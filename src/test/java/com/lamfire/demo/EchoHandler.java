package com.lamfire.demo;

import com.lamfire.code.PUID;
import com.lamfire.hydra.Session;
import com.lamfire.jspp.RESULT;
import com.lamfire.jspp.SERVICE;
import com.lamfire.paladin.Context;
import com.lamfire.paladin.NS;
import com.lamfire.paladin.NSHandler;

/**
 * Created with IntelliJ IDEA.
 * User: linfan
 * Date: 16-1-18
 * Time: 下午2:02
 * To change this template use File | Settings | File Templates.
 */
@NS(name="ns.echo")
public class EchoHandler implements NSHandler {
    @Override
    public void service(Context ctx, SERVICE service) {
        //System.out.println(service);
        service.setType("result");
        RESULT result = new RESULT();
        result.put("method","ECHO");
        result.put("token",PUID.puidAsString());
        service.setFrom(ctx.getRemoteAddr().toString());
        service.setResult(result);
        ctx.send(service);
    }
}

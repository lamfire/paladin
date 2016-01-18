package com.lamfire.paladin;

import com.lamfire.hydra.*;
import com.lamfire.jspp.JSPP;
import com.lamfire.jspp.JSPPUtils;
import com.lamfire.jspp.ProtocolType;
import com.lamfire.jspp.SERVICE;
import com.lamfire.logger.Logger;


public class Paladin implements MessageReceivedListener{
    private static final Logger LOGGER = Logger.getLogger(Paladin.class);
    private final NSRegistry registry = new NSRegistry();
    private final PaladinOption option = new PaladinOption();
    private final String host;
    private final int port;
    private Hydra hydra;

    public Paladin(String host,int port){
        this.host = host;
        this.port = port;
    }

    public void setThreads(int threads){
        option.setThreads(threads);
    }

    public void mappingNS(String packageName) throws Exception {
        option.setMappingNSPackage(packageName);
        registry.mappingPackage(packageName);
    }

    public synchronized void startup(){
        if(hydra != null){
            return;
        }

        if(registry.isEmpty()){
            LOGGER.error("Not NS handler was registered,shutdown now.");
            return;
        }

        HydraBuilder builder = new HydraBuilder();
        builder.bind(host).port(port).messageReceivedListener(this).threads(option.getThreads());
        hydra = builder.build();
        hydra.startup();
    }

    public synchronized void shutdown(){
        if(hydra == null){
            return;
        }
        hydra.shutdown();
        hydra = null;
    }

    private void executeService(Context ctx ,SERVICE service){
        String ns = service.getNs();
        NSHandler handler = registry.lookup(ns);
        if(handler == null){
            LOGGER.error("NS["+ns+"] not found.");
            return;
        }

        handler.service(ctx,service);
    }

    @Override
    public void onMessageReceived(Session session, Message message) {
        byte[] bytes = message.content();
        JSPP jspp = JSPPUtils.decode(bytes);
        if(JSPPUtils.getProtocolType(jspp) == ProtocolType.SERVICE){
            Context ctx = new Context(session,message);
            executeService(ctx, (SERVICE)jspp);
            return;
        }
    }
}

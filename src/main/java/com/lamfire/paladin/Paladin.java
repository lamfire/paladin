package com.lamfire.paladin;

import com.lamfire.hydra.*;
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

    public void setPaladingSerializer(PaladinSerializer serializer){
        this.option.setPaladinSerializer(serializer);
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
            LOGGER.error("Not found NS handler was be registered");
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
        SERVICE service = this.option.getPaladinSerializer().decode(bytes);
        if(service == null){
            return;
        }

        Context ctx = new Context(session,message,option.getPaladinSerializer());
        executeService(ctx,service);
    }
}

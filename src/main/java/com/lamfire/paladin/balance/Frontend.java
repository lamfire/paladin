package com.lamfire.paladin.balance;

import com.lamfire.hydra.*;
import com.lamfire.jspp.JSPP;
import com.lamfire.jspp.JSPPUtils;
import com.lamfire.jspp.SERVICE;
import com.lamfire.logger.Logger;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class Frontend implements MessageReceivedListener {
    private static final Logger LOGGER = Logger.getLogger(Frontend.class);
    private String bind;
    private int port;

    private Hydra hydra;
    private BalanceOption option;
    private final BlockingQueue<SERVICE> requestQueue = new LinkedBlockingQueue<SERVICE>();

    public Frontend(String bind, int port,BalanceOption option) {
        this.bind = bind;
        this.port = port;
        this.option = option;
    }

    void startup(){
        HydraBuilder builder = new HydraBuilder();
        builder.threads(4);
        builder.bind(bind);
        builder.port(port);
        builder.messageReceivedListener(this);
        hydra = builder.build();
        hydra.startup();
        LOGGER.info("startup on " + bind + ":" + port);
    }

    @Override
    public void onMessageReceived(Session session, Message message) {
        //decode
        SERVICE service = option.getPaladinSerializer().decode(message.content());
        //LOGGER.debug("[MESSAGE] : " + service);
        Statis.getInstance().incrementRequests();

        //check Server Busy?
        if(requestQueue.size() >= option.getMaxWaitQueueLength()){
            Statis.getInstance().incrementWaitQueueFullIgnores();
            service.setType("error");
            service.setBody("Server Busy");
            byte[] bytes = JSPPUtils.encode(service);
            message.content(bytes);
            message.header().contentLength(bytes.length);
            session.send(message);
            LOGGER.error("max wait queue,send server busy.");
            return;
        }

        //add to wait queue
        try {
            AttachArgs.attach(service,session.getId(),message.header().id());
            requestQueue.put(service);
            Statis.getInstance().setWaitQueueLength(requestQueue.size());
        } catch (InterruptedException e) {
            LOGGER.warn(e.getMessage(),e);
        }
    }

    void shutdown(){
        this.hydra.shutdown();
        LOGGER.info("shutdown on " + bind + ":" + port);
    }

    public Session getSession(long id){
        return hydra.getSessionMgr().get(id);
    }

    public SERVICE takeRequest(){
        try {
            SERVICE take =  requestQueue.take();
            Statis.getInstance().setWaitQueueLength(requestQueue.size());
            return take;
        } catch (InterruptedException e) {
            LOGGER.warn(e.getMessage(),e);
        }
        return null;
    }
}

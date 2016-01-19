package com.lamfire.paladin.balance;

import com.lamfire.hydra.Message;
import com.lamfire.hydra.MessageFactory;
import com.lamfire.hydra.Session;
import com.lamfire.jspp.JSPPUtils;
import com.lamfire.jspp.SERVICE;
import com.lamfire.logger.Logger;
import com.lamfire.utils.CircularLinkedList;


public class PollingLoadBalance implements LoadBalance,Runnable {
    private static final Logger LOGGER = Logger.getLogger(PollingLoadBalance.class);
    private final CircularLinkedList<Backend> backends = new CircularLinkedList<Backend>();

    private Frontend frontend;
    private boolean _shutdown = false;
    private BalanceOption option;
    private Thread thread;

    public PollingLoadBalance(String bind, int port,BalanceOption option){
        this.option = option;
        this.frontend = new Frontend(bind,port,option);
    }

    @Override
    public void addBackend(String host, int port) {
        Backend backend = new Backend(host,port,this);
        backends.add(backend);
    }

    @Override
    public void onResponse(SERVICE response) {
        long sid = response.getLong(AttachArgs.SESSION_ID);
        int mid = response.getInteger(AttachArgs.MESSAGE_ID);
        Session s = frontend.getSession(sid);
        if(s  == null){
            LOGGER.error("the session["+sid+"] was closed ,ignore response : " + response );
            return;
        }
        AttachArgs.remove(response);
        Message m  = MessageFactory.message(mid,0, JSPPUtils.encode(response));
        s.send(m);
    }

    public void startup(){
        this.frontend.startup();
        for(int i=0;i< backends.size();i++){
            Backend b = backends.get(i);
            b.startup();
        }
        thread = new Thread(this);
        thread.setName("LoadBalance");
        thread.setDaemon(true);
        thread.start();
    }


    public void shutdown(){
        _shutdown = true;
        this.frontend.shutdown();
        for(int i=0;i< backends.size();i++){
            Backend b = backends.get(i);
            b.shutdown();
        }
    }

    @Override
    public void run() {
        while(!_shutdown){
            forwardToBackend();
        }
    }

    private void forwardToBackend(){
        try{
            SERVICE service = frontend.takeRequest();
            Statis.getInstance().incrementForwards();
            if(service != null){
                Backend backend = backends.next();
                backend.send(service);
                Statis.getInstance().incrementBackend(backend);
                LOGGER.debug(backend + " - forward message : " + service);
            }
        } catch (Throwable t){
            LOGGER.error(t.getMessage(), t);
        }
    }
}

package com.lamfire.paladin.balance;

import com.lamfire.hydra.*;
import com.lamfire.jspp.JSPPUtils;
import com.lamfire.jspp.SERVICE;
import com.lamfire.logger.Logger;


public class Backend implements MessageReceivedListener{
    private static final Logger LOGGER = Logger.getLogger(Backend.class);
    private String host;
    private int port;
    private LoadBalance loadBalance;

    private Snake snake;

    public Backend(String host, int port, LoadBalance loadBalance) {
        this.host = host;
        this.port = port;
        this.loadBalance = loadBalance;
    }

    void startup(){
        SnakeBuilder builder = new SnakeBuilder();
        builder.autoConnectRetry(true);
        builder.heartbeatEnable(true);
        builder.host(host);
        builder.port(port);
        builder.threads(4);
        builder.messageReceivedListener(this);
        snake = builder.build();
        snake.startup();
        LOGGER.info("startup on " + host + ":" + port);
    }

    void shutdown(){
        snake.shutdown();
        LOGGER.info("shutdown on " + host + ":" + port);
    }

    public void send(SERVICE service){
        Message msg = MessageFactory.message(0,0,JSPPUtils.encode(service));
        snake.getSession().send(msg);
    }

    @Override
    public void onMessageReceived(Session session, Message message) {
        //decode
        SERVICE service = (SERVICE) JSPPUtils.decode(message.content());
        this.loadBalance.onResponse(service);
    }

    @Override
    public String toString() {
        return "Backend{" +
                "port=" + port +
                ", host='" + host + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Backend backend = (Backend) o;

        if (port != backend.port) return false;
        if (!host.equals(backend.host)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = host.hashCode();
        result = 31 * result + port;
        return result;
    }

    public boolean isAvailable() {
        return !snake.getSessionMgr().isEmpty();
    }
}

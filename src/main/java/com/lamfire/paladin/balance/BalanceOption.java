package com.lamfire.paladin.balance;

/**
 * Created with IntelliJ IDEA.
 * User: linfan
 * Date: 16-1-19
 * Time: 上午10:40
 * To change this template use File | Settings | File Templates.
 */
public class BalanceOption {
    private int maxWaitQueueLength = 1024;
    private int maxConnections = 10000;

    public int getMaxWaitQueueLength() {
        return maxWaitQueueLength;
    }

    public void setMaxWaitQueueLength(int maxWaitQueueLength) {
        this.maxWaitQueueLength = maxWaitQueueLength;
    }

    public int getMaxConnections() {
        return maxConnections;
    }

    public void setMaxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
    }
}

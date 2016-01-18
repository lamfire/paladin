package com.lamfire.paladin;

/**
 * Created with IntelliJ IDEA.
 * User: linfan
 * Date: 16-1-18
 * Time: 上午11:36
 * To change this template use File | Settings | File Templates.
 */
public class PaladinOption {
    private int threads = 16;
    private String mappingNSPackage;

    public int getThreads() {
        return threads;
    }

    public void setThreads(int threads) {
        this.threads = threads;
    }

    public String getMappingNSPackage() {
        return mappingNSPackage;
    }

    public void setMappingNSPackage(String mappingNSPackage) {
        this.mappingNSPackage = mappingNSPackage;
    }
}

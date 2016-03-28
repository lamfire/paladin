package com.lamfire.paladin;


public class PaladinOption {
    private int threads = 16;
    private PaladinSerializer paladinSerializer = new NormalPaladinSerializer();
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

    public PaladinSerializer getPaladinSerializer() {
        return paladinSerializer;
    }

    public void setPaladinSerializer(PaladinSerializer paladinSerializer) {
        this.paladinSerializer = paladinSerializer;
    }
}

package com.lamfire.paladin.balance;


import com.lamfire.jspp.SERVICE;

public interface LoadBalance {
    public void addBackend(String host,int port);
    public void onResponse(SERVICE response);
}

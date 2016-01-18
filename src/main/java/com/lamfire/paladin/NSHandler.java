package com.lamfire.paladin;

import com.lamfire.hydra.Message;
import com.lamfire.hydra.Session;
import com.lamfire.jspp.SERVICE;


public interface NSHandler {
    public void service(Context context, SERVICE service);
}

package com.lamfire.paladin;

import com.lamfire.hydra.Message;
import com.lamfire.hydra.Session;
import com.lamfire.jspp.SERVICE;

/**
 * Created with IntelliJ IDEA.
 * User: linfan
 * Date: 16-1-18
 * Time: 上午11:42
 * To change this template use File | Settings | File Templates.
 */
public interface NSHandler {
    public void service(Context context, SERVICE service);
}

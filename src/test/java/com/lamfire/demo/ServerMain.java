package com.lamfire.demo;

import com.lamfire.paladin.Paladin;
import com.lamfire.paladin.PaladinOption;

/**
 * Created with IntelliJ IDEA.
 * User: linfan
 * Date: 16-1-18
 * Time: 下午2:02
 * To change this template use File | Settings | File Templates.
 */
public class ServerMain {

    public static void main(String[] args) throws Exception {
        Paladin paladin = new Paladin("0.0.0.0",9200);
        paladin.mappingNS("com.lamfire.demo");
        paladin.startup();
    }
}

package com.lamfire.demo;

import com.lamfire.code.MD5;
import com.lamfire.paladin.AESPaladinSerializer;
import com.lamfire.paladin.Paladin;

/**
 * Created with IntelliJ IDEA.
 * User: linfan
 * Date: 16-1-18
 * Time: 下午2:02
 * To change this template use File | Settings | File Templates.
 */
public class AESServerMain {

    public static void main(String[] args) throws Exception {
        Paladin paladin = new Paladin("0.0.0.0",9200);
        paladin.mappingNS("com.lamfire.demo");
        paladin.setPaladingSerializer(new AESPaladinSerializer());
        paladin.startup();
    }
}

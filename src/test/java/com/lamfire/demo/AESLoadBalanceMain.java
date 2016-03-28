package com.lamfire.demo;

import com.lamfire.code.MD5;
import com.lamfire.paladin.AESPaladinSerializer;
import com.lamfire.paladin.balance.BalanceOption;
import com.lamfire.paladin.balance.PollingLoadBalance;

/**
 * Created with IntelliJ IDEA.
 * User: linfan
 * Date: 16-1-19
 * Time: 下午3:20
 * To change this template use File | Settings | File Templates.
 */
public class AESLoadBalanceMain {
    public static void main(String[] args) {
        BalanceOption option = new BalanceOption();
        option.setPaladinSerializer(new AESPaladinSerializer(MD5.digest("123456".getBytes())));
        PollingLoadBalance balance = new PollingLoadBalance("0.0.0.0",8888,option);
        balance.addBackend("127.0.0.1",9100);
        balance.addBackend("127.0.0.1",9200);
        balance.startup();
    }
}

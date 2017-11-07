package com.lamfire.demo;

import com.lamfire.code.MD5;
import com.lamfire.hydra.Message;
import com.lamfire.hydra.Session;
import com.lamfire.hydra.reply.Future;
import com.lamfire.hydra.reply.ReplySnake;
import com.lamfire.jspp.ARGS;
import com.lamfire.jspp.SERVICE;
import com.lamfire.paladin.AESPaladinSerializer;
import com.lamfire.utils.Reflect;

/**
 * Created with IntelliJ IDEA.
 * User: linfan
 * Date: 16-1-18
 * Time: 下午2:11
 * To change this template use File | Settings | File Templates.
 */
public class AESLBClientMain {
    public static void main(String[] args) throws Exception{
        //创建网络连接对象
        ReplySnake snake = new ReplySnake();
        snake.setHeartbeatEnable(false);
        //snake.startup("127.0.0.1",9200);
        snake.startup("127.0.0.1",8888);


        //创建JSPP消息
        ARGS arg = new ARGS();
        arg.put("username","lamfire");
        arg.put("token","1234567890");

        SERVICE m = new SERVICE();
        m.setNs("ns.echo");
        m.setArgs(arg);
        m.setId("100001");


        Session session = (Session) Reflect.on(snake).field("snake").call("getSession").get();

        //发送消息
        AESPaladinSerializer serializer = new AESPaladinSerializer();
        Message message = serializer.encode(session,m);
        Future future = snake.send(message.content()) ;
        System.out.println(" [ECHO] - " + serializer.decode(session,future.getResponseMessage()) +" ");
        snake.shutdown();
    }
}

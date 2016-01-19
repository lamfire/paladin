package com.lamfire.demo;

import com.lamfire.hydra.Message;
import com.lamfire.hydra.reply.Future;
import com.lamfire.hydra.reply.ReplySnake;
import com.lamfire.jspp.*;

/**
 * Created with IntelliJ IDEA.
 * User: linfan
 * Date: 16-1-18
 * Time: 下午2:49
 * To change this template use File | Settings | File Templates.
 */
public class ClientPerformanceMain {
    public static void main(String[] args) throws Exception{
        //创建网络连接对象
        ReplySnake snake = new ReplySnake();
        snake.startup("127.0.0.1",8888);

        //创建JSPP消息
        ARGS arg = new ARGS();
        arg.put("username", "lamfire");
        arg.put("token", "1234567890");

        SERVICE m = new SERVICE();
        m.setNs("ns.echo");
        m.setArgs(arg);
        m.setId("100001");

        int count = 0;
        long startAt = System.currentTimeMillis();
        while(true){
            try{
                //设置自定义属性
                m.setId(count+"");

                //发送消息
                Future future = snake.send(JSPPUtils.encode(m)) ;
                //获得响应数据
                Message message = future.getResponse();

                //每1000次输出一次响应数据及响应时间
                if((++count) % 1000 == 0){
                    System.out.println(count +" - " + new String(message.content()) +" " + ( System.currentTimeMillis() - startAt) +"ms");
                    startAt = System.currentTimeMillis();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}

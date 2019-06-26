package com.tensquare.rabbitmqdemo.Reception;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author Rem
 * @Date 2019-06-21
 */

@Component
@RabbitListener(queues = "itheima")
public class Customer2 {

    @RabbitHandler
    public void getMsg(String msg) {
        System.err.println("itheima:" + msg);
    }
}

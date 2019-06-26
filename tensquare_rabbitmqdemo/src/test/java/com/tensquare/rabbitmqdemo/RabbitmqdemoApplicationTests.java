package com.tensquare.rabbitmqdemo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitmqdemoApplicationTests {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void contextLoads() {
        rabbitTemplate.convertAndSend("itcast", "直接模式测试消息");
    }

    @Test
    public void contextLoads2() {
        rabbitTemplate.convertAndSend("hhz", "", "分裂模式测试消息");
    }

    @Test
    public void contextLoads3() {
        rabbitTemplate.convertAndSend("topic21", "goods.log", "主题模式测试消息");
    }

}

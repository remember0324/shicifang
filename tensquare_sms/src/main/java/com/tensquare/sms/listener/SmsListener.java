package com.tensquare.sms.listener;

import com.aliyuncs.exceptions.ClientException;
import com.tensquare.sms.utils.SmsUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author Rem
 * @Date 2019-06-21
 */

@Component
@RabbitListener(queues = "sms")
public class SmsListener {

    @Autowired
    private SmsUtil smsUtil;

    @Value("${aliyun.sms.template_code}")
    private String template_code;//模板编号
    @Value("${aliyun.sms.sign_name}")
    private String sign_name;//签名


    /**
     * 监听收到的消息_通过阿里云发送短信出去
     *
     * @param map
     */
    @RabbitHandler
    public void sendSms(Map<String, String> map) throws ClientException {
        String mobile = map.get("mobile");
        String code = map.get("code");
        System.err.println("手机号:" + mobile);
        System.err.println("验证码:" + code);
        //通过阿里云发短信
        smsUtil.sendSms(mobile, template_code, sign_name, "{\"code\":\"" + code + "\"}");

    }
}

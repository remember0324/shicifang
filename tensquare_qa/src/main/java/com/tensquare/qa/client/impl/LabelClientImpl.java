package com.tensquare.qa.client.impl;

import com.tensquare.qa.client.LabelClient;
import entity.Result;
import entity.StatusCode;
import org.springframework.stereotype.Component;

/**
 * @Author Rem
 * @Date 2019-06-25
 */

@Component
public class LabelClientImpl implements LabelClient {


    @Override
    public Result findById(String id) {
        return new Result(false, StatusCode.ERROR, "触发熔断器");
    }
}

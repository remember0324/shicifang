package com.tensquare.qa.client;

import com.tensquare.qa.client.impl.LabelClientImpl;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author Rem
 * @Date 2019-06-24
 */

@FeignClient(value = "tensquare-base", fallback = LabelClientImpl.class)
public interface LabelClient {


    /**
     * 调用base根据id查询标签
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/label/{id}")
    public Result findById(@PathVariable("id") String id);
}

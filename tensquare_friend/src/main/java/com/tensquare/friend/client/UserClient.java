package com.tensquare.friend.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * @Author Rem
 * @Date 2019-06-24
 */

@FeignClient("tensquare-user")
public interface UserClient {


    @PutMapping("user/updateFans/{userid}/{friendid}/{x}")
    public void updateFans(@PathVariable("userid") String userid, @PathVariable("friendid") String friendid, @PathVariable("x") int x);

}

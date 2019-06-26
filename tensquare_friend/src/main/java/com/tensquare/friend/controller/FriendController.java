package com.tensquare.friend.controller;

import com.tensquare.friend.client.UserClient;
import com.tensquare.friend.service.FriendService;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author Rem
 * @Date 2019-06-24
 */

@RestController
@RequestMapping("/friend")
public class FriendController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private FriendService friendService;

    @Autowired
    private UserClient userClient;

    @PostMapping("/like/{friendid}/{type}")
    public Result addFriend(@PathVariable String friendid, @PathVariable String type) {

        Claims claims = (Claims) request.getAttribute("user_claims");
        if (claims == null) {
            return new Result(false, StatusCode.ERROR, "权限不足");
        }
        String userid = claims.getId();
        if (!StringUtils.isEmpty(type)) {
            if ("1".equals(type)) {
                //添加喜欢
                int flag = friendService.likeFriend(userid, friendid);
                if (flag == 0) {
                    return new Result(false, StatusCode.REPERROR, "重复操作");
                }
                //更新粉丝数
                userClient.updateFans(userid, friendid, 1);
                return new Result(false, StatusCode.OK, "添加成功");
            } else if ("2".equals(type)) {
                //添加不喜欢
                int flag = friendService.notlikeFriend(userid, friendid);
                if (flag == 0) {
                    return new Result(false, StatusCode.REPERROR, "重复操作");
                }
                return new Result(false, StatusCode.OK, "添加成功");

            }
            return new Result(false, StatusCode.OK, "参数有误");
        }

        return new Result(false, StatusCode.OK, "参数有误");

    }

    /**
     * 删除好友
     *
     * @return
     */
    @DeleteMapping("/{friendid}")
    public Result delFriend(@PathVariable String friendid) {
        Claims claims = (Claims) request.getAttribute("user_claims");
        if (claims == null) {
            return new Result(false, StatusCode.ERROR, "权限不足");
        }
        String userid = claims.getId();
        friendService.delFriend(userid, friendid);

        //更新粉丝数
        userClient.updateFans(userid, friendid, -1);
        return new Result(false, StatusCode.OK, "删除成功");

    }
}

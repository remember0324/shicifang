package com.tensquare.user.controller;

import com.tensquare.user.pojo.User;
import com.tensquare.user.service.UserService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 控制器层
 *
 * @author Administrator
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private JwtUtil jwtUtil;


    /**
     * 查询全部数据
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", userService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "查询成功", userService.findById(id));
    }


    /**
     * 分页+多条件查询
     *
     * @param searchMap 查询条件封装
     * @param page      页码
     * @param size      页大小
     * @return 分页结果
     */
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
        Page<User> pageList = userService.findSearch(searchMap, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap) {
        return new Result(true, StatusCode.OK, "查询成功", userService.findSearch(searchMap));
    }

    /**
     * 修改
     *
     * @param user
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody User user, @PathVariable String id) {
        user.setId(id);
        userService.update(user);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除
     *
     * @param id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id) {

        Claims claims = (Claims) request.getAttribute("admin_claims");
        if (claims != null) {
            userService.deleteById(id);
            return new Result(true, StatusCode.OK, "删除成功");
        }
        return new Result(true, StatusCode.ACCESSERROR, "权限不足");

    }

    /**
     * 发送短信验证码
     *
     * @param mobile
     * @return
     */
    @PostMapping("/sendsms/{mobile}")
    public Result sendSms(@PathVariable String mobile) {
        userService.sendSms(mobile);
        return new Result(true, StatusCode.OK, "发送成功");
    }

    /**
     * 注册用户
     *
     * @param code
     * @param user
     * @return
     */
    @PostMapping("/register/{code}")
    public Result register(@PathVariable String code, @RequestBody User user) {
        String checkCode = (String) redisTemplate.opsForValue().get("sms" + user.getMobile());

        if (StringUtils.isEmpty(code)) {
            return new Result(true, StatusCode.ERROR, "请重新发送短信验证码");
        }
        if (!code.equals(checkCode)) {
            return new Result(true, StatusCode.ERROR, "验证码错误");
        }
        userService.add(user);
        return new Result(true, StatusCode.OK, "注册成功");
    }

    /**
     * 登陆
     *
     * @param map
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody Map<String, String> map) {
        User user = userService.login(map);
        if (user != null) {
            //生成token
            String token = jwtUtil.createJWT(user.getId(), user.getNickname(), "user");
            Map<String, Object> tokenMap = new HashMap<>(3);

            tokenMap.put("token", token);
            tokenMap.put("name", user.getNickname());
            tokenMap.put("avatar", user.getAvatar());
            return new Result(true, StatusCode.OK, "登陆成功", tokenMap);
        }
        return new Result(true, StatusCode.LOGINERROR, "登陆失败");
    }

    /**
     * 更新粉丝数
     * @param userid
     * @param friendid
     * @param x
     */
    @PutMapping("/updateFans/{userid}/{friendid}/{x}")
    public void updateFans(@PathVariable String userid, @PathVariable String friendid, @PathVariable int x) {
        userService.updateFanscountAndFollowcount(userid, friendid, x);
    }
}

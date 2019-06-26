package com.tensquare.spit.controller;

import com.tensquare.spit.pojo.Spit;
import com.tensquare.spit.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 吐槽控制层
 *
 * @Author Rem
 * @Date 2019-06-19
 */

@RestController
@CrossOrigin
@RequestMapping(value = "/spit", method = RequestMethod.POST)
public class SpitController {
    @Autowired
    private SpitService spitService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private HttpServletRequest request;

    /**
     * 保存
     *
     * @param spit
     * @return
     */
    @PostMapping
    public Result save(Spit spit) {
        Claims claims = (Claims) request.getAttribute("user_claims");
        if (claims == null) {
            return new Result(true, StatusCode.ACCESSERROR, "权限不足");
        }
        spit.setUserid(claims.getId());
        spitService.save(spit);
        return new Result(true, StatusCode.OK, "保存成功");
    }

    /**
     * 查询全部
     *
     * @return
     */
    @GetMapping
    public Result findAll() {
        List<Spit> spitList = spitService.findAll();
        return new Result(true, StatusCode.OK, "查询全部成功", spitList);
    }

    /**
     * 根据id查询
     *
     * @param spitId
     * @return
     */
    @GetMapping("/{spitId}")
    public Result findbyId(@PathVariable String spitId) {
        return new Result(true, StatusCode.OK, "根据id成功", spitService.findById(spitId));
    }

    /**
     * 修改
     *
     * @param spitId
     * @param spit
     * @return
     */
    @PutMapping("/{spitId}")
    public Result update(@PathVariable String spitId, @RequestBody Spit spit) {
        spit.set_id(spitId);
        spitService.update(spit);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除
     *
     * @param spitId
     * @return
     */
    @DeleteMapping("/{spitId}")
    public Result delete(@PathVariable String spitId) {
        spitService.delete(spitId);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /**
     * 根据上级id查找
     *
     * @param parentid
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/comment/{parentid}/{page}/{size}")
    public Result findByParentId(@PathVariable String parentid, @PathVariable int page, @PathVariable int size) {
        Page<Spit> p = spitService.findByParentid(parentid, page, size);
        return new Result(true, StatusCode.OK, "根据上级id查找成功", new PageResult<Spit>(p.getTotalElements(), p.getContent()));
    }

    /**
     * 点赞
     *
     * @param spitId
     * @return
     */
    @PutMapping("/thumbup/{spitId}")
    public Result thumbup(@PathVariable String spitId) {
        String userid = "123";
        Object zan = redisTemplate.opsForValue().get("thumbup_" + userid + "_" + spitId);
        if (zan == null) {
            redisTemplate.opsForValue().set("thumbup_" + userid + "_" + spitId, "1");
            return new Result(true, StatusCode.REPERROR, "重复点赞");
        }
        spitService.thumbup(spitId);
        return new Result(true, StatusCode.OK, "点赞成功");
    }


    /**
     * 浏览
     *
     * @param spitId
     * @return
     */
    @PutMapping("/visits/{spitId}")
    public Result visits(@PathVariable String spitId) {
        String userid = "123";
        Object browse = redisTemplate.opsForValue().get("visits_" + userid + "_" + spitId);
        if (browse == null) {
            redisTemplate.opsForValue().set("visits_" + userid + "_" + spitId, "1");
            return new Result(true, StatusCode.REPERROR, "重复浏览");
        }
        spitService.visits(spitId);
        return new Result(true, StatusCode.OK, "浏览加1");
    }

    /**
     * 分享
     *
     * @param spitId
     * @return
     */
    @PutMapping("/share/{spitId}")
    public Result share(@PathVariable String spitId) {
        spitService.share(spitId);
        return new Result(true, StatusCode.OK, "分享加1");
    }

}

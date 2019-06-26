package com.tensquare.article_crawler.controller;

import com.tensquare.article_crawler.pojo.Comment;
import com.tensquare.article_crawler.service.CommentService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Rem
 * @Date 2019-06-19
 */

@RestController
@CrossOrigin
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 新增评论
     *
     * @param comment
     * @return
     */
    @PostMapping
    public Result save(@RequestBody Comment comment) {
        System.err.println(comment);
        commentService.add(comment);
        return new Result(true, StatusCode.OK, "评论成功 ");
    }

    /**
     * 根据id查找评论
     *
     * @param parentId
     * @return
     */
    @GetMapping("/article/{parentId}")
    public Result findByid(@PathVariable String parentId) {
        return new Result(true, StatusCode.OK, "根据id查找评论", commentService.findbyId(parentId));
    }

    /**
     * 删除评论
     *
     * @param parentId
     * @return
     */
    @DeleteMapping("/article/{parentId}")
    public Result delete(@PathVariable String parentId) {
        commentService.delete(parentId);
        return new Result(true, StatusCode.OK, "删除评论");
    }


    @GetMapping("/article")
    public Result findByid() {
        return new Result(true, StatusCode.OK, "获得全部", commentService.findAll());

    }
}


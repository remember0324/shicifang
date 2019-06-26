package com.tensquare.search.controller;

import com.tensquare.search.pojo.Article;
import com.tensquare.search.service.ArticleService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Rem
 * @Date 2019-06-20
 */

@RestController
@CrossOrigin
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService aricleService;

    @PostMapping
    public Result save(@RequestBody Article article) {
        System.err.println(article);
        aricleService.save(article);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    @GetMapping("/{key}/{page}/{size}")
    public Result search(@PathVariable String key, @PathVariable int page, @PathVariable int size) {
        Page<Article> p = aricleService.findBykey(key, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Article>(p.getTotalElements(), p.getContent()));
    }
}

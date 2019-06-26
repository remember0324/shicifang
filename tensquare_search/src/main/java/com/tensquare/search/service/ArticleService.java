package com.tensquare.search.service;

import com.tensquare.search.dao.ArticleDao;
import com.tensquare.search.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * @Author Rem
 * @Date 2019-06-20
 */

@Service
public class ArticleService {

    @Autowired
    private ArticleDao articleDao;

    /**
     * 新增
     *
     * @param article
     */
    public void save(Article article) {
        articleDao.save(article);
    }

    /**
     * 查询
     * @param key
     * @param page
     * @param size
     * @return
     */
    public Page<Article> findBykey(String key, int page, int size) {

        return articleDao.findByTitleLikeOrContentLike(key, key, PageRequest.of(page - 1, size));
    }

}

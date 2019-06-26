package com.tensquare.search.dao;


import com.tensquare.search.pojo.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @Author Rem
 * @Date 2019-06-20
 */

public interface ArticleDao extends ElasticsearchRepository<Article, String> {

    /**
     * 关键字模糊查询
     * @param title
     * @param content
     * @param pageable
     * @return
     */
    Page<Article> findByTitleLikeOrContentLike(String title, String content, Pageable pageable);

}

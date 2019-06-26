package com.tensquare.article_crawler.dao;


import com.tensquare.article_crawler.pojo.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Author Rem
 * @Date 2019-06-19
 */

public interface CommentDao extends MongoRepository<Comment, String> {

}

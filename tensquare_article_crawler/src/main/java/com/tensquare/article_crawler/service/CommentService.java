package com.tensquare.article_crawler.service;

import com.tensquare.article_crawler.dao.CommentDao;
import com.tensquare.article_crawler.pojo.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import java.util.Date;
import java.util.List;

/**
 * @Author Rem
 * @Date 2019-06-19
 */

@Service
@Transactional
public class CommentService {
    @Autowired
    private CommentDao commentDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 新增评论
     *
     * @param comment
     */
    public void add(Comment comment) {
        comment.set_id(idWorker.nextId() + "");
        comment.setPublishdate(new Date());
        commentDao.save(comment);
    }

    /**
     * 查询评论
     *
     * @param parentid
     * @return
     */
    public Comment findbyId(String parentid) {
        return commentDao.findById(parentid).get();
    }

    /**
     * 查全部
     * @return
     */
    public List<Comment> findAll() {
        return commentDao.findAll();
    }

    public void delete(String parentId) {
        commentDao.deleteById(parentId);
    }
}

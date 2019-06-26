package com.tensquare.spit.service;

import com.tensquare.spit.dao.SpitDao;
import com.tensquare.spit.pojo.Spit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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
public class SpitService {

    @Autowired
    private SpitDao spitDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 新增
     *
     * @param spit
     */
    public void save(Spit spit) {
        spit.set_id(idWorker.nextId() + "");
        spit.setPublishtime(new Date());
        spit.setVisits(0);
        spit.setThumbup(0);
        spit.setShare(0);
        spit.setComment(0);
        spit.setState("1");
        if (spit.getParentid() != null && !"".equals(spit.getParentid())) {
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(spit.get_id()));
            Update update = new Update();
            update.inc("commit", 1);
            mongoTemplate.updateFirst(query, update, "spit");
        }

        spitDao.save(spit);
    }

    /**
     * 删除
     *
     * @param spitId
     */
    public void delete(String spitId) {
        spitDao.deleteById(spitId);
    }

    /**
     * 修改
     *
     * @param spit
     */
    public void update(Spit spit) {
        spit.setPublishtime(new Date());
        spitDao.save(spit);
    }

    /**
     * 根据id查询
     *
     * @param spitId
     * @return
     */
    public Spit findById(String spitId) {
        return spitDao.findById(spitId).get();
    }

    /**
     * 查询全部
     *
     * @return
     */
    public List<Spit> findAll() {
        Sort sort = new Sort(Sort.Direction.DESC, "publishtime");
        return spitDao.findAll(sort);
    }

    /**
     * 根据上级ID查询吐槽数据（分页）
     *
     * @param parentid
     * @param page
     * @param size
     * @return
     */
    public Page<Spit> findByParentid(String parentid, int page, int size) {
        return spitDao.findByParentid(parentid, PageRequest.of(page - 1, size));
    }

    /**
     * 吐槽点赞
     *
     * @param spitId
     */
    public void thumbup(String spitId) {
        //方法1_消耗内存大
        // Spit spit = spitDao.findById(spitId).get();
        // spit.setThumbup(spit.getThumbup() == null ? 1 : spit.getThumbup() + 1);
        // spitDao.save(spit);
        //方法2_mongo原生办法
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(spitId));
        Update update = new Update();
        update.inc("thumbup", 1);
        mongoTemplate.updateFirst(query, update, "spit");
    }

    /**
     * 浏览
     *
     * @param spitId
     */
    public void visits(String spitId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(spitId));
        Update update = new Update();
        update.inc("visits", 1);
        mongoTemplate.updateFirst(query, update, "spit");
    }

    /**
     * 分享
     *
     * @param spitId
     */
    public void share(String spitId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(spitId));
        Update update = new Update();
        update.inc("share", 1);
        mongoTemplate.updateFirst(query, update, "spit");
    }

}

package com.tensquare.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.user.pojo.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 *
 * @author Administrator
 */
public interface UserDao extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {

    User findByMobile(String mobile);

    /**
     * 更新粉丝数
     *
     * @param userid
     * @param x
     */
    @Modifying
    @Query(value = "UPDATE tb_user SET fanscount=fanscount+?2 WHERE id =?1", nativeQuery = true)
    void updateFans(String userid, int x);

    /**
     * 更新关注数
     *
     * @param userid
     * @param x
     */
    @Modifying
    @Query(value = "UPDATE tb_user SET followcount=followcount+?2 WHERE id =?1", nativeQuery = true)
    void updateFollow(String userid, int x);
}
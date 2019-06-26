package com.tensquare.friend.dao;

import com.tensquare.friend.pojo.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @Author Rem
 * @Date 2019-06-24
 */
public interface FriendDao extends JpaRepository<Friend, String> {

    /**
     * 查找用户
     *
     * @param userid
     * @param friendid
     * @return
     */
    Friend findByUseridAndFriendid(String userid, String friendid);

    /**
     * 更新是否喜欢
     *
     * @param islike
     * @param userid
     * @param friendid
     */
    @Query(value = "UPDATE tb_friend SET islike=? WHERE userid=? AND friendid =?", nativeQuery = true)
    @Modifying
    void updateisLike(String islike, String userid, String friendid);

    /**
     * 删除好友
     *
     * @param userid
     * @param friendid
     */
    @Modifying
    @Query(value = "DELETE FROM tb_friend WHERE userid =? AND friendid = ?", nativeQuery = true)
    void delFriend(String userid, String friendid);
}

package com.tensquare.friend.dao;

import com.tensquare.friend.pojo.NoFriend;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author Rem
 * @Date 2019-06-24
 */
public interface NoFriendDao extends JpaRepository<NoFriend, String> {
    /**
     * 查找用户
     *
     * @param userid
     * @param friendid
     * @return
     */
    public NoFriend findByUseridAndFriendid(String userid, String friendid);

}

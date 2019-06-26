package com.tensquare.friend.service;

import com.tensquare.friend.dao.FriendDao;
import com.tensquare.friend.dao.NoFriendDao;
import com.tensquare.friend.pojo.Friend;
import com.tensquare.friend.pojo.NoFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author Rem
 * @Date 2019-06-24
 */

@Service
@Transactional
public class FriendService {

    @Autowired
    private FriendDao friendDao;

    @Autowired
    private NoFriendDao noFriendDao;

    /**
     * 添加喜欢
     *
     * @param userid
     * @param friendid
     * @return
     */
    public int likeFriend(String userid, String friendid) {
        //1查询之前是否喜欢
        Friend friend = friendDao.findByUseridAndFriendid(userid, friendid);
        if (friend != null) {
            //2喜欢过返回0
            return 0;
        }
        //3没有喜欢过,添加喜欢
        friend = new Friend();
        friend.setUserid(userid);
        friend.setFriendid(friendid);
        friend.setIslike("0");
        friendDao.save(friend);
        //4判读是否相互喜欢,是,islike改为1
        Friend frd = friendDao.findByUseridAndFriendid(friendid, userid);
        if (frd != null) {
            friendDao.updateisLike("1", userid, friendid);
            friendDao.updateisLike("1", friendid, userid);
        }
        return 1;
    }

    /**
     * 添加不喜欢
     *
     * @param userid
     * @param friendid
     * @return
     */
    public int notlikeFriend(String userid, String friendid) {
        //1查询之前是否(不喜欢)
        NoFriend noFriend = noFriendDao.findByUseridAndFriendid(userid, friendid);
        if (noFriend != null) {
            //2不喜欢过返回0
            return 0;
        }
        noFriend = new NoFriend();
        noFriend.setUserid(userid);
        noFriend.setFriendid(friendid);
        noFriendDao.save(noFriend);
        return 1;
    }

    /**
     * 删除好友
     *
     * @param userid
     * @param friendid
     */
    public void delFriend(String userid, String friendid) {
        //1.删除好友
        friendDao.delFriend(userid, friendid);
        //2.更新好友表的islike
        friendDao.updateisLike("0", friendid, userid);
        //3.添加不喜欢
        NoFriend noFriend = new NoFriend();
        noFriend.setUserid(userid);
        noFriend.setFriendid(friendid);
        noFriendDao.save(noFriend);
    }
}

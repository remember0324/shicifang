package com.tensquare.friend.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Author Rem
 * @Date 2019-06-24
 */

@Table(name = "tb_nofriend")
@Entity
@IdClass(NoFriend.class)
public class NoFriend implements Serializable {

    @Id
    private String userid;
    @Id
    private String friendid;

    public NoFriend() {
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getFriendid() {
        return friendid;
    }

    public void setFriendid(String friendid) {
        this.friendid = friendid;
    }

    @Override
    public String toString() {
        return "NoFriend{" +
                "userid='" + userid + '\'' +
                ", friendid='" + friendid + '\'' +
                '}';
    }
}

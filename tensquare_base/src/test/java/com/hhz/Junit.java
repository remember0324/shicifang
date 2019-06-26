package com.hhz;

import com.tensquare.base.BaseApplication;
import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @Author Rem
 * @Date 2019-06-17
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BaseApplication.class)
public class Junit {

    @Autowired
    private LabelDao labelDao;

    @Test
    @Transactional
    public void t1(){
        Label label = labelDao.getOne("1");
        System.err.println(label);
    }

    @Test
    public void t2(){
        List<Label> labels = labelDao.findAll();
        for (Label label : labels) {
            System.err.println(label);
        }
    }
}

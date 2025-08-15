package com.xxl.job.admin.dao;

import com.xxl.job.admin.core.model.XxlJobLogGlue;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Date;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class XxlJobLogGlueDaoTest {

    @Resource
    private XxlJobLogGlueDao xxlJobLogGlueDao;

    @Test
    public void test(){
        XxlJobLogGlue logGlue = new XxlJobLogGlue();
        logGlue.setJobId(1);
        logGlue.setGlueType("1");
        logGlue.setGlueSource("1");
        logGlue.setGlueRemark("1");

        logGlue.setAddTime(new Date());
        logGlue.setUpdateTime(new Date());
        xxlJobLogGlueDao.save(logGlue);

        xxlJobLogGlueDao.findByJobId(1);

        xxlJobLogGlueDao.removeOld(1, 1);

        xxlJobLogGlueDao.deleteByJobId(1);
    }

}

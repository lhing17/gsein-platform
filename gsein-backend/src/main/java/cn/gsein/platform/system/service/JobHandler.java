package cn.gsein.platform.system.service;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Component;


@Component
public class JobHandler {

    @XxlJob("demoJobHandler")
    public ReturnT<String> demoJobHandler(String param) throws Exception {
        System.out.println("XXL-JOB, Hello World.");
        return ReturnT.SUCCESS;
    }
}

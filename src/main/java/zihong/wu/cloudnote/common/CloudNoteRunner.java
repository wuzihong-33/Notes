package zihong.wu.cloudnote.common;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import zihong.wu.cloudnote.common.config.CommonConfig;
import zihong.wu.cloudnote.common.util.RequestUtils;

import javax.annotation.Resource;

public class CloudNoteRunner implements ApplicationRunner {
    @Resource
    private CommonConfig config;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        RequestUtils.port = config.port;
        RequestUtils.address = config.address;
    }
}

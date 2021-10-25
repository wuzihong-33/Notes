package zihong.wu.cloudnote.common;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import zihong.wu.cloudnote.common.config.CommonConfig;
import zihong.wu.cloudnote.common.config.PublicConstant;
import zihong.wu.cloudnote.common.util.RequestUtils;

import javax.annotation.Resource;
@Component
@Order(value = 1)
public class CloudNoteRunner implements ApplicationRunner {
    @Resource
    private CommonConfig config;

    @Override
    public void run(ApplicationArguments args) {
        PublicConstant.port = config.port;
        PublicConstant.serviceUrl = "https://127.0.0.1:" + config.port;
        PublicConstant.appName = config.appName;
        PublicConstant.mailServerHost = config.mailServerHost;
        PublicConstant.mailServerUser = config.mailServerUser;
        PublicConstant.mailServerPassword = config.mailServerPassword;

        PublicConstant.nginxPath = config.nginxPath;
        PublicConstant.nginxUrl = config.nginxUrl;
        PublicConstant.imgPath = config.imgPath;
        PublicConstant.thumPath = config.thumPath;
        PublicConstant.webUrl = config.webUrl;
    }
}

package zihong.wu.cloudnote.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class CommonConfig {
    /**
     * 项目运行端口
     */
    @Value("${server.port:8080}")
    public String port;

    /**
     * 应用名
     */
    @Value(("${spring.application.name:KellerNote}"))
    public String appName;

    /**
     * 邮件服务器地址
     */
    @Value("${mail.server.host}")
    public  String mailServerHost;
    /**
     * 发件人名称
     */
    @Value("${mail.server.user}")
    public  String mailServerUser;
    /**
     * 发件人密码
     */
    @Value("${mail.server.password}")
    public  String mailServerPassword;


    @Value("${nginx.path}")
    public String nginxPath;

    @Value("${nginx.url}")
    public String nginxUrl;

    @Value("${img.path}")
    public String imgPath;

    @Value("${thum.path}")
    public String thumPath;

    @Value("${web.url}")
    public String webUrl;
}

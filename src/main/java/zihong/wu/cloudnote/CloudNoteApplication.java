package zihong.wu.cloudnote;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;
import zihong.wu.cloudnote.common.response.ResultData;
import zihong.wu.cloudnote.common.util.Console;
import zihong.wu.cloudnote.common.util.RestTemplateUtils;
import zihong.wu.cloudnote.common.util.StringUtils;
import zihong.wu.cloudnote.entity.UserInfo;
import zihong.wu.cloudnote.mapper.UserMapper;
import zihong.wu.cloudnote.service.EmailService;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.lang.annotation.Annotation;
import java.util.List;

@SpringBootApplication

public class CloudNoteApplication {
    @Bean
    /**
     * 引入RestTemplate Bean
     * 用来进行服务间的Http通信
     * 同时重新定义其解析时用到的字符集，防止中文乱码
     */
    RestTemplate restTemplate(){
        return RestTemplateUtils.getHttpsTemplate();

    }
    public static void main(String[] args) {
        SpringApplication.run(CloudNoteApplication.class, args);
    }

}

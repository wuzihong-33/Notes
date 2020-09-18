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
import zihong.wu.cloudnote.common.util.StringUtils;
import zihong.wu.cloudnote.entity.UserInfo;
import zihong.wu.cloudnote.mapper.UserMapper;
import zihong.wu.cloudnote.service.EmailService;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.lang.annotation.Annotation;
import java.util.List;

@SpringBootApplication
@MapperScan("zihong.wu.cloudnote.mapper")
//@PropertySource("application.yml")
//@PropertySource("classpath:application.yml")
@PropertySource(value = "classpath:application.yml")

public class CloudNoteApplication {
    @Value("${spring.datasource.url}")
    String jdbcUrl;

    @Value("${spring.datasource.username}")
    String jdbcUsername;

    @Value("${spring.datasource.password}")
    String jdbcPassword;


    @Bean
    DataSource createDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(jdbcUsername);
        config.setPassword(jdbcPassword);
        config.addDataSourceProperty("autoCommit", "true");
        config.addDataSourceProperty("connectionTimeout", "5");
        config.addDataSourceProperty("idleTimeout", "60");
        return new HikariDataSource(config);
    }
    @Bean
    SqlSessionFactoryBean createSqlSessionFactoryBean(@Autowired DataSource dataSource) {
        var sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        return sqlSessionFactoryBean;
    }


    @Bean
    /**
     * 引入RestTemplate Bean 用来进行服务间的Http通信
     * 同时重新定义其解析时用到的字符集，防止中文乱码
     */
    RestTemplate restTemplate() {
        //创建一个 RestTemplate 实例
        RestTemplate restTemplate = new RestTemplate();
        //清除掉原有的消息转换器，因为这些转换器处理中文字符的能力有限，比较容易出现乱码
        restTemplate.getMessageConverters().clear();
        //为 RestTemplate 实例指定 FastJson 的消息转换器
        restTemplate.getMessageConverters().add(new FastJsonHttpMessageConverter());
        //返回配置好的 RestTemplate 实例
        return restTemplate;
    }

    public static void main(String[] args) {
        SpringApplication.run(CloudNoteApplication.class, args);
    }


}

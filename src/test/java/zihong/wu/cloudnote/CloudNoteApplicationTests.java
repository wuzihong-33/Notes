package zihong.wu.cloudnote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import zihong.wu.cloudnote.common.util.StringUtils;
import zihong.wu.cloudnote.entity.UserInfo;
import zihong.wu.cloudnote.mapper.UserMapper;
import zihong.wu.cloudnote.service.UserService;

import javax.annotation.Resource;
import java.util.Arrays;

@SpringBootTest
class CloudNoteApplicationTests {


    public static void main(String[] args) {
        String str = "type";
        char[] cs = str.toCharArray();
//        System.out.println(Arrays.toString(cs));
        cs[0] -= 32;
//        System.out.println(Arrays.toString(cs));

        // 进行字母的ascii编码前移，效率要高于截取字符串进行转换的操作
//        cs[0] -= -32;
        System.out.println(String.valueOf(cs));


    }


}

package zihong.wu.cloudnote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import zihong.wu.cloudnote.common.util.Console;
import zihong.wu.cloudnote.common.util.StringUtils;
import zihong.wu.cloudnote.entity.UserInfo;
import zihong.wu.cloudnote.mapper.UserMapper;

import javax.annotation.Resource;

@SpringBootTest
public class UserMapperTest {

    @Resource
    private UserMapper userMapper;

    @Test
    void contextLoads() {
    }

    @Test
    public void initTestData(){
        for(int i = 0 ; i < 10 ; i++){
            UserInfo userInfo = new UserInfo();
            userInfo.setType(1);
            userInfo.setEmail(StringUtils.getAllCharString(10));
            userInfo.setPassword("123456");
            userMapper.baseInsertAndReturnKey(userInfo);
            Console.println(userInfo.getId() + "",userInfo);
        }
    }

//    @Test
//    public void getAll(){
//        List<UserInfo> list = userMapper.baseSelectAll(new UserInfo());
//        for(UserInfo userInfo : list){
//            Console.println(userInfo.getId() + "",userInfo);
//        }
//    }
//    @Test
//    public void createUserInfoTable(){
//        userMapper.baseCreate(new UserInfo());
//    }



}

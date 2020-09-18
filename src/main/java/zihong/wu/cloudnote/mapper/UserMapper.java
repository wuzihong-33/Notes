package zihong.wu.cloudnote.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import zihong.wu.cloudnote.common.mybatis.BaseMapper;
import zihong.wu.cloudnote.entity.UserInfo;
import java.util.List;

/**
 * 用@Mapper 注解将 UserMapper 接口标注为一个持久层组件，交给Spring管理
 * 持久层组件只负责执行增删改查操作，不参与业务逻辑处理
 */

@Mapper
public interface UserMapper extends BaseMapper<UserInfo> {
    @Select("select id, type, email, password, createTime, status, isDelete, updateTime, updateUserId " +
            "from user_info")
    List<UserInfo> selectAll();

    @Select("select id, type, email, password, createTime, status, isDelete, updateTime, updateUserId " +
            "from user_info where id = #{id}")
    UserInfo selectById(int id);

    @Select("Select id, type, email, password, createTime, status, isDelete, updateTime, updateUserId " +
            "from user_info where email = #{email} ")
    UserInfo selectByEmail(String email);

    @Insert("Insert into user_info (id, type, email, password, createTime, status, isDelete) " +
            "values(#{id}, #{type}, #{email}, #{password}, #{createTime}, #{status}, #{isDelete})")
    @Options(useGeneratedKeys=true,keyProperty = "id", keyColumn = "id")
    Integer insertAndReturnKey(UserInfo userInfo);

}

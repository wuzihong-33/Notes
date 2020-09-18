package zihong.wu.cloudnote.common.mybatis.provider;

import zihong.wu.cloudnote.common.mybatis.BaseEntity;
import zihong.wu.cloudnote.common.mybatis.BaseException;
import zihong.wu.cloudnote.common.mybatis.SqlFieldReader;

/**
 * 创建表的Provider
 */

public class BaseCreateProvider {

    public static <T extends BaseEntity> String create(T entity) throws BaseException {
        return SqlFieldReader.getCreateTableSql(entity);
    }


}

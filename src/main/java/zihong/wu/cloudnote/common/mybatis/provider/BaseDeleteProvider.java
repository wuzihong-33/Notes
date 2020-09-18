package zihong.wu.cloudnote.common.mybatis.provider;

import zihong.wu.cloudnote.common.mybatis.BaseEntity;
import zihong.wu.cloudnote.common.mybatis.SqlFieldReader;
import zihong.wu.cloudnote.common.util.Console;
import zihong.wu.cloudnote.common.util.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BaseDeleteProvider {

    public static Map<String,String> deleteByIdMap = new ConcurrentHashMap<>(16);

    /**
     * 根据id，删除数据，要求要有id字段
     * @param entity
     * @param <T>
     * @return DELETE FROM tableName WHERE id = #{id}；
     */
    public static <T extends BaseEntity> String deleteById(T entity){
        Class cls = entity.getClass();
        String className = cls.getName();
        String sql = deleteByIdMap.get(className);
        if(StringUtils.isEmpty(sql)){
            sql = getDeletePrefix(entity) + " WHERE id = #{id} ";
            deleteByIdMap.put(className,sql);
        }
        Console.info("deleteById",sql,entity);
        return sql;
    }

    public static <T extends BaseEntity> String deleteByKey(T entity){
        try {
            String sql =  getDeletePrefix(entity) + SqlFieldReader.getConditionByKeySuffix(entity);
            Console.info("deleteByKey",sql,entity);
            return sql;
        }catch (Exception e){
            return null;
        }
    }

    public static <T extends BaseEntity> String deleteByCondition(T entity){
        String sql = getDeletePrefix(entity) + SqlFieldReader.getConditionSuffix(entity);
        Console.info("deleteByCondition",sql,entity);
        return sql;
    }

    private static <T extends BaseEntity> String getDeletePrefix(T entity){
        return "DELETE FROM " + SqlFieldReader.getTableName(entity) + " ";
    }
}

package zihong.wu.cloudnote.common.mybatis.provider;

import zihong.wu.cloudnote.common.mybatis.BaseEntity;
import zihong.wu.cloudnote.common.mybatis.SqlFieldReader;
import zihong.wu.cloudnote.common.util.Console;
import zihong.wu.cloudnote.common.util.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BaseSelectProvider {

    public static Map<String,String> selectPrefixWithDetailedMap = new ConcurrentHashMap<>(16);
    public static Map<String,String> selectPrefixMap = new ConcurrentHashMap<>(16);

    /**
     * 根据Id进行条件查询
     * @param entity
     * @param <T>
     * @return SELECT 所有字段 FROM 表名 WHERE id = #{id};
     */
    public static <T extends BaseEntity> String selectById(T entity){
        String sql = getSelectPrefix(entity) + " WHERE id = #{id}";
        Console.info("selectById",sql,entity);
        return sql;
    }

    /**
     * 根据主键查询数据，要求至少有一个主键，且主键必须有值
     * @param entity
     * @param <T>
     * @return SELECT 所有字段 FROM 表名 WHERE primaryKey = #{primaryKey};
     */
    public static <T extends BaseEntity> String selectByKey(T entity){
        try {
            String sql = getSelectPrefix(entity) + SqlFieldReader.getConditionByKeySuffix(entity);
            Console.info("selectByKey",sql,entity);
            return sql;
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 查询所有数据,不带条件查询
     * @param entity
     * @param <T>
     * @return SELECT 字段 FROM tableName
     */
    public static <T extends BaseEntity> String selectAll(T entity){
        String sql = getSelectPrefix(entity);
        Console.info("selectAll",sql,entity);
        return sql;
    }


    /**
     * 带条件的查询，查询为动态查询，所以不可缓存
     * 传入对象中带@SortAttribute注解的字段作为排序字段
     * 传入的对象中带@IndexAttribute注解的字段有值的都作为查询条件
     * @param entity
     * @param <T>
     * @return SELECT id,name... FROM router  WHERE name = #{name} AND serviceName = #{serviceName}  ORDER BY createTime ASC
     */
    public static <T extends BaseEntity> String selectByCondition(T entity){
        String sql = getSelectPrefix(entity)
                + SqlFieldReader.getConditionSuffix(entity)
                + SqlFieldReader.getSortSuffix(entity);
        Console.info("selectByCondition",sql,entity);
        return sql;
    }


    /**
     * 查询记录总数
     * @param entity
     * @param <T>
     * @return SELECT COUNT(1) FROM router
     */
    public static <T extends BaseEntity> String selectCount(T entity){
        String sql = "SELECT COUNT(1) FROM " + SqlFieldReader.getTableName(entity);
        Console.info("selectCount",sql,entity);
        return sql;
    }

    /**
     * 根据条件查询记录总数
     * 传入的对象中带@IndexAttribute注解的字段有值的都作为查询条件
     * @param entity
     * @param <T>
     * @return SELECT COUNT(1) FROM router WHERE name = #{name} AND serviceName = #{serviceName}
     */
    public static <T extends BaseEntity> String selectCountByCondition(T entity){
        String sql = selectCount(entity) + SqlFieldReader.getConditionSuffix(entity);
        Console.info("selectCountByCondition",sql,entity);
        return sql;
    }


    /**
     * 不加条件的分页查询
     * @param entity
     * @param <T>
     * @return SELECT id,name... FROM router  LIMIT #{startRows},#{pageSize}
     */
    public static <T extends BaseEntity> String selectPageList(T entity){
        String sql = selectAll(entity) + " LIMIT #{baseKyleStartRows},#{baseKylePageSize}";
        Console.info("selectPageList",sql,entity);
        return sql;
    }

    /**
     * 加条件的分页查询
     * 传入的对象中带@IndexAttribute注解的字段有值的都作为查询条件
     * @param entity
     * @param <T>
     * @return
     */
    public static <T extends BaseEntity> String selectPageListByCondition(T entity){
        String sql = selectByCondition(entity) + " LIMIT #{baseKyleStartRows},#{baseKylePageSize}";
        Console.info("selectPageListByCondition",sql,entity);
        return sql;
    }

    /**
     * 获取查询的通用前缀
     * @param entity
     * @param <T>
     * @return SELECT 所有字段 FROM 表名
     */
    private static <T extends BaseEntity> String getSelectPrefix(T entity) {
        String className = entity.getClass().getName();
        String sql;
        if(entity.isBaseKyleDetailed()){
            sql = selectPrefixWithDetailedMap.get(className);
        }else {
            sql = selectPrefixMap.get(className);
        }
        if(StringUtils.isEmpty(sql)){
            sql = "SELECT " + SqlFieldReader.getFieldStr(entity) + " FROM " + SqlFieldReader.getTableName(entity) + " ";
            if(entity.isBaseKyleDetailed()){
                selectPrefixWithDetailedMap.put(className,sql);
            }else {
                selectPrefixMap.put(className,sql);
            }
        }
        return sql;
    }


}

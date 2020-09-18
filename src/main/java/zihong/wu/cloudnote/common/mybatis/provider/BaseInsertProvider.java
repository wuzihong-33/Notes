package zihong.wu.cloudnote.common.mybatis.provider;

import zihong.wu.cloudnote.common.mybatis.BaseEntity;
import zihong.wu.cloudnote.common.mybatis.SqlFieldReader;
import zihong.wu.cloudnote.common.util.Console;
import zihong.wu.cloudnote.common.util.StringUtils;
import zihong.wu.cloudnote.entity.EmailLog;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BaseInsertProvider {

    /**
     * 缓存insert语句
     */
    public static Map<String,String> insertMap = new ConcurrentHashMap<>(16);
    public static Map<String,String> insertAndReturnKeyMap = new ConcurrentHashMap<>(16);

    /**
     * 生成insert语句，格式类似于：INSERT INTO user_info (email,password) VALUES (#{email},#{password});
     * @param entity
     * @param <T>
     * @return
     */
    public static <T extends BaseEntity> String insert(T entity) {
        Class cls = entity.getClass();
        String className = cls.getName();
        String sql = insertMap.get(className);
        if (StringUtils.isEmpty(sql)) {
            String fieldStr = SqlFieldReader.getFieldStr(entity);
            StringBuilder builder = new StringBuilder();

            builder.append("INSERT INTO ")
                    .append(SqlFieldReader.getTableName(entity)).append(" ")
                    .append("(").append(fieldStr).append(")")
                    .append("VALUES(");

            StringBuilder valueStr = new StringBuilder();
            String[] fields = fieldStr.split(",");
            for (String field : fields) {
                valueStr.append("#{").append(field).append("}").append(",");
            }
            builder.append(valueStr,0,valueStr.length()-1).append(")");
            sql = builder.toString();
            insertMap.put(className, sql);
        }
        Console.info("insert", sql, entity);
        return sql;
    }

    /**
     * 无需添加id
     * @param entity
     * @param <T>
     * @return INSERT INTO user_info (email,password) VALUES (#{email},#{password});
     */
    public static <T extends BaseEntity> String insertAndReturnKey(T entity) {
        Class cls = entity.getClass();
        String className = cls.getName();
        String sql = insertAndReturnKeyMap.get(className);
        if(StringUtils.isEmpty(sql)){
            String fieldStr = SqlFieldReader.getFieldStr(entity);
            String[] arrays = fieldStr.split(",");

            StringBuilder builder = new StringBuilder();

            StringBuilder valuesStr = new StringBuilder();

            builder.append("INSERT INTO ")
                    .append(SqlFieldReader.getTableName(entity)).append(" ")
                    .append("(");
            for(String str : arrays){
                if("id".equals(str)){
                    continue;
                }
                valuesStr.append(str).append(",");
            }

            builder.append(valuesStr.substring(0,valuesStr.length() - 1));
            builder.append(") ").append("VALUES(");

            valuesStr = new StringBuilder();
            for(String str : arrays){
                if("id".equals(str)){
                    continue;
                }
                valuesStr.append("#{").append(str).append("}").append(",");
            }

            builder.append(valuesStr.substring(0,valuesStr.length() - 1))
                    .append(")");
            sql = builder.toString();
            insertAndReturnKeyMap.put(className,sql);
        }
        Console.info("insertAndReturnKey",sql,entity);
        return sql;
    }




}

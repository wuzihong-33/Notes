package zihong.wu.cloudnote.common.mybatis;

import zihong.wu.cloudnote.common.mybatis.annotation.*;
import zihong.wu.cloudnote.common.util.Console;
import zihong.wu.cloudnote.common.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Provider工具类
 * 提供：获取表表名、字段名等公共方法
 * getTableName()、getFieldStr()、getConditionSuffix()、getSortSuffix()、getFields()
 */
public class SqlFieldReader {
    /**
     * 读取表名，要求类上有@TableAttribute
     * @param entity
     * @param <T>
     * @return
     */
    public static <T extends BaseEntity> String getTableName(T entity) {
        TableAttribute table = entity.getClass().getAnnotation(TableAttribute.class);
        if (table == null) {
            return null;
        }
        return table.name();
    }

    /**
     * 返回查询字段,column1,column2...
     * isBaseKyleDetailed()判断entity是查询明细字段还是非明细字段
     * 查询明细字段显示明细字段，不查询明细字段返回非明细字段
     * @param <T>
     * @return
     */
    public static <T extends BaseEntity> String getFieldStr(T entity) {
        Class cls = entity.getClass();
        Field[] fields = cls.getDeclaredFields();
        // 带@FieldAttribute注解的属性名，构造查询 *字段
        StringBuilder builder = new StringBuilder();

        // 所有属性名
        StringBuilder allFields = new StringBuilder();

        for (Field field : fields) {
            allFields.append(field.getName()).append(",");
            // field上有@FieldAttribute标注
            if (field.getAnnotation(FieldAttribute.class) != null) {
                FieldAttribute fieldAttribute = field.getAnnotation(FieldAttribute.class);
                // 判断entity是否查询明细字段，查询明细字段则返回明细字段，不查询明细字段则显示非明细字段
                if (entity.isBaseKyleDetailed()) {
                    builder.append(field.getName()).append(",");
                // 如果不查询明细字段，则不返回明细字段
                }else {
                    // detailed==false 表明该属性不是明细字段，则查询显示该字段;如果是明细字段，则查询不显示该字段
                    if (!fieldAttribute.detailed()) {
                        builder.append(field.getName()).append(",");
                    }
                }
            }
        }
        if (builder.length() > 0) {
            return builder.substring(0,builder.length()-1);// 去除最后一个","
        // 无被@FieldAttribute标注的属性，则查询 *
        }else if (allFields.length() > 0) {
            return allFields.substring(0,allFields.length()-1);
        // 无任何查询
        }else {
            return null;
        }
    }

    /**
     * 有@IndexAttribute注释且有值
     * @param entity
     * @param <T>
     * @return WHRER name1 = #{name1} {OR||AND name2 = #{name2} }...
     */
    public static <T extends BaseEntity> String getConditionSuffix(T entity) {
        String condition;
        if (entity.getBaseKyleUseAnd() == null) {// 单条件查询
            condition = "";
        }else if (entity.getBaseKyleUseAnd()) {
            condition = " AND ";
        }else {
            condition = " OR ";
        }

        Class cls = entity.getClass();
        Field[] fields = cls.getDeclaredFields();
        StringBuilder builder = new StringBuilder();
        builder.append(" WHERE ");
        try {
            for (Field field : fields) {
                // 有@IndexAttribute注释且有值
                if (field.getAnnotation(IndexAttribute.class) != null) {
                    if (SqlFieldReader.hasValue(entity, field.getName())) {
                        builder.append(field.getName())
                                .append(" = #{").append(field.getName()).append("}")
                                .append(condition);
                    }
                }
            }
            int index = builder.length()-5;
//            int index = builder.lastIndexOf(condition);
            if (index < 0) { // 无带@IndexAttribute注释的file，则返回""，即不使用条件查询
                return "";
            }
            Console.println("sql",builder.substring(0,index));
            return builder.substring(0,index);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 注意，不要return null;
        return "";
    }

    /**
     * 判断一个对象的指定字段是否有值
     * @param entity
     * @param fileName
     * @param <T>
     * @return
     */
    public static <T> boolean hasValue(T entity, String fileName) {
        Class cls = entity.getClass();
        Method method = null;
        try {
            method = cls.getMethod("get" + StringUtils.captureName(fileName));
            if (method.invoke(entity) == null) {
                return false;
            }else {
                return true;
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     *
     * @param entity
     * @param <T>
     * @return ORDER BY column... (ASE,默认)||DESC
     */
    public static <T extends BaseEntity> String getSortSuffix(T entity) {
        String condition;

        if(entity.getBaseKyleUseASC() == null){
            return "";
        }
        if(entity.getBaseKyleUseASC()){
            condition = "ASC";
        }else {
            condition = "DESC";
        }

        Class cls = entity.getClass();
        Field[] fields = cls.getDeclaredFields();

        StringBuilder builder = new StringBuilder();
        builder.append(" ORDER BY ");
        try {
            for (Field field : fields) {
                if (field.getAnnotation(SortAttribute.class) != null) {
                    builder.append(field.getName()).append(" ")
                            .append(condition).append(",");
                }
            }

            int index = builder.lastIndexOf(",");
            if(index < 0){
                return "";
            }
            return builder.substring(0,index);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取主键的查询条件
     * 传入的对象需要有带@keyAttribute注解的字段且要有值
     * @param entity
     * @param <T>
     * @return WHERE primaryKey = #{primaryKey};like WHERE userId = #{userId}
     */
    public static <T> String getConditionByKeySuffix(T entity) throws BaseException{
        Class cls = entity.getClass();
        Field[] fields = cls.getDeclaredFields();
        StringBuilder builder = new StringBuilder();
        builder.append(" WHERE ");
        try {
            for(Field field : fields) {
                if (field.getAnnotation(KeyAttribute.class) != null) {
                    if (hasValue(entity,field.getName())) {
                        builder.append(field.getName())
                                .append(" = #{").append(field.getName()).append("} ");
                    } else {
                        throw new BaseException("@KeyAttribute修饰的字段不能为空");
                    }
                    break;
                }
            }
            int index = builder.lastIndexOf("=");
            if (index < 0) {
                throw new BaseException("没有找到@KeyAttribute修饰的字段");
            }
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw  new BaseException(e.getMessage());
        }
    }






    /**
     * 获取所有字段列表
     * 该方法返回带@FieldAttribute注释的字段，如果都没有带该注解，则返回类中所有字段
     * @param entity
     * @param <T>
     * @return
     */
    public static <T extends BaseEntity> List<String> getFields(T entity) {
        Field[] fields = entity.getClass().getDeclaredFields();

        List<String> fieldList = new ArrayList<>();
        List<String> allList = new ArrayList<>();
        //带@FieldAttribute注解的属性名
        for(Field field : fields){
            allList.add(field.getName());
            if(field.getAnnotation(FieldAttribute.class) != null){
                fieldList.add(field.getName());
            }
        }
        if(fieldList.size() == 0){
            return allList;
        }
        return fieldList;
    }

    public static <T extends BaseEntity> String getCreateTableSql(T entity) throws BaseException {
        TableAttribute table =  entity.getClass().getAnnotation(TableAttribute.class);
        if(table == null){
            throw new BaseException("要解析表名，未发现@TableAttribute注解");
        }
        String tableName = table.name();
        String tableComment = table.comment();

        StringBuilder builder = new StringBuilder();

        builder.append("create table")
                .append(tableName)
                .append("( \n");

        // 添加字段
        builder.append(getAddFieldSql(entity));
        builder.append(") ");

        // 如果有表说明，添加表说明
        if(StringUtils.isNotEmpty(tableComment)){
            builder.append("comment '")
                    .append(tableComment)
                    .append("'; \n");
        }else {
            builder.append("; \n");
        }
        //添加主键
        builder.append(getCreateKeySql(entity));

        //添加索引
        builder.append(getCreateIndexSql(entity));


        Console.print("",builder.toString());
        return builder.toString();

    }

    public static <T extends BaseEntity> String getAddFieldSql(T entity) {
        Field[] fields = entity.getClass().getDeclaredFields();
        StringBuilder builder = new StringBuilder();

        /*
        解析字段描述：是否唯一、是否必填、是否设置了最大长度等
         */
        for(Field field:fields){
            FieldAttribute fieldAttribute = field.getAnnotation(FieldAttribute.class);
            if(fieldAttribute != null){

                builder.append(field.getName())
                        .append(" ")
                        .append(TypeCaster.getType(field.getType().getSimpleName(),fieldAttribute.length()));
                if(fieldAttribute.notNull()){
                    builder.append(" not null ");
                }

                if(fieldAttribute.unique()){
                    builder.append(" unique ");
                }

                //如果有字段说明，添加字段说明
                if(StringUtils.isNotEmpty(fieldAttribute.value())) {
                    builder.append(" comment '")
                            .append(fieldAttribute.value())
                            .append("'");
                }
                builder.append(", \n");
            }
        }
        builder.deleteCharAt(builder.lastIndexOf(","));
        return builder.toString();
    }

    private static <T extends BaseEntity> String getCreateKeySql(T entity) {
        Field[] fields = entity.getClass().getDeclaredFields();
        StringBuilder builder = new StringBuilder();
        for (Field field : fields) {
            KeyAttribute keyAttribute = field.getAnnotation(KeyAttribute.class);
            if (keyAttribute != null) {
                FieldAttribute fieldAttribute = field.getAnnotation(FieldAttribute.class);
                if (fieldAttribute == null) {
                    return "";
                }
                builder.append("alter table ")
                        .append(getTableName(entity))
                        .append(" change ")
                        .append(field.getName())
                        .append(" ")
                        .append(field.getName())
                        .append(" ")
                        .append(TypeCaster.getType(field.getType().getSimpleName(), fieldAttribute.length()));
                if (keyAttribute.autoIncr()) {
                    builder.append(" auto_increment ");
                }
                builder.append(" primary key comment '")
                        .append(fieldAttribute.value())
                        .append("'; \n");
                break;
            }
        }
        return builder.toString();
    }

    /**
     *
     * @param entity
     * @param <T>
     * @return alter table user_info add index user_info_index_type (type);
     */
    public static <T extends BaseEntity> String getCreateIndexSql(T entity){
        String tableName = getTableName(entity);
        StringBuilder builder = new StringBuilder();

        Field[] fields = entity.getClass().getDeclaredFields();
        for(Field field:fields){
            if(field.getAnnotation(IndexAttribute.class) != null){

                builder.append("alter table ")
                        .append(tableName)
                        .append(" add index ")
                        .append(tableName)
                        .append("_index_")
                        .append(field.getName())
                        .append(" (")
                        .append(field.getName())
                        .append("); \n");
            }
        }
        return builder.toString();
    }


}


package zihong.wu.cloudnote.common.util;

import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    /**
     * 纯数字集合
     */
    private static final String numbersChar = "0123456789";

    /**
     * 大小写字母、数字集合
     */
    private static final String allChar = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    /**
     * 手机号校验规则：1开头的11位数字
     */
    private static final String PHONE_NUMBER_PATTERN = "^1+\\d{10}$";

    /**
     * 用户名校验规则：允许字母、数字、下划线
     */
    private static final String USER_NAME_PATTERN = "^[a-zA-Z0-9_]+$";

    /**
     * 邮箱校验规则：**@**.**
     */
    private static final String EMAIL_ADDRESS_PATTERN = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|" +
        "(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";

    /**
     * 校验是否是用户名
     * @param string
     * @return
     */
    public static boolean isUserName(String string) {
        if(isEmpty(string)){
            return false;
        }
        Pattern pattern = Pattern.compile(USER_NAME_PATTERN);
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }

    /**
     * 校验是否是手机号
     * @param string
     * @return
     */
    public static boolean isPhoneNo(String string) {
        if(isEmpty(string)){
            return false;
        }
        Pattern pattern = Pattern.compile(PHONE_NUMBER_PATTERN);
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();

    }


    public static boolean notPhoneOrEmail(String string){
        return !isEmail(string) && !isPhoneNo(string);
    }

    public static boolean isPhoneOrEmail(String string){
        return isEmail(string) || isPhoneNo(string);
    }

    /**
     * 校验是否是邮箱
     * @param string
     * @return
     */
    public static boolean isEmail(String string) {
        if(isEmpty(string)){
            return false;
        }
        Pattern pattern = Pattern.compile(EMAIL_ADDRESS_PATTERN);
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }

    public static boolean notEmail(String string){
        return !isEmail(string);
    }


    /**
     * 只要有一个为空的，就返回 true
     * 判断一系列字符串中是否有空的（包含:空字符串、null、纯空格字符）
     * @param parameters 需要判断的字符串，可以是多个
     * @return
     */
    public static boolean isEmpty(Object... parameters){
        for(Object param:parameters){
            if(param == null){
                return true;
            }
            if(param instanceof String){
                String str = (String) param;
                if(str.isEmpty()){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 仅当所有元素都有值才返回 true
     * 判断一组对象中没有空值，仅当所有参数都有值时才会返回 true
     * @param parameters
     * @return
     */
    public static boolean noEmpty(Object... parameters){
        return !isEmpty(parameters);
    }

    /**
     * 只要有一个元素有值，就返回 true
     * 判断一组元素是否有值
     * @param parameters
     * @return
     */
    public static boolean hasValue(Object ... parameters){
        for(Object param:parameters){
            if(param != null){
                if(param instanceof String){
                    String str = (String)param;
                    return !str.isEmpty();
                }else{
                    return true;
                }
            }

        }
        return false;
    }

    /**
     * 仅当所有元素都没值才返回true
     * @param parameters
     * @return
     */
    public static boolean noValue(Object ... parameters){
        return !hasValue(parameters);
    }


    /**
     * 将字符串的首字母转大写
     * @param str 需要转换的字符串
     * @return
     */
    public static String captureName(String str) {
        // 进行字母的ascii编码前移，效率要高于截取字符串进行转换的操作
        char[] cs=str.toCharArray();
        cs[0]-=32;
        return String.valueOf(cs);
    }

    /**
     * 生成指定长度的数字字符串
     * @param length
     *  字符串长度
     * @return
     */
    public static String getNumberString(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(numbersChar.charAt(random.nextInt(numbersChar.length())));
        }
        return sb.toString();
    }

    /**
     * 生成指定长度的字符串（含大小写字母及数字）
     * @param length
     * @return
     */
    public static String getAllCharString(int length){
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(allChar.charAt(random.nextInt(allChar.length())));
        }
        return sb.toString();
    }

    /**
     * 获取UUID(32位的字母数字的集合)
     * @return
     */
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-","").toUpperCase();
    }

    public static void main(String[] args){
        Console.print("",isEmail("guyexing@cc.com"));
        Console.print("UUID",getUUID());
    }
}

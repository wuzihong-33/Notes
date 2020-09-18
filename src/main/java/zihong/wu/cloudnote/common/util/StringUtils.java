package zihong.wu.cloudnote.common.util;

import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    private static final String numbersChar = "0123456789";
    private static final String allChar = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    /**
     * 手机校验规则：1开头的11位数字
     */
    private static final String PHONE_NUMBER_PATTERN = "^1+\\d{10}$";

    /**
     * 用户名校验规则：允许字母、数字、下划线
     */
    private static final String USER_NAME_PATTERN = "^[a-zA-Z0-9_]+$";

    /**
     * 邮件校验规则：**@**.**
     */
    private static final String EMAIL_ADDRESS_PATTERN = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+" +
            "@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|" +
            "(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";

    /**
     * 校验用户名
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
     * 判断一组字符串是否有值
     * 只要有一个字符串有值，就返回 true
     * @param parameters
     * @return
     */
    public static boolean hasValue(String ... parameters){
        for(String str:parameters){
            if(str != null && !str.trim().isEmpty()){
                return true;
            }
        }
        return false;
    }


    /**
     * 校验手机格式
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

    /**
     * 校验邮箱格式
     * @param string
     * @return
     */
    public static boolean isEmail(String string) {
        if (isEmpty(string)) {
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
     * 既不是邮箱也不是手机号
     * @param string
     * @return
     */
    public static boolean notPhoneOrEmail(String string){
        return !isEmail(string) && !isPhoneNo(string);
    }

    /**
     * 满足手机号或者邮箱
     * @param string
     * @return
     */
    public static boolean isPhoneOrEmail(String string){
        return isEmail(string) || isPhoneNo(string);
    }


    /**
     * return true if str == null || isEmpty() || 空字符串
     * @param parameters
     * @return
     */
    public static boolean isEmpty(String... parameters) {
        for (String str : parameters) {
            if (str == null || str.isEmpty() || str.trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }
    public static boolean isNotEmpty(String... parameters){
        return !isEmpty(parameters);
    }

    /**
     * 将字符串首字母转大写
     * @param str
     * @return
     */
    public static String captureName(String str) {
        str = str.substring(0, 1).toUpperCase() + str.substring(1);
        return  str;
//        Console.print(str);
//        char[] cs = str.toCharArray();
//
//        // 进行字母的ascii编码前移，效率要高于截取字符串进行转换的操作
//        cs[0] -= -32;
//        Console.print("getXxxx",str,String.valueOf(cs));
//        return String.valueOf(cs);
    }

    /**
     * 随机生成指定长度的数字字符串
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
     * 生成指定长度的任意字符字符串
     * @param length
     * @return
     */
    public static String getAllCharString(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(allChar.charAt(random.nextInt(allChar.length())));
        }
        return sb.toString();
    }
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-","").toUpperCase();
    }



}

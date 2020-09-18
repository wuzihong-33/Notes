package zihong.wu.cloudnote.common.config;

/**
 * 项目中要用到的一些公共常量
 */
public class PublicConstant {

    public static final int DEFAULT_USER_TYPE = 0;

    /**
     * 业务成功标示
     */
    public static final int SUCCESS  = 0;
    /**
     * 业务失败标示
     */
    public static final int FAILED = 1;

    /**
     * 注册类型
     */
    public static final int REGISTER_TYPE = 0;

    /**
     * 登录类型
     */
    public static final int LOGIN_TYPE = 1;

    /**
     * 重置密码类型
     */
    public static final int RESET_PASSWORD_TYPE = 2;

    /**
     * 邮件验证码有效期
     */
    public static final int EMAIL_CODE_TIME = 5;

    /**
     * 邮件验证码长度
     */
    public static final int EMAIL_CODE_LENGTH = 6;

    /**
     * JWT 有效时间 12 小时
     */
    public static final long JWT_EXP_TIME = 12 * 60 * 60 * 1000;

    /**
     * JWT 签名密钥
     */
    public static final String JWT_SIGN_KEY = "kellerNotes20241002";


    /**
     * 应用启动的端口号
     */
    public static String port ;

    /**
     * 应用运行的 IP 地址
     */
    public static String address ;

    /**
     * 管理员的用户类型
     */
    public static final int ADMIN_USER_TYPE = 100;


    /**
     * 应用名
     */
    public static String appName ;

    /**
     * 邮件服务器登录密码
     */

    public static String nginxPath;

    public static String nginxUrl;

    public static String imgPath;

    public static String thumPath;

    /**
     * 缩略图前缀名
     */
    public static final String THUM_PREFIX = "thum";

    /**
     * 缩略图最大宽度
     */
    public static final int THUM_MAX_WIDTH = 120;

    /**
     * 缩略图最大高度
     */
    public static final int THUM_MAX_HEIGHT = 120;

    public static final String USER_ID_KEY = "kellerUserId";

    public static final String ADMIN_ID_KEY = "kellerAdminId";


    /**
     * 服务器访问地址
     */
    public static String serviceUrl = "http://" + address + ":" + port;

    /**
     * 邮件服务器地址
     */
    public static String mailServerHost = "smtp.qq.com";


    /**
     * 邮件服务器登录用户名
     */
    public static String mailServerUser = "965297162@qq.com";


    /**
     * 邮件服务器登录密码
     */
    public static String mailServerPassword = "btehgvxvmyhgbaja";

    /**
     * 通用，不做访问权限设置
     */
    public static final int AUTHORITY_COMMON = 1 << 0;

    /**
     * 用户登录后可以访问
     */
    public static final int AUTHORITY_LOGON  = 1 << 1;

    /**
     * 管理员可以访问
     */
    public static final int AUTHORITY_ADMIN   = 1 << 2;

    /**
     * 超级管理员可以访问
     */
    public static final int AUTHORITY_SUPPER_ADMIN = 1 << 3;


    public static boolean notUserType(Integer userType){
        return !isUserType(userType);
    }

    public static boolean isUserType(Integer userType){
        if(userType == null){
            return false;
        }
        return userType == DEFAULT_USER_TYPE || userType == ADMIN_USER_TYPE;
    }


}

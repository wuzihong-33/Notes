package zihong.wu.cloudnote.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import zihong.wu.cloudnote.common.config.PublicConstant;
import zihong.wu.cloudnote.entity.UserInfo;

import java.util.Date;

public class JwtUtils {

    //定义用户名称（即：注册邮箱）的键名
    private static String userNameKey = "userName";

    //定义用户类型的键名
    private static String userTypeKey = "userType";

    /**
     * 生成 JWT
     * @return
     */
    public static String getJwtString(UserInfo userInfo){
        //获取当前的毫秒数
        long now = System.currentTimeMillis();
        //构建 JWT
        JwtBuilder jwtBuilder = Jwts.builder()
                //设置 JWT 的 ID，在这里使用用户 ID 作为 JWT 的 ID
                .setId(userInfo.getId() + "")
                //设置应用名,作为其中一个校验条件
                .setSubject(PublicConstant.appName)
                //签发时间
                .setIssuedAt( new Date() )
                //过期时间
                .setExpiration( new Date( now + PublicConstant.JWT_EXP_TIME ) )
                //自定义内容：注册邮箱
                .claim(userNameKey,userInfo.getEmail())
                //自定义内容：用户类型
                .claim(userTypeKey,userInfo.getType())
                //指定使用 HS256 方式进行加密，同时指定加密密钥
                .signWith( SignatureAlgorithm.HS256, PublicConstant.JWT_SIGN_KEY);
        //生成 JWT
        return jwtBuilder.compact();
    }


    /**
     * 解析 JWT
     * @return
     */
    public static UserInfo getUser(String jwtString){
        try {
            Claims claims = Jwts.parser()
                    //指定解密时的密钥
                    .setSigningKey(PublicConstant.JWT_SIGN_KEY)
                    //尝试解析 JWT
                    .parseClaimsJws(jwtString)
                    //获取到 JWT 中的信息
                    .getBody();
            //解析出 JWT ID，即用户 ID
            int id = Integer.parseInt(claims.getId());
            //解析出应用名
            String subject = claims.getSubject();
            //校验应用名
            if(!subject.equals(PublicConstant.appName)){
                return null;
            }
            //创建一个 UserInfo 对象，以接收从 JWT 中解析出的信息
            UserInfo userInfo = new UserInfo();
            //设置用户 ID
            userInfo.setId(id);
            //设置邮箱
            userInfo.setEmail(claims.get(userNameKey,String.class));
            //设置用户类型
            userInfo.setType(claims.get(userTypeKey,Integer.class));
            return userInfo;
        }catch (Exception e){
            Console.error("checkJwt","JWT 解析失败",jwtString,e.getMessage());
            return null;
        }
    }

    /**
     * 从JWT中获取到UserId
     * @param jwtString
     * @return
     */
    public static Integer getUserId(String jwtString){
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(PublicConstant.JWT_SIGN_KEY)
                    .parseClaimsJws(jwtString)
                    .getBody();
            int id = Integer.parseInt(claims.getId());
            String subject = claims.getSubject();
            //校验应用名
            if(!subject.equals(PublicConstant.appName)){
                return null;
            }
            return id;
        }catch (Exception e){
            e.printStackTrace();
            Console.error("checkJwt","JWT 解析失败",jwtString,e.getMessage());
            return null;
        }
    }





}

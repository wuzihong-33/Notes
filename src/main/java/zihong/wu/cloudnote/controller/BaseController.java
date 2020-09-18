package zihong.wu.cloudnote.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zihong.wu.cloudnote.common.config.PublicConstant;
import zihong.wu.cloudnote.common.response.Response;
import zihong.wu.cloudnote.common.util.Console;
import zihong.wu.cloudnote.common.util.StringUtils;
import zihong.wu.cloudnote.entity.UserInfo;
import zihong.wu.cloudnote.service.UserService;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 不需要登录就能调用的接口
 */
@RestController
@RequestMapping("/base")
@CrossOrigin(origins = "*",allowedHeaders="*", maxAge = 3600)
public class BaseController {
    @Resource
    private UserService userService;

    /**
     * 注册功能
     */
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody Map<String,String> params){
        Console.info("register",params);
        String email = params.get("email");
        String password = params.get("password");
        String code = params.get("code");
        if(StringUtils.isEmpty(password,code) || StringUtils.notEmail(email)){
            return Response.badRequest();
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setEmail(email);
        userInfo.setPassword(password);
        userInfo.setType(PublicConstant.DEFAULT_USER_TYPE);

        return Response.ok(userService.register(userInfo,code));
    }

    /**
     * 发送登录验证码
     * @param type
     * @param email
     * @return
     */
    @GetMapping("/getCodeForLogin")
    public ResponseEntity getCodeForLogin(Integer type,String email){
        Console.info("getCodeForLogin",email);

        //验证邮箱和用户类型的合法性
        if(StringUtils.notEmail(email) || PublicConstant.notUserType(type)){
            return Response.badRequest();
        }
        return Response.ok(userService.sendEmailCode(
                type,PublicConstant.LOGIN_TYPE,email));
    }


    /**
     * 获取验证码
     * @param email
     * @return
     */
    @GetMapping("/getCodeForRegister")
    public ResponseEntity getCode(String email){
        Console.info("getCodeForRegister",email);
        if(StringUtils.notEmail(email)){
            return Response.badRequest();
        }
        return Response.ok(userService.sendRegisterCode(PublicConstant.DEFAULT_USER_TYPE,email));
    }

    /**
     * 账号密码登录
     * @param params
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody Map<String,String> params){
        Console.info("login",params);
        String email = params.get("email");
        String password = params.get("password");
        int type = Integer.parseInt(params.get("type"));
        if(StringUtils.isEmpty(password) || StringUtils.notEmail(email)){
            return Response.badRequest();
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setEmail(email);
        userInfo.setPassword(password);
        userInfo.setType(type);

        return Response.ok(userService.login(userInfo));
    }

    /**
     * 验证码登录
     * @param params
     * @return
     */
    @PostMapping("/loginWithCode")
    public ResponseEntity loginWithCode(@RequestBody Map<String,String> params){
        Console.info("login",params);
        String email = params.get("email");
        String code = params.get("code");
        int type = Integer.parseInt(params.get("type"));
        //校验参数
        if(StringUtils.isEmpty(code) || StringUtils.notEmail(email) || PublicConstant.notUserType(type)){
            return Response.badRequest();
        }
        return Response.ok(userService.loginWithCode(email,type,code));
    }


}

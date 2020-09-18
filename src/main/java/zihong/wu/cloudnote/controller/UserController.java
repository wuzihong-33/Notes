package zihong.wu.cloudnote.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zihong.wu.cloudnote.common.response.Response;
import zihong.wu.cloudnote.common.response.ResultData;
import zihong.wu.cloudnote.common.util.StringUtils;
import zihong.wu.cloudnote.entity.UserInfo;
import zihong.wu.cloudnote.service.UserService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 登录后才能被调用的接口
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @GetMapping
    public ResponseEntity getAll(){
        ResultData resultData = userService.getAll();
        return Response.ok(resultData);
    }

    @GetMapping("/getByEmail")
    public ResponseEntity getByEmail(String email){
        if(StringUtils.isEmpty(email)){
            return Response.badRequest();
        }
        ResultData resultData = userService.getByEmail(email);
        return Response.ok(resultData);
    }
}

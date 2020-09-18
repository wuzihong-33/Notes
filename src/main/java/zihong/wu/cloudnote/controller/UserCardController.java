package zihong.wu.cloudnote.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zihong.wu.cloudnote.common.response.Response;
import zihong.wu.cloudnote.common.util.StringUtils;
import zihong.wu.cloudnote.service.UserCardService;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@RestController
@RequestMapping("/userCard")
public class UserCardController {
    @Resource
    private UserCardService service;

    @PostMapping
    public ResponseEntity setUserCard(Integer kellerUserId, String nickName, String email, String label){
        if(kellerUserId != null && StringUtils.hasValue(nickName,email,label)){
            return Response.ok(
                    service.setUserCard(kellerUserId,nickName,email,label));
        }
        return Response.badRequest();
    }


}

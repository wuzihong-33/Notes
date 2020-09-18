package zihong.wu.cloudnote.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import zihong.wu.cloudnote.common.config.RequestConfig;
import zihong.wu.cloudnote.common.response.Response;
import zihong.wu.cloudnote.common.response.ResultData;
import zihong.wu.cloudnote.common.util.JwtUtils;
import zihong.wu.cloudnote.service.UserCardService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/upload")
@CrossOrigin(origins = "*",allowedHeaders="*", maxAge = 3600)
public class UploadController {
    @Resource
    private UserCardService userCardService;

    //上传头像
    @PostMapping
    public ResponseEntity upload(MultipartFile file, HttpServletRequest request){
        //如果读取不到文件流，返回 400
        if(file == null){
            return Response.badRequest();
        }
        // 获取 JWT
        String token = request.getHeader(RequestConfig.TOKEN);
        //解析出 userId
        Integer userId = JwtUtils.getUserId(token);
        if(userId == null){
            return Response.unauthorized();
        }
        ResultData resultData = userCardService.setPortrait(file,userId);
        return Response.ok(resultData);
    }




}

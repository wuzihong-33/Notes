package zihong.wu.cloudnote.common.proxy;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import zihong.wu.cloudnote.common.response.Response;
import zihong.wu.cloudnote.common.util.RequestUtils;
import zihong.wu.cloudnote.common.util.ResponseUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 接口转发 JSON格式
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*",allowedHeaders="*", maxAge = 3600)
public class ApiController {
    @Resource
    HttpServletRequest request;

    @Autowired
    RestTemplate restTemplate;

    // TODO 转发前校验JWT和用户权限

    /**
     * json 格式的GET请求
     * @param params
     * @return
     */
    @GetMapping
    public ResponseEntity get(@RequestBody Map<String,String> params){
        ResponseEntity responseEntity;
        try {
            responseEntity = restTemplate.getForEntity(RequestUtils.getUrl(params,request), String.class,params);
            RequestUtils.successLog(request,params,responseEntity);
        }catch (Exception e){
            responseEntity = ResponseUtils.getResponseFromException(e);
            RequestUtils.errorLog(request,params,responseEntity);
        }
        return responseEntity;
    }

    /**
     * JSON  形式的 POST 请求
     * @param params
     * @return
     */
    @PostMapping
    public ResponseEntity post(@RequestBody Map<String,String> params){
        ResponseEntity responseEntity;
        try {
            responseEntity = restTemplate.postForEntity(RequestUtils.getUrl(params, request),null,String.class,params);
            RequestUtils.successLog(request,params,responseEntity);
        }catch (Exception e){
            responseEntity = ResponseUtils.getResponseFromException(e);
            RequestUtils.errorLog(request,params,responseEntity);
        }
        return responseEntity;
    }

    /**
     * JSON 格式的 PUT 请求
     * @param params
     * @return
     */
    @PutMapping
    public ResponseEntity put(@RequestBody Map<String,String> params){
        ResponseEntity responseEntity = Response.ok();
        try {
            restTemplate.put(RequestUtils.getUrl(params,request),null,params);
            RequestUtils.successLog(request,params,responseEntity);
        }catch (Exception e){
            responseEntity = ResponseUtils.getResponseFromException(e);
            RequestUtils.errorLog(request,params,responseEntity);
        }
        return responseEntity;
    }

    /**
     * JSON 形式的 DELETE 请求
     * @param params
     * @return
     */
    @DeleteMapping
    public ResponseEntity delete(@RequestBody Map<String,String> params){
        ResponseEntity responseEntity = Response.ok();
        try {
            restTemplate.delete(RequestUtils.getUrl(params,request),params);
            RequestUtils.successLog(request,params,responseEntity);
        }catch (Exception e){
            responseEntity = ResponseUtils.getResponseFromException(e);
            RequestUtils.errorLog(request,params,responseEntity);
        }
        return responseEntity;
    }
}

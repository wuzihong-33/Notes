package zihong.wu.cloudnote.common.util;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import zihong.wu.cloudnote.common.response.Response;
import zihong.wu.cloudnote.common.response.ResultData;

public class ResponseUtils {
    /**
     * 根据组件返回的错误码重组应答报文
     * @param exception
     * @return
     */
    public static ResponseEntity getResponseFromException(Exception exception) {
        ResponseEntity response;
        if(exception instanceof HttpClientErrorException){
            HttpClientErrorException errorException = (HttpClientErrorException) exception;
            switch (errorException.getStatusCode()){
                case FORBIDDEN:  response = Response.forbidden(); break;
                case BAD_REQUEST: response = Response.badRequest();break;
                case UNAUTHORIZED: response = Response.unauthorized();break;
                case INTERNAL_SERVER_ERROR: response = Response.error();break;
                default:{
                    ResultData resultData = ResultData.error("ERROR");
                    response = ResponseEntity.status(
                            errorException.getStatusCode()).contentType(MediaType.APPLICATION_JSON).body(resultData);
                }
            }
        }else {
            response = Response.badRequest();
        }
        return  response;
    }

    public static ResultData getResultDataFromException(HttpClientErrorException exception){
        return  (ResultData)getResponseFromException(exception).getBody();
    }
}

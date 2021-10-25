package zihong.wu.cloudnote.service;

import org.springframework.stereotype.Service;
import zihong.wu.cloudnote.common.response.ResultData;
import zihong.wu.cloudnote.common.util.SendEmailUtils;
import zihong.wu.cloudnote.entity.EmailLog;
import zihong.wu.cloudnote.mapper.EmailMapper;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EmailService {
    @Resource
    private EmailMapper mapper;

    /**
     * 发送邮件验证码并保存验证码
     * @param type
     * @param email
     * @return
     */
    public ResultData sendCode(int type, String email){
        EmailLog emailLog = SendEmailUtils.sendVCode(type, email);
        if(emailLog == null){
            return ResultData.error("邮件发送失败");
        }
        mapper.baseInsertAndReturnKey(emailLog);
        return ResultData.success();
    }

    /**
     * 对比从数据库得到的EmailLog(result)与传入参数EmailLog(emailLog)
     * @param emailLog
     * @return
     */
    public boolean checkCode(EmailLog emailLog){
        emailLog.setEmail(emailLog.getEmail());
        emailLog.setBaseKyleUseASC(false);
        List<EmailLog> list = mapper.baseSelectByCondition(emailLog);
        if(list == null || list.size() <= 0){
            return false;
        }
        EmailLog result = list.get(0);
        if(result.isEfficientVerificationCode() &&
                result.equalsCode(emailLog)){ // result 与 emailLog 进行比较
            setCodeUsed(result);
            return true;
        }
        return false;
    }

    private void setCodeUsed(EmailLog emailLog){
        emailLog.setIsUsed(1);
        mapper.baseUpdateById(emailLog);
    }

}

package zihong.wu.cloudnote.mapper;

import org.apache.ibatis.annotations.Mapper;
import zihong.wu.cloudnote.common.mybatis.BaseMapper;
import zihong.wu.cloudnote.entity.NotesInfo;

@Mapper
public interface NotesMapper extends BaseMapper<NotesInfo> {
}

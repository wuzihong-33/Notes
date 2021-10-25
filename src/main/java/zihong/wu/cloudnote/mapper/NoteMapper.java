package zihong.wu.cloudnote.mapper;

import org.apache.ibatis.annotations.Mapper;
import zihong.wu.cloudnote.common.mybatis.BaseMapper;
import zihong.wu.cloudnote.entity.NoteInfo;

@Mapper
public interface NoteMapper extends BaseMapper<NoteInfo> {
}

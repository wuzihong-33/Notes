package zihong.wu.cloudnote.entity;

import zihong.wu.cloudnote.common.mybatis.BaseEntity;
import zihong.wu.cloudnote.common.mybatis.annotation.*;

import java.util.Date;

@TableAttribute(name = "note_info",comment = "笔记详情表")
public class NoteInfo extends BaseEntity {

  @FieldAttribute
  @KeyAttribute(autoIncr = true)
  private Integer id;

  @FieldAttribute("用户ID")
  @IndexAttribute
  private Integer userId;

  @FieldAttribute("笔记本ID")
  @IndexAttribute
  private Integer notesId;

  @FieldAttribute("笔记类型 0:富文本笔记 1:MarkDown笔记")
  private Integer type;

  @FieldAttribute("笔记标题")
  private String title;

  @FieldAttribute(value = "纯文本笔记内容",length = 70000,detailed = true)
  private String text;

  @FieldAttribute(value = "html格式的笔记内容",length = 70000,detailed = true)
  private String html;

  @FieldAttribute("排序")
  @SortAttribute
  private Integer sort;

  @FieldAttribute("笔记创建时间")
  private Date createTime = new Date();

  @FieldAttribute("是否删除 0:未删除 1:已删除")
  private Integer isDelete;

  @FieldAttribute("最后一次修改时间")
  private Date updateTime;

  @FieldAttribute("最后一次修改人")
  private Integer updateUserID;

  public NoteInfo() {
  }

  public NoteInfo(Integer id) {
    this.id = id;
  }

  public NoteInfo(Integer userId, Integer notesId) {
    this.userId = userId;
    this.notesId = notesId;
  }

  public NoteInfo(Integer userId, Integer notesId, String title) {
    this.userId = userId;
    this.notesId = notesId;
    this.title = title;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public Integer getNotesId() {
    return notesId;
  }

  public void setNotesId(Integer notesId) {
    this.notesId = notesId;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getHtml() {
    return html;
  }

  public void setHtml(String html) {
    this.html = html;
  }

  public Integer getSort() {
    return sort;
  }

  public void setSort(Integer sort) {
    this.sort = sort;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Integer getIsDelete() {
    return isDelete;
  }

  public void setIsDelete(Integer isDelete) {
    this.isDelete = isDelete;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  public Integer getUpdateUserID() {
    return updateUserID;
  }

  public void setUpdateUserID(Integer updateUserID) {
    this.updateUserID = updateUserID;
  }

  @Override
  public String toString() {
    return "NoteInfo{" +
        "id=" + id +
        ", userId=" + userId +
        ", notesId=" + notesId +
        ", type=" + type +
        ", title='" + title + '\'' +
        ", text='" + text + '\'' +
        ", html='" + html + '\'' +
        ", sort=" + sort +
        ", createTime=" + createTime +
        ", isDelete=" + isDelete +
        ", updateTime=" + updateTime +
        ", updateUserID=" + updateUserID +
        '}';
  }
}

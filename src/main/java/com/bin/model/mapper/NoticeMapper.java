package com.bin.model.mapper;

import com.bin.model.dto.Notice.NoticeDto;
import com.bin.model.entity.Notice;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeMapper {
    public List selectMessageList(int userId,int reader);
    public int sendMessage(Notice notice);
    public int readerMessage(int id,int reader);
    public int countReader(int userId,int reader);
    public Notice selectNoticeById(int id);
}

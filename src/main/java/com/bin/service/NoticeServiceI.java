package com.bin.service;

import com.bin.model.dto.Notice.NoticeDto;
import java.util.Map;

public interface NoticeServiceI {
    public Map<String,Object> selectMessageList(NoticeDto dto);
    public Map<String,Object> readerMessage(NoticeDto dto);
    public Map<String,Object> countUnReader(NoticeDto dto);
}

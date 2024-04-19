package com.bin.queue.consume;

import com.alibaba.fastjson.JSON;
import com.bin.model.entity.Notice;
import com.bin.model.mapper.NoticeMapper;
import com.bin.queue.MyQueueBroker;
import com.bin.queue.dto.MessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: bin.jiang
 * @date: 2024/4/9 14:54
 **/
@Component("notice")
public class NoticeConsume implements Consume {

    @Autowired(required = false)
    protected NoticeMapper noticeMapper;

    @Override
    public void process(MessageDto messageDto) {
        String message = messageDto.getMessage();
        Notice notice = JSON.parseObject(message, Notice.class);
        int cnt = noticeMapper.sendMessage(notice);
        if(cnt != 1){
            // 新增通知失败，发送到失败消息队列
            MyQueueBroker.addFailMessage(messageDto);
        }
    }
}

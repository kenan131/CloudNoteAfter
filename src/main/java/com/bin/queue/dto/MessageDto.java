package com.bin.queue.dto;

import lombok.Data;

/**
 * @author: bin.jiang
 * @date: 2024/4/9 10:50
 **/
@Data
public class MessageDto {
    String topic;
    String message;

    public MessageDto(String topic, String message) {
        this.topic = topic;
        this.message = message;
    }
}

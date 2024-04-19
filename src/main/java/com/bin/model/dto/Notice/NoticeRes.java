package com.bin.model.dto.Notice;

import lombok.Data;

@Data
public class NoticeRes {
    int id;
    String Message;
    int reader;
    String sendTime;
}

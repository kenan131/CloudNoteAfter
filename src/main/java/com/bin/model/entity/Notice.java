package com.bin.model.entity;

import lombok.Data;
import java.util.Date;

@Data
public class Notice implements Comparable<Notice>{
    private int id;
    private int reader;
    private String message;
    private int userId;
    private Date sendTime;

    public Notice(int reader, String message, int userId,Date sendTime) {
        this.reader = reader;
        this.message = message;
        this.userId = userId;
        this.sendTime=sendTime;
    }

    public Notice() {
    }

    @Override
    public int compareTo(Notice notice) {
        if(this.sendTime == notice.sendTime ){
            return 0;
        }
        else if(this.sendTime.before(notice.sendTime)){
            return 1;
        }
        return -1;
    }
}

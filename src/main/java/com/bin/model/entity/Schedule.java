package com.bin.model.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Schedule{
    int id;
    String plan;
    Date createTime;
    Date finishTime;
    int status;
    int userId;

    public Schedule(String plan, Date createTime, Date finishTime, int status, int userId) {
        this.plan = plan;
        this.createTime = createTime;
        this.finishTime = finishTime;
        this.status = status;
        this.userId = userId;
    }

    public Schedule() {
    }

}

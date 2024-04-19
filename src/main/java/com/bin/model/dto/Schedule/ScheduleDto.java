package com.bin.model.dto.Schedule;

import lombok.Data;

import java.util.Date;

@Data
public class ScheduleDto {
    Integer id;
    Integer status;
    String plan;
    Date finishTimeDate;
    Date finishTimeDay;
}

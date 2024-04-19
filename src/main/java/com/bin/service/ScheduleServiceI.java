package com.bin.service;

import com.bin.model.dto.Schedule.ScheduleDto;

import java.text.ParseException;
import java.util.Map;

public interface ScheduleServiceI {
    Map<String,Object> getScheduleList(ScheduleDto dto);
    Map<String,Object> finishSchedule(ScheduleDto dto);
    Map<String,Object> deleteSchedule(ScheduleDto dto);
    Map<String,Object> insertSchedule(ScheduleDto dto) throws ParseException;
    Map<String,Object> getRecentPlan(ScheduleDto dto);
    void sendEmailRemind();
}

package com.bin.model.mapper;

import com.bin.model.entity.Schedule;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ScheduleMapper {
    Schedule selectById(int id);
    int insertSchedule(Schedule schedule);
    List selectScheduleByStatus(int userId,int status);
    int updateScheduleStatus(int id,int status);
    int deleteSchedule(int id);
    List selectToDayUnFinish(int status);
}

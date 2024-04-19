package com.bin.controller;

import com.bin.model.Enum.ResStatus;
import com.bin.model.dto.ResResponse;
import com.bin.model.dto.Schedule.ScheduleDto;
import com.bin.service.ScheduleServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Map;

/**
 * @author: bin.jiang
 * @date: 2023/3/14 16:58
 **/
@RestController
@RequestMapping("schedule")
@CrossOrigin
public class ScheduleController {
    @Autowired
    ScheduleServiceI scheduleService;

    @PostMapping("getScheduleList")
    public ResResponse getScheduleList(@RequestBody ScheduleDto scheduleDto){
        Map<String, Object> ans = scheduleService.getScheduleList(scheduleDto);
        ResResponse res;
        if(ResStatus.ERROR.getType().equals(ans.get("type"))){
            res=new ResResponse(ResStatus.ERROR.getType(),ans.get("message"));
        }
        else{
            res=new ResResponse(ResStatus.Success.getType(),ans.get("message"),ans.get("data"));
        }
        return res;
    }

    @PostMapping("finishSchedule")
    public ResResponse finishSchedule(@RequestBody ScheduleDto scheduleDto){
        Map<String, Object> ans = scheduleService.finishSchedule(scheduleDto);
        ResResponse res;
        if(ResStatus.ERROR.getType().equals(ans.get("type"))){
            res=new ResResponse(ResStatus.ERROR.getType(),ans.get("message"));
        }
        else{
            res=new ResResponse(ResStatus.Success.getType(),ans.get("message"));
        }
        return res;
    }
    @PostMapping("deleteSchedule")
    public ResResponse deleteSchedule(@RequestBody ScheduleDto scheduleDto){
        Map<String, Object> ans = scheduleService.deleteSchedule(scheduleDto);
        ResResponse res;
        if(ResStatus.ERROR.getType().equals(ans.get("type"))){
            res=new ResResponse(ResStatus.ERROR.getType(),ans.get("message"));
        }
        else{
            res=new ResResponse(ResStatus.Success.getType(),ans.get("message"));
        }
        return res;
    }
    @PostMapping("insertSchedule")
    public ResResponse insertSchedule(@RequestBody ScheduleDto scheduleDto) throws ParseException {
        Map<String, Object> ans = scheduleService.insertSchedule(scheduleDto);
        ResResponse res;
        if(ResStatus.ERROR.getType().equals(ans.get("type"))){
            res=new ResResponse(ResStatus.ERROR.getType(),ans.get("message"));
        }
        else{
            res=new ResResponse(ResStatus.Success.getType(),ans.get("message"));
        }
        return res;
    }
    @PostMapping("getRecentPlan")
    public ResResponse getRecentPlan(@RequestBody ScheduleDto scheduleDto){
        Map<String, Object> ans = scheduleService.getRecentPlan(scheduleDto);
        ResResponse res;
        if(ResStatus.ERROR.getType().equals(ans.get("type"))){
            res=new ResResponse(ResStatus.ERROR.getType(),ans.get("message"));
        }
        else{
            res=new ResResponse(ResStatus.Success.getType(),ans.get("message"),ans.get("data"));
        }
        return res;
    }
}

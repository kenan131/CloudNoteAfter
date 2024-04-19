package com.bin.service.imple;

import com.bin.model.Enum.ResStatus;
import com.bin.model.Enum.ScheduleStatus;
import com.bin.model.dto.Schedule.ScheduleDto;
import com.bin.model.dto.Schedule.ScheduleRes;
import com.bin.model.entity.Schedule;
import com.bin.model.entity.User;
import com.bin.model.mapper.ScheduleMapper;
import com.bin.model.mapper.UserMapper;
import com.bin.service.ScheduleServiceI;
import com.bin.util.HostHolder;
import com.bin.util.MailClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ScheduleService implements ScheduleServiceI {

    @Autowired
    HostHolder hostHolder;

    @Autowired(required = false)
    ScheduleMapper scheduleMapper;

    @Autowired
    private MailClient mailClient;
    @Autowired(required = false)
    private UserMapper userMapper;

    //未完成行程计划
    @Override
    public Map<String, Object> getScheduleList(ScheduleDto dto) {
        Map<String,Object> res=new HashMap<>();
        res.put("type",ResStatus.ERROR.getType());
        if(dto.getStatus() == null){
            res.put("message","参数缺失，请重新输入！");
            return res;
        }
        User user = hostHolder.getUser();
        if(user==null){
            res.put("message","用户登录异常！");
            return res;
        }
        List<Schedule> schedules = scheduleMapper.selectScheduleByStatus(user.getId(), dto.getStatus());
        List<ScheduleRes> resList = getResList(schedules);
        Collections.sort(resList);
        res.put("type",ResStatus.Success.getType());
        res.put("message","获取数据成功！");
        res.put("data",resList);
        return res;
    }

    @Override
    public Map<String, Object> finishSchedule(ScheduleDto dto) {
        Map<String,Object> res=new HashMap<>();
        res.put("type",ResStatus.ERROR.getType());
        if(dto.getId() == null){
            res.put("message","参数缺失，请重试！");
            return res;
        }
        Schedule schedule = scheduleMapper.selectById(dto.getId());
        if(!isOwn(schedule)){
            res.put("message","非法完成其他用户的行程记录！");
            return res;
        }
        int cnt = scheduleMapper.updateScheduleStatus(dto.getId(), ScheduleStatus.FINISH.getCode());
        if(cnt!=1){
            res.put("message","更新行程状态失败，请重试！");
            return res;
        }
        res.put("message","更新行程成功！");
        res.put("type",ResStatus.Success.getType());
        return res;
    }

    @Override
    public Map<String, Object> deleteSchedule(ScheduleDto dto) {
        Map<String,Object> res=new HashMap<>();
        res.put("type",ResStatus.ERROR.getType());
        if(dto.getId() == null){
            res.put("message","参数缺失，请重试！");
            return res;
        }
        Schedule schedule = scheduleMapper.selectById(dto.getId());
        if(!isOwn(schedule)){
            res.put("message","非法删除其他用户的行程记录！");
            return res;
        }
        int cnt = scheduleMapper.deleteSchedule(dto.getId());
        if(cnt!=1){
            res.put("message","删除行程失败，请重试！");
            return res;
        }
        res.put("message","删除行程成功！");
        res.put("type",ResStatus.Success.getType());
        return res;
    }

    @Override
    public Map<String, Object> insertSchedule(ScheduleDto dto) throws ParseException {
        Map<String,Object> res=new HashMap<>();
        res.put("type",ResStatus.ERROR.getType());
        if(dto.getPlan() == null ||dto.getFinishTimeDate() == null || dto.getFinishTimeDay() ==null){
            res.put("message","参数缺失，请重试！");
            return res;
        }
        User user = hostHolder.getUser();
        if(user==null){
            res.put("message","用户登录异常！");
            return res;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//前端日期格式
        String date = simpleDateFormat.format(dto.getFinishTimeDate());
        String day = simpleDateFormat.format(dto.getFinishTimeDay());
        String finishTime= date.substring(0,10)+day.substring(10,18);
        Date finishDate = simpleDateFormat.parse(finishTime);//拼接时间
        Schedule schedule = new Schedule(dto.getPlan(), new Date(), finishDate, ScheduleStatus.UNFINISH.getCode(), user.getId());
        int cnt = scheduleMapper.insertSchedule(schedule);
        if(cnt!=1){
            res.put("message","创建行程记录失败，请重试！");
            return res;
        }
        res.put("message","创建行程记录成功！");
        res.put("type",ResStatus.Success.getType());
        return res;
    }

    @Override
    public Map<String, Object> getRecentPlan(ScheduleDto dto) {
        Map<String,Object> res=new HashMap<>();
        res.put("type",ResStatus.ERROR.getType());
        if(dto.getStatus()!=0){
            res.put("message","参数错误");
            return res;
        }
        Map<String, Object> map = getScheduleList(dto);
        List<ScheduleRes> data = (List<ScheduleRes>) map.get("data");
        Collections.sort(data);
        res.put("message","获取最近计划数据成功！");
        res.put("type",ResStatus.Success.getType());
        if(data.size()!=0){
            res.put("data",data.get(0));
        }
        return res;
    }

    @Override
    public void sendEmailRemind() {
        List<Schedule> schedules = scheduleMapper.selectToDayUnFinish(ScheduleStatus.UNFINISH.getCode());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(Schedule schedule : schedules){
            User user = userMapper.selectById(schedule.getId());
            if(user==null){
                continue;
            }
            String date=simpleDateFormat.format(schedule.getFinishTime());
            String str = "温馨提示！\n " +
                    "你的计划安排：{"+schedule.getPlan()+"} 将在"+date+"结束，请在及时安排时间完成！";
            try{
                mailClient.sendMail(user.getEmail(),"云笔记行程记录提醒",str);
            }catch (Exception e){
                System.out.println("ID为"+schedule.getId()+ "的行程记录邮件提醒发送失败！");
            }
        }
    }

    List<ScheduleRes> getResList(List<Schedule> lists){
        List<ScheduleRes> res= new ArrayList();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(Schedule schedule: lists){
            ScheduleRes scheduleRes = new ScheduleRes();
            scheduleRes.setId(schedule.getId());
            scheduleRes.setPlan(schedule.getPlan());
            scheduleRes.setCreateTime(simpleDateFormat.format(schedule.getCreateTime()));
            scheduleRes.setFinishTime(simpleDateFormat.format(schedule.getFinishTime()));
            scheduleRes.setStatus(schedule.getStatus());
            res.add(scheduleRes);
        }
        return res;
    }

    Boolean isOwn(Schedule schedule){
        User user = hostHolder.getUser();
        if(schedule.getUserId()!=user.getId()){
            return false;
        }
        return true;
    }
}

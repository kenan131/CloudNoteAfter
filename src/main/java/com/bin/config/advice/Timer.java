package com.bin.config.advice;

import com.bin.service.ScheduleServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Timer {
    @Autowired
    ScheduleServiceI scheduleServiceI;

    @Value("${timer}")
    String timer;

    @Scheduled(cron = "${timer}")
    public void hello(){
        System.out.println("定时器触发！");
        scheduleServiceI.sendEmailRemind();
    }
}

package com.bin;


import com.bin.model.mapper.*;
import com.bin.service.ScheduleServiceI;
import com.bin.util.MailClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CloudNoteApplicationTests {


    @Autowired
    ScheduleServiceI scheduleServiceI;
    @Autowired
    ScheduleMapper scheduleMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    MailClient mailClient;

    @Test
    void fileTest(){

    }
}

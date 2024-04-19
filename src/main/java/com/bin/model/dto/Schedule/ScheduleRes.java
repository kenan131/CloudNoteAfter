package com.bin.model.dto.Schedule;

import com.bin.model.entity.Schedule;
import lombok.Data;
import lombok.SneakyThrows;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class ScheduleRes implements Comparable<ScheduleRes>{
    int id;
    String plan;
    String createTime;
    String finishTime;
    int status;

    @SneakyThrows
    @Override
    public int compareTo(ScheduleRes b) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date aDate = simpleDateFormat.parse(this.getFinishTime());
        Date bDate = simpleDateFormat.parse(b.getFinishTime());
        if(aDate == bDate){
            return 0;
        }
        else if(aDate.before(bDate)){
            return -1;
        }
        return 1;
    }
}

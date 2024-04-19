package com.bin.model.Enum;

public enum ScheduleStatus {
    UNFINISH(0),FINISH(1);
    private int code;
    ScheduleStatus(int code){
        this.code=code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}

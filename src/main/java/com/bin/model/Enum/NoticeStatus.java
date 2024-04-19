package com.bin.model.Enum;

public enum NoticeStatus {
    UNREADER(0),READER(1);
    private int code;
    NoticeStatus(int code){
        this.code=code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}

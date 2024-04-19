package com.bin.model.Enum;

/**
 * @author: bin.jiang
 * @date: 2023/3/1 14:59
 **/

public enum UserStatus {
    ACTIVE(1),UNACTIVE(0),ADMIN(1),UNADMIN(0);
    private int code;
    public int getCode(){
        return code;
    }
    UserStatus(int active){
        this.code=active;
    }
}

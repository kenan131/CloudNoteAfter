package com.bin.model.dto;

import lombok.Data;

@Data
public class ResResponse {
    String type;
    Object message;
    Object data;
    public ResResponse(String type,Object message,Object data){
        this.type=type;
        this.message=message;
        this.data=data;
    }
    public ResResponse(String type,Object message){
        this.type=type;
        this.message=message;
    }
}

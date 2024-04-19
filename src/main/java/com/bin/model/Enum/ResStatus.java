package com.bin.model.Enum;

import lombok.Getter;

@Getter
public enum ResStatus {
    Success("S"),
    ERROR("E");
    private String type;
    ResStatus(String type){
        this.type=type;
    }
}

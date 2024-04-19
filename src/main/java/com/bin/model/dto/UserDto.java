package com.bin.model.dto;

import lombok.Data;

/**
 * @author: bin.jiang
 * @date: 2023/2/27 11:39
 **/
@Data
public class UserDto {
    private String account;
    private String password;
    private String email;
    private String captcha;
    private String random;
}

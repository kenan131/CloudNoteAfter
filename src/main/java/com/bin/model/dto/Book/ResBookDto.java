package com.bin.model.dto.Book;

import lombok.Data;

import java.util.Date;

/**
 * @author: bin.jiang
 * @date: 2023/3/6 15:15
 **/

@Data
public class ResBookDto {
    private Integer bookId;
    private String bookName;
    private String createTime;
    private Integer share;

    public ResBookDto(Integer bookId, String bookName , String createTime,Integer share) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.createTime = createTime;
        this.share=share;
    }

    public ResBookDto() {

    }
}

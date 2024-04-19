package com.bin.model.dto.Book;

import lombok.Data;

/**
 * @author: bin.jiang
 * @date: 2023/3/6 11:38
 **/
@Data
public class BookDto {
    private Integer noteBookId;
    private String bookName;
    private Integer bookId;
    private String text;
    private Integer shareStatus;
    private String message;
}

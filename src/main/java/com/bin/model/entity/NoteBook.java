package com.bin.model.entity;

import lombok.Data;

/**
 * @author: bin.jiang
 * @date: 2023/3/3 11:27
 **/
@Data
public class NoteBook {
    private int id;
    private String name;
    private int userId;

    public NoteBook(String name, int userId) {
        this.name = name;
        this.userId = userId;
    }

    public NoteBook() {
    }
}

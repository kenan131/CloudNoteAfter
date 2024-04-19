package com.bin.model.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author: bin.jiang
 * @date: 2023/3/3 11:27
 **/
@Data
public class Book {
    private int id;
    private String name;
    private String text;
    private int del;
    private int share;
    private Date createTime;
    private Date updateTime;
    private int noteId;

    public Book(String name, String text, int del, int share, Date createTime, Date updateTime, int noteId) {
        this.name = name;
        this.text = text;
        this.del = del;
        this.share = share;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.noteId = noteId;
    }

    public Book() {
    }
}

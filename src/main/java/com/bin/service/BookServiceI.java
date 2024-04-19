package com.bin.service;

import com.bin.model.dto.Book.BookDto;
import com.bin.model.dto.UserDto;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface BookServiceI {
    Map<String,Object> getBookList(BookDto dto);

    Map<String,Object> insertBook(BookDto dto);
    //删除笔记本，将笔记的删除字段设置为用户id
    Map<String,Object> deleteBook(BookDto dto);
    Map<String,Object> realDelBook(BookDto dto);

    Map<String,Object> updateBookName(BookDto dto);

    //设置是否共享笔记
    Map<String,Object> setShareBook(BookDto dto);

    //更改笔记归属笔记本
    Map<String,Object> shiftBookOnNoteBook(BookDto dto);

    //获取共享笔记列表
    Map<String,Object> getShareBookList(BookDto dto);
    Map<String,Object> getShareSuccess(BookDto dto);
    //还原笔记
    Map<String,Object> restoreBook(BookDto dto);
    //保存笔记
    Map<String,Object> saveBook(BookDto dto);
    //获取笔记数据
    Map<String,Object> getBookText(BookDto dto);
    Map<String,Object> downLoadBook(BookDto dto);
    Map<String,Object> importBookFromFile(BookDto dto);
    Map<String,Object> examineBookShareStatus(BookDto dto);
    public Map<String,Object> checkIsAdmin();
    Map<String,Object> shareFriend(BookDto dto);
    Map<String,Object> getShareBook(String uuid);
}

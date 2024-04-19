package com.bin.model.mapper;

import com.bin.model.entity.Book;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author: bin.jiang
 * @date: 2023/3/3 11:33
 **/
@Mapper
public interface BookMapper {
    public List selectBooks(int noteId);
    public int updateBookName(int bookId,String name);
    public int deleteBook(int bookId,int userId);
    public int insertBook(Book book);
    public List selectBookByName(int noteId,String name);
    public int shiftNoteBook(int bookId,int noteId);
    public Book selectBookById(int bookId);
    public int shareBook(int bookId,int share);
    public List selectBookListByDel(int userId);
    public List selectBookListByShare(int share);
    public List selectBookListByDelAndName(int userId,String bookName);
    public int saveBook(int bookId,String text);
    public String getBookText(int bookId);
    public int realDelBook(int bookId);
}

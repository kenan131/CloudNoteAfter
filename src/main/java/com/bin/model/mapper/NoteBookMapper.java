package com.bin.model.mapper;

import com.bin.model.entity.NoteBook;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author: bin.jiang
 * @date: 2023/3/3 11:32
 **/
@Mapper
public interface NoteBookMapper {
    public List selectNotes(int userId);
    public List selectNoteName(int userId,String name);
    public int updateNoteName(int noteId,String name);
    public int deleteNoteBook(int noteId);
    public int insertNoteBook(NoteBook noteBook);
    public NoteBook selectNoteBookById(int noteBookId);
}

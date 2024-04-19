package com.bin.service;

import com.bin.model.dto.NoteBook.NoteBookDto;
import com.bin.model.entity.NoteBook;

import java.util.Map;

public interface NoteBookServiceI {
    Map<String,Object> getNoteBookList(NoteBookDto dto);
    Map<String,Object> deleteBNoteBook(Integer noteId);
    Map<String,Object> insertNoteBook(NoteBookDto dto);
    Map<String,Object> updateNoteBookName(NoteBookDto dto);
}

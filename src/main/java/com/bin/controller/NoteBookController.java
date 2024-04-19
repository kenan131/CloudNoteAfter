package com.bin.controller;

import com.bin.model.Enum.ResStatus;
import com.bin.model.dto.NoteBook.NoteBookDto;
import com.bin.model.dto.ResResponse;
import com.bin.service.imple.NoteBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("noteBook")
@CrossOrigin
public class NoteBookController {

    @Autowired
    NoteBookService noteBookService;

    @PostMapping("noteBookList")
    public ResResponse getNoteBookList(@RequestBody NoteBookDto noteBookDto){
        Map<String, Object> ans = noteBookService.getNoteBookList(noteBookDto);
        ResResponse res;
        if(ResStatus.ERROR.getType().equals(ans.get("type"))){
            res=new ResResponse(ResStatus.ERROR.getType(),ans.get("message"));
        }
        else{
            res=new ResResponse(ResStatus.Success.getType(),ans.get("message"),ans.get("data"));
        }
        return res;
    }

    @PostMapping("insertNoteBook")
    public ResResponse insertNoteBook(@RequestBody NoteBookDto noteBookDto){
        Map<String, Object> ans = noteBookService.insertNoteBook(noteBookDto);
        ResResponse res;
        if(ResStatus.ERROR.getType().equals(ans.get("type"))){
            res=new ResResponse(ResStatus.ERROR.getType(),ans.get("message"));
        }
        else{
            res=new ResResponse(ResStatus.Success.getType(),ans.get("message"));
        }
        return res;
    }

    @PostMapping("deleteBNoteBook")
    public ResResponse deleteBNoteBook(@RequestBody NoteBookDto noteBookDto){
        Map<String, Object> ans = noteBookService.deleteBNoteBook(noteBookDto.getNoteBookId());
        ResResponse res;
        if(ResStatus.ERROR.getType().equals(ans.get("type"))){
            res=new ResResponse(ResStatus.ERROR.getType(),ans.get("message"));
        }
        else{
            res=new ResResponse(ResStatus.Success.getType(),ans.get("message"));
        }
        return res;
    }

    @PostMapping("updateNoteBookName")
    public ResResponse updateNoteBookName(@RequestBody NoteBookDto noteBookDto){
        Map<String, Object> ans = noteBookService.updateNoteBookName(noteBookDto);
        ResResponse res;
        if(ResStatus.ERROR.getType().equals(ans.get("type"))){
            res=new ResResponse(ResStatus.ERROR.getType(),ans.get("message"));
        }
        else{
            res=new ResResponse(ResStatus.Success.getType(),ans.get("message"));
        }
        return res;
    }

}

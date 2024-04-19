package com.bin.controller;

import com.bin.model.dto.Book.BookDto;
import com.bin.model.Enum.ResStatus;
import com.bin.model.dto.ResResponse;
import com.bin.model.dto.UserDto;
import com.bin.service.BookServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("book")
@CrossOrigin
public class BookController {

    @Autowired(required = false)
    BookServiceI bookService;

    @PostMapping("getBookList")
    public ResResponse getBookList(@RequestBody BookDto bookDto){
        Map<String, Object> ans = bookService.getBookList(bookDto);
        ResResponse res;
        if(ResStatus.ERROR.getType().equals(ans.get("type"))){
            res=new ResResponse(ResStatus.ERROR.getType(),ans.get("message"));
        }
        else{
            res=new ResResponse(ResStatus.Success.getType(),ans.get("message"),ans.get("data"));
        }
        return res;
    }

    @PostMapping("insertBook")
    public ResResponse insertBook(@RequestBody BookDto bookDto){
        Map<String, Object> ans = bookService.insertBook(bookDto);
        ResResponse res;
        if(ResStatus.ERROR.getType().equals(ans.get("type"))){
            res=new ResResponse(ResStatus.ERROR.getType(),ans.get("message"));
        }
        else{
            res=new ResResponse(ResStatus.Success.getType(),ans.get("message"));
        }
        return res;
    }

    @PostMapping("deleteBook")
    public ResResponse deleteBook(@RequestBody BookDto bookDto){
        Map<String, Object> ans = bookService.deleteBook(bookDto);
        ResResponse res;
        if(ResStatus.ERROR.getType().equals(ans.get("type"))){
            res=new ResResponse(ResStatus.ERROR.getType(),ans.get("message"));
        }
        else{
            res=new ResResponse(ResStatus.Success.getType(),ans.get("message"));
        }
        return res;
    }

    @PostMapping("realDelBook")
    public ResResponse realDelBook(@RequestBody BookDto bookDto){
        Map<String, Object> ans = bookService.realDelBook(bookDto);
        ResResponse res;
        if(ResStatus.ERROR.getType().equals(ans.get("type"))){
            res=new ResResponse(ResStatus.ERROR.getType(),ans.get("message"));
        }
        else{
            res=new ResResponse(ResStatus.Success.getType(),ans.get("message"));
        }
        return res;
    }


    @PostMapping("restoreBook")
    public ResResponse restoreBook(@RequestBody BookDto bookDto){
        Map<String, Object> ans = bookService.restoreBook(bookDto);
        ResResponse res;
        if(ResStatus.ERROR.getType().equals(ans.get("type"))){
            res=new ResResponse(ResStatus.ERROR.getType(),ans.get("message"));
        }
        else{
            res=new ResResponse(ResStatus.Success.getType(),ans.get("message"));
        }
        return res;
    }

    @PostMapping("updateBookName")
    public ResResponse updateBookName(@RequestBody BookDto bookDto){
        Map<String, Object> ans = bookService.updateBookName(bookDto);
        ResResponse res;
        if(ResStatus.ERROR.getType().equals(ans.get("type"))){
            res=new ResResponse(ResStatus.ERROR.getType(),ans.get("message"));
        }
        else{
            res=new ResResponse(ResStatus.Success.getType(),ans.get("message"));
        }
        return res;
    }

    @PostMapping("setShareBook")
    public ResResponse setShareBook(@RequestBody BookDto bookDto){
        Map<String, Object> ans = bookService.setShareBook(bookDto);
        ResResponse res;
        if(ResStatus.ERROR.getType().equals(ans.get("type"))){
            res=new ResResponse(ResStatus.ERROR.getType(),ans.get("message"));
        }
        else{
            res=new ResResponse(ResStatus.Success.getType(),ans.get("message"));
        }
        return res;
    }

    @PostMapping("shiftBookOnNoteBook")
    public ResResponse shiftBookOnNoteBook(@RequestBody BookDto bookDto){
        Map<String, Object> ans = bookService.shiftBookOnNoteBook(bookDto);
        ResResponse res;
        if(ResStatus.ERROR.getType().equals(ans.get("type"))){
            res=new ResResponse(ResStatus.ERROR.getType(),ans.get("message"));
        }
        else{
            res=new ResResponse(ResStatus.Success.getType(),ans.get("message"));
        }
        return res;
    }


    @PostMapping("getShareBookList")
    public ResResponse getShareBookList(@RequestBody BookDto bookDto){
        Map<String, Object> ans = bookService.getShareBookList(bookDto);
        ResResponse res;
        if(ResStatus.ERROR.getType().equals(ans.get("type"))){
            res=new ResResponse(ResStatus.ERROR.getType(),ans.get("message"));
        }
        else{
            res=new ResResponse(ResStatus.Success.getType(),ans.get("message"),ans.get("data"));
        }
        return res;
    }

    @PostMapping("saveBook")
    public ResResponse saveBook(@RequestBody BookDto bookDto){
        Map<String, Object> ans = bookService.saveBook(bookDto);
        ResResponse res;
        if(ResStatus.ERROR.getType().equals(ans.get("type"))){
            res=new ResResponse(ResStatus.ERROR.getType(),ans.get("message"));
        }
        else{
            res=new ResResponse(ResStatus.Success.getType(),ans.get("message"));
        }
        return res;
    }

    @PostMapping("getBookText")
    public ResResponse getBookText(@RequestBody BookDto bookDto){
        Map<String, Object> ans = bookService.getBookText(bookDto);
        ResResponse res;
        if(ResStatus.ERROR.getType().equals(ans.get("type"))){
            res=new ResResponse(ResStatus.ERROR.getType(),ans.get("message"));
        }
        else{
            res=new ResResponse(ResStatus.Success.getType(),ans.get("message"),ans.get("data"));
        }
        return res;
    }

    @PostMapping("downLoadBookFile")
    public ResResponse downLoadBookFile(@RequestBody BookDto dto){
        Map<String, Object> ans = bookService.downLoadBook(dto);
        ResResponse res;
        if(ResStatus.ERROR.getType().equals(ans.get("type"))){
            res=new ResResponse(ResStatus.ERROR.getType(),ans.get("message"));
        }
        else{
            res=new ResResponse(ResStatus.Success.getType(),ans.get("message"),ans.get("data"));
        }
        return res;
    }
    @PostMapping("importBookFromFile")
    public ResResponse importBookFromFile(@RequestBody BookDto dto){
        Map<String, Object> ans = bookService.importBookFromFile(dto);
        ResResponse res;
        if(ResStatus.ERROR.getType().equals(ans.get("type"))){
            res=new ResResponse(ResStatus.ERROR.getType(),ans.get("message"));
        }
        else{
            res=new ResResponse(ResStatus.Success.getType(),ans.get("message"));
        }
        return res;
    }

    @PostMapping("examineBookShareStatus")
    public ResResponse unAcceptBook(@RequestBody BookDto dto){
        Map<String, Object> ans = bookService.examineBookShareStatus(dto);
        ResResponse res;
        if(ResStatus.ERROR.getType().equals(ans.get("type"))){
            res=new ResResponse(ResStatus.ERROR.getType(),ans.get("message"));
        }
        else{
            res=new ResResponse(ResStatus.Success.getType(),ans.get("message"));
        }
        return res;
    }
    @PostMapping("checkIsAdmin")
    public ResResponse checkIsAdmin(@RequestBody UserDto userDto){
        Map<String, Object> ans = bookService.checkIsAdmin();
        ResResponse res;
        if(ResStatus.ERROR.getType().equals(ans.get("type"))){
            res=new ResResponse(ResStatus.ERROR.getType(),ans.get("message"));
        }
        else{
            res=new ResResponse(ResStatus.Success.getType(),ans.get("message"));
        }
        return res;
    }
    @PostMapping("shareFriend")
    public ResResponse shareFriend(@RequestBody BookDto bookDto){
        Map<String, Object> ans = bookService.shareFriend(bookDto);
        ResResponse res;
        if(ResStatus.ERROR.getType().equals(ans.get("type"))){
            res=new ResResponse(ResStatus.ERROR.getType(),ans.get("message"));
        }
        else{
            res=new ResResponse(ResStatus.Success.getType(),ans.get("message"),ans.get("data"));
        }
        return res;
    }
}

package com.bin.controller;

import com.bin.model.dto.Book.BookDto;
import com.bin.model.Enum.ResStatus;
import com.bin.model.dto.ResResponse;
import com.bin.service.BookServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author: bin.jiang
 * @date: 2023/3/17 10:40
 **/
@RestController
@RequestMapping("share")
@CrossOrigin
public class shareController {
    @Autowired
    BookServiceI bookService;

    @PostMapping("getShareSuccessList")
    public ResResponse getShareSuccessList(@RequestBody BookDto dto){
        Map<String, Object> ans = bookService.getShareSuccess(dto);
        ResResponse res;
        if(ResStatus.ERROR.getType().equals(ans.get("type"))){
            res=new ResResponse(ResStatus.ERROR.getType(),ans.get("message"));
        }
        else{
            res=new ResResponse(ResStatus.Success.getType(),ans.get("message"),ans.get("data"));
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

    @GetMapping("getShareBook")
    public ResResponse getShareBook(String uuid){
        Map<String, Object> ans = bookService.getShareBook(uuid);
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

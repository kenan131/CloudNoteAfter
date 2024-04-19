package com.bin.controller;

import com.bin.model.Enum.ResStatus;
import com.bin.model.dto.Notice.NoticeDto;
import com.bin.model.dto.ResResponse;
import com.bin.service.NoticeServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Map;

@RestController
@RequestMapping("notice")
@CrossOrigin
public class NoticeController {

    @Autowired
    NoticeServiceI noticeService;


    @PostMapping("selectMessageList")
    public ResResponse selectMessageList(@RequestBody NoticeDto noticeDto) throws ParseException {
        Map<String, Object> ans = noticeService.selectMessageList(noticeDto);
        ResResponse res;
        if(ResStatus.ERROR.getType().equals(ans.get("type"))){
            res=new ResResponse(ResStatus.ERROR.getType(),ans.get("message"));
        }
        else{
            res=new ResResponse(ResStatus.Success.getType(),ans.get("message"),ans.get("data"));
        }
        return res;
    }

    @PostMapping("readerMessage")
    public ResResponse readerMessage(@RequestBody NoticeDto noticeDto) throws ParseException {
        Map<String, Object> ans = noticeService.readerMessage(noticeDto);
        ResResponse res;
        if(ResStatus.ERROR.getType().equals(ans.get("type"))){
            res=new ResResponse(ResStatus.ERROR.getType(),ans.get("message"));
        }
        else{
            res=new ResResponse(ResStatus.Success.getType(),ans.get("message"));
        }
        return res;
    }

    @PostMapping("countUnReader")
    public ResResponse countUnReader(@RequestBody NoticeDto noticeDto) throws ParseException {
        Map<String, Object> ans = noticeService.countUnReader(noticeDto);
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

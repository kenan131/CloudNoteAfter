package com.bin.service.imple;

import com.bin.model.Enum.NoticeStatus;
import com.bin.model.Enum.ResStatus;
import com.bin.model.dto.Notice.NoticeDto;
import com.bin.model.dto.Notice.NoticeRes;
import com.bin.model.entity.Notice;
import com.bin.model.entity.User;
import com.bin.model.mapper.NoticeMapper;
import com.bin.service.NoticeServiceI;
import com.bin.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class NoticeService  implements NoticeServiceI {

    @Autowired(required = false)
    NoticeMapper noticeMapper;

    @Autowired
    HostHolder hostHolder;

    @Override
    public Map<String, Object> selectMessageList(NoticeDto dto) {
        Map<String,Object> res = new HashMap<>();
        res.put("type", ResStatus.ERROR.getType());
        User user = hostHolder.getUser();
        if(user==null){
            res.put("message","登录参数有误，请重试！");
            return res;
        }
        List<Notice> unReader = noticeMapper.selectMessageList(user.getId(),NoticeStatus.UNREADER.getCode());
        List<Notice> reader = noticeMapper.selectMessageList(user.getId(), NoticeStatus.READER.getCode());
        List<NoticeRes> un = getResList(unReader);
        List<NoticeRes> read = getResList(reader);
        List<NoticeRes> data=new ArrayList<>();
        for(NoticeRes noticeRes : un){
            data.add(noticeRes);
        }
        for (NoticeRes noticeRes : read){
            data.add(noticeRes);
        }
        res.put("message","获取通知数据成功！");
        res.put("data",data);
        res.put("type",ResStatus.Success.getType());
        return res;
    }

    @Override
    public Map<String, Object> readerMessage(NoticeDto dto) {
        Map<String,Object> res = new HashMap<>();
        res.put("type", ResStatus.ERROR.getType());
        if(dto.getId()==null){
            res.put("message","参数缺失，请重试！");
            return res;
        }
        Notice notice = noticeMapper.selectNoticeById(dto.getId());
        if(!isOwn(notice)){
            res.put("message","登录信息有误，请重试！");
            return res;
        }
        int cnt = noticeMapper.readerMessage(dto.getId(), NoticeStatus.READER.getCode());
        if(cnt!=1){
            res.put("message","已读通知失败，请重试！");
            return res;
        }
        res.put("message","已读通知成功！");
        res.put("type",ResStatus.Success.getType());
        return res;
    }

    @Override
    public Map<String, Object> countUnReader(NoticeDto dto) {
        Map<String,Object> res = new HashMap<>();
        res.put("type", ResStatus.ERROR.getType());
        User user = hostHolder.getUser();
        if(user==null){
            res.put("message","登录信息有误，请重试！");
            return res;
        }
        int cnt = noticeMapper.countReader(user.getId(), NoticeStatus.UNREADER.getCode());
        res.put("message","获取未读通知数量成功！");
        res.put("type",ResStatus.Success.getType());
        res.put("data",cnt);
        return res;
    }

    public boolean isOwn(Notice notice){
        User user = hostHolder.getUser();
        if(user==null){
            return false;
        }
        if(user.getId()!=notice.getUserId()) {
            return false;
        }
        return true;
    }

    public List<NoticeRes> getResList(List<Notice> noticeList){
        Collections.sort(noticeList);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<NoticeRes> res= new ArrayList<>();
        for(Notice notice : noticeList){
            NoticeRes noticeRes = new NoticeRes();
            noticeRes.setId(notice.getId());
            noticeRes.setMessage(notice.getMessage());
            noticeRes.setReader(notice.getReader());
            noticeRes.setSendTime(simpleDateFormat.format(notice.getSendTime()));
            res.add(noticeRes);
        }
        return res;
    }
}

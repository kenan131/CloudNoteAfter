package com.bin.service.imple;

import com.bin.model.Enum.ResStatus;
import com.bin.model.dto.NoteBook.NoteBookDto;
import com.bin.model.dto.NoteBook.ResNoteBookDto;
import com.bin.model.entity.NoteBook;
import com.bin.model.entity.User;
import com.bin.model.mapper.BookMapper;
import com.bin.model.mapper.NoteBookMapper;
import com.bin.service.NoteBookServiceI;
import com.bin.util.HostHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NoteBookService implements NoteBookServiceI {

    @Autowired
    private HostHolder hostHolder;

    @Autowired(required = false)
    private NoteBookMapper noteBookMapper;

    @Autowired(required = false)
    private BookMapper bookMapper;
    @Override
    public Map<String, Object> getNoteBookList(NoteBookDto dto) {
        Map<String,Object> res = new HashMap();
        res.put("type", ResStatus.ERROR.getType());
        User user = hostHolder.getUser();
        if(user==null){
            res.put("message","用户未登录，请登录！");
            return res;
        }
        List<NoteBook> noteBooks;
        if(!StringUtils.isBlank(dto.getName())){
            noteBooks = noteBookMapper.selectNoteName(user.getId(), dto.getName());
        }else{
            noteBooks = noteBookMapper.selectNotes(user.getId());
        }
        List<ResNoteBookDto> data = new ArrayList<>();
        for(NoteBook noteBook : noteBooks){
            ResNoteBookDto resNoteBookDto = new ResNoteBookDto();
            resNoteBookDto.setId(noteBook.getId());
            resNoteBookDto.setNotebookName(noteBook.getName());
            data.add(resNoteBookDto);
        }
        res.put("type",ResStatus.Success.getType());
        res.put("message","获取笔记本数据成功！");
        res.put("data",data);
        return res;
    }

    @Override
    @Transactional
    public Map<String, Object> deleteBNoteBook(Integer noteId) {
        Map<String,Object> res = new HashMap();
        res.put("type", ResStatus.ERROR.getType());
        User user = hostHolder.getUser();
        if(user==null){
            res.put("message","用户未登录，请登录！");
            return res;
        }
        if(noteId == null){
            res.put("message","参数缺失，请重试！");
            return res;
        }
        if(noteId==999){
            res.put("message","不能删除回收站！");
            return res;
        }
        List list = bookMapper.selectBooks(noteId);
        if (list.size()!=0){
            res.put("message","请先将笔记本下的所有笔记都删除后，再删除笔记本！");
            return res;
        }
        int cnt = noteBookMapper.deleteNoteBook(noteId);
        if(cnt!=1){
            res.put("message","删除笔记本失败，请重试！");
            return res;
        }
        res.put("type",ResStatus.Success.getType());
        res.put("message","删除笔记本成功！");
        return res;
    }

    @Override
    public Map<String, Object> insertNoteBook(NoteBookDto dto) {
        Map<String,Object> res = new HashMap();
        res.put("type", ResStatus.ERROR.getType());
        User user = hostHolder.getUser();
        if(user==null){
            res.put("message","用户未登录，请登录！");
            return res;
        }
        if(StringUtils.isBlank(dto.getName())){
            res.put("message","笔记本名称参数为空，请重试！");
            return res;
        }
        NoteBook noteBook = new NoteBook(dto.getName(),user.getId());
        int cnt = noteBookMapper.insertNoteBook(noteBook);
        if(cnt!=1){
            res.put("message","新建笔记本失败，请重试！");
            return res;
        }
        res.put("type",ResStatus.Success.getType());
        res.put("message","新建笔记本成功！");
        return res;
    }

    @Override
    public Map<String, Object> updateNoteBookName(NoteBookDto dto) {
        Map<String,Object> res = new HashMap();
        res.put("type", ResStatus.ERROR.getType());
        User user = hostHolder.getUser();
        if(user==null){
            res.put("message","用户未登录，请登录！");
            return res;
        }
        if(StringUtils.isBlank(dto.getName())){
            res.put("message","笔记本名称参数为空，请重试！");
            return res;
        }
        if(dto.getNoteBookId() == null){
            res.put("message","参数缺失，请重试！");
            return res;
        }
        if(dto.getNoteBookId()==999){
            res.put("message","不能更改回收站的名称！");
            return res;
        }
        int cnt = noteBookMapper.updateNoteName(dto.getNoteBookId(), dto.getName());
        if(cnt!=1){
            res.put("message","重命名笔记本失败，请重试！");
            return res;
        }
        res.put("type",ResStatus.Success.getType());
        res.put("message","重命名笔记本成功！");
        return res;
    }
}

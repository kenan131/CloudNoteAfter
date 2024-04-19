package com.bin.service.imple;

import com.alibaba.fastjson.JSON;
import com.bin.model.dto.Book.BookDto;
import com.bin.model.dto.Book.ResBookDto;
import com.bin.model.Enum.BookStatus;
import com.bin.model.Enum.NoticeStatus;
import com.bin.model.Enum.ResStatus;
import com.bin.model.Enum.UserStatus;
import com.bin.model.entity.Book;
import com.bin.model.entity.NoteBook;
import com.bin.model.entity.Notice;
import com.bin.model.entity.User;
import com.bin.model.mapper.BookMapper;
import com.bin.model.mapper.NoteBookMapper;
import com.bin.model.mapper.NoticeMapper;
import com.bin.queue.MyQueueBroker;
import com.bin.queue.dto.MessageDto;
import com.bin.service.BookServiceI;
import com.bin.util.HostHolder;
import com.bin.util.Md5Util;
import com.bin.util.RedisKeyUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class BookService implements BookServiceI {

    @Autowired
    protected HostHolder hostHolder;

    @Autowired(required = false)
    protected BookMapper bookMapper;

    @Autowired(required = false)
    protected NoteBookMapper noteBookMapper;

    @Autowired(required = false)
    protected NoticeMapper noticeMapper;

    @Autowired(required = false)
    RedisTemplate redisTemplate;

    @Override
    public Map<String, Object> getBookList(BookDto dto) {
        Map<String,Object> res = new HashMap();
        res.put("type", ResStatus.ERROR.getType());
        if(dto.getNoteBookId()==null){
            res.put("message","参数缺失，请重重试！");
            return res;
        }
        List<Book> lists;
        User user = hostHolder.getUser();
        if(StringUtils.isBlank(dto.getBookName())){
            if(dto.getNoteBookId()==BookStatus.RUBBISH.getCode()){
                lists=bookMapper.selectBookListByDel(user.getId());
            }
            else{
                lists = bookMapper.selectBooks(dto.getNoteBookId());
            }
        }
        else{
            if(dto.getNoteBookId()==BookStatus.RUBBISH.getCode()){
                lists=bookMapper.selectBookListByDelAndName(user.getId(),dto.getBookName());
            }
            else{
                lists = bookMapper.selectBookByName(dto.getNoteBookId(),dto.getBookName());
            }
        }
        List<ResBookDto> resBookList = getResBookList(lists);
        res.put("type",ResStatus.Success.getType());
        res.put("message","成功获取笔记列表数据");
        res.put("data",resBookList);
        return res;
    }

    @Override
    public Map<String, Object> insertBook(BookDto dto) {
        Map<String,Object> res = new HashMap();
        res.put("type", ResStatus.ERROR.getType());
        User user = hostHolder.getUser();
        if(StringUtils.isBlank(dto.getBookName())&&dto.getNoteBookId()==null){
            res.put("message","参数缺失，请重新输入！");
            return res;
        }
        Book book = new Book(dto.getBookName(),"", BookStatus.UNDELETE.getCode(), BookStatus.UNSHARE.getCode(),new Date(),new Date(),dto.getNoteBookId());
        int cnt = bookMapper.insertBook(book);
        if(cnt!=1){
            res.put("message","新建笔记失败，请重试！");
            return res;
        }
        res.put("type",ResStatus.Success.getType());
        res.put("message","新建笔记成功！");
        return res;
    }

    @Override
    public Map<String, Object> deleteBook(BookDto dto) {
        Map<String,Object> res = new HashMap();
        res.put("type", ResStatus.ERROR.getType());
        User user = hostHolder.getUser();
        if(dto.getBookId() == null){
            res.put("message","参数缺失，请重试！");
            return res;
        }
        Book book = bookMapper.selectBookById(dto.getBookId());
        if(book==null){
            res.put("message","要删除的笔记不存在，请重试！");
            return res;
        }
        if(!isUserBook(book)){
            res.put("message","只能删除自己拥有的笔记！");
            return res;
        }
        int cnt = bookMapper.deleteBook(dto.getBookId(), user.getId());
        if(cnt!=1){
            res.put("message","删除笔记失败，请重试！");
            return res;
        }
        res.put("type",ResStatus.Success.getType());
        res.put("message","删除笔记成功！");
        return res;
    }

    @Override
    public Map<String, Object> updateBookName(BookDto dto) {
        Map<String,Object> res = new HashMap();
        res.put("type", ResStatus.ERROR.getType());
        if(dto.getBookId() == null && StringUtils.isBlank(dto.getBookName())){
            res.put("message","参数缺失，请重试！");
            return res;
        }
        Book book = bookMapper.selectBookById(dto.getBookId());
        if(book==null){
            res.put("message","要更改的笔记不存在，请重试！");
            return res;
        }
        if(!isUserBook(book)){
            res.put("message","只能更改自己拥有的笔记！");
            return res;
        }
        int cnt = bookMapper.updateBookName(dto.getBookId(),dto.getBookName());
        if(cnt!=1){
            res.put("message","更改笔记名失败，请重试！");
            return res;
        }
        res.put("type",ResStatus.Success.getType());
        res.put("message","更改笔记名成功！");
        return res;
    }

    @Override
    public Map<String, Object> setShareBook(BookDto dto) {
        Map<String,Object> res = new HashMap();
        res.put("type", ResStatus.ERROR.getType());
        if(dto.getBookId() == null || dto.getShareStatus()==null){
            res.put("message","参数缺失，请重试！");
            return res;
        }
        Book book = bookMapper.selectBookById(dto.getBookId());
        if(book==null){
            res.put("message","要更改的笔记不存在，请重试！");
            return res;
        }
        if(!isUserBook(book)){
            res.put("message","只能共享自己拥有的笔记！");
            return res;
        }
        int cnt = bookMapper.shareBook(dto.getBookId(),dto.getShareStatus());
        if(cnt!=1){
            res.put("message","共享笔记名失败，请重试！");
            return res;
        }
        res.put("type",ResStatus.Success.getType());
        res.put("message","共享笔记成功！");
        return res;
    }

    @Override
    public Map<String, Object> shiftBookOnNoteBook(BookDto dto) {
        Map<String,Object> res = new HashMap();
        res.put("type", ResStatus.ERROR.getType());
        if(dto.getBookId() == null || dto.getNoteBookId()==null){
            res.put("message","参数缺失，请重试！");
            return res;
        }
        Book book = bookMapper.selectBookById(dto.getBookId());
        if(book==null){
            res.put("message","要移动的笔记不存在，请重试！");
            return res;
        }
        if(!isUserBook(book)){
            res.put("message","只能移动自己拥有的笔记！");
            return res;
        }
        int cnt = bookMapper.shiftNoteBook(dto.getBookId(), dto.getNoteBookId());
        if(cnt!=1){
            res.put("message","更改笔记所属笔记本失败，请重试！");
            return res;
        }
        res.put("type",ResStatus.Success.getType());
        res.put("message","更改笔记所属笔记本成功！");
        return res;
    }

    //永久删除方法
    @Override
    public Map<String, Object> realDelBook(BookDto dto) {
        Map<String,Object> res = new HashMap();
        res.put("type", ResStatus.ERROR.getType());
        User user = hostHolder.getUser();
        if(dto.getBookId() == null ){
            res.put("message","参数缺失，请重试！");
            return res;
        }
        Book book = bookMapper.selectBookById(dto.getBookId());
        if(book==null){
            res.put("message","删除的笔记不存在，请重试！");
            return res;
        }
        if(book.getDel()==-1){
            res.put("message","只能在回收站中永久删除笔记，请重试！");
            return res;
        }
        if(book.getDel()!=user.getId()){
            res.put("message","删除用户权限错误，请重试！");
            return res;
        }
        int cnt = bookMapper.realDelBook(dto.getBookId());
        if(cnt!=1){
            res.put("message","永久删除笔记失败，请重试！");
            return res;
        }
        res.put("type",ResStatus.Success.getType());
        res.put("message","永久删除笔记成功！");
        return res;
    }

    @Override
    public Map<String, Object> getShareBookList(BookDto dto) {
        Map<String,Object> res = new HashMap();
        res.put("type", ResStatus.ERROR.getType());
        List<Book> lists = bookMapper.selectBookListByShare(BookStatus.SHARE.getCode());
        List<ResBookDto> resBookList = getResBookList(lists);
        res.put("type",ResStatus.Success.getType());
        res.put("message","成功获取笔记列表数据");
        res.put("data",resBookList);
        return res;
    }

    @Override
    public Map<String, Object> getShareSuccess(BookDto dto) {
        Map<String,Object> res = new HashMap();
        res.put("type", ResStatus.ERROR.getType());
        List<Book> lists = bookMapper.selectBookListByShare(BookStatus.SHARESUCCESS.getCode());
        List<ResBookDto> resBookList = getResBookList(lists);
        res.put("type",ResStatus.Success.getType());
        res.put("message","成功获取笔记列表数据");
        res.put("data",resBookList);
        return res;
    }

    @Override
    public Map<String, Object> restoreBook(BookDto dto) {
        Map<String,Object> res = new HashMap();
        res.put("type", ResStatus.ERROR.getType());
        if(dto.getBookId() == null){
            res.put("message","参数缺失，请重试！");
            return res;
        }
        Book book = bookMapper.selectBookById(dto.getBookId());
        if(book==null){
            res.put("message","要还原的笔记不存在，请重试！");
            return res;
        }
        if(!isUserBook(book)){
            res.put("message","只能还原自己拥有的笔记！");
            return res;
        }
        int cnt = bookMapper.deleteBook(dto.getBookId(), BookStatus.UNDELETE.getCode());
        if(cnt!=1){
            res.put("message","还原笔记失败，请重试！");
            return res;
        }
        res.put("type",ResStatus.Success.getType());
        res.put("message","还原笔记成功！");
        return res;
    }

    @Override
    public Map<String, Object> getBookText(BookDto dto) {
        Map<String,Object> res = new HashMap();
        res.put("type", ResStatus.ERROR.getType());
        if(dto.getBookId() == null){
            res.put("message","参数缺失，请重试！");
            return res;
        }
        Book book = bookMapper.selectBookById(dto.getBookId());
        if(book==null){
            res.put("message","获取数据的笔记不存在，请重试！");
            return res;
        }
        if(!isUserBook(book)&&book.getShare()!=BookStatus.SHARE.getCode()&&book.getShare()!=BookStatus.SHARESUCCESS.getCode()){
            res.put("message","只能获取自己拥有的笔记！");
            return res;
        }
        String text = bookMapper.getBookText(dto.getBookId());
        res.put("type",ResStatus.Success.getType());
        res.put("message","获取笔记成功！");
        res.put("data",text);
        return res;
    }

    @Override
    public Map<String, Object> saveBook(BookDto dto) {
        Map<String,Object> res = new HashMap();
        res.put("type", ResStatus.ERROR.getType());
        if(dto.getBookId() == null || dto.getText()==null){
            res.put("message","参数缺失，请重试！");
            return res;
        }
        Book book = bookMapper.selectBookById(dto.getBookId());
        if(book==null){
            res.put("message","要保存的笔记不存在，请重试！");
            return res;
        }
        if(!isUserBook(book)){
            res.put("message","只能保存自己拥有的笔记！");
            return res;
        }
        int cnt = bookMapper.saveBook(dto.getBookId(), dto.getText());
        if(cnt!=1){
            res.put("message","还原笔记失败，请重试！");
            return res;
        }
        res.put("type",ResStatus.Success.getType());
        res.put("message","还原笔记成功！");
        return res;
    }

    @Override
    public Map<String, Object> downLoadBook(BookDto dto) {
        Map<String,Object> res = new HashMap();
        res.put("type", ResStatus.ERROR.getType());
        if(dto.getBookId() == null){
            res.put("message","参数缺失，请重试！");
            return res;
        }
        Book book = bookMapper.selectBookById(dto.getBookId());
        if(book==null){
            res.put("message","要下载的笔记不存在，请重试！");
            return res;
        }
        if(!isUserBook(book)){
            res.put("message","只能下载自己拥有的笔记！");
            return res;
        }
        res.put("type",ResStatus.Success.getType());
        res.put("message",book.getName());//笔记名
        res.put("data",book.getText());
        return res;
    }

    @Override
    public Map<String, Object> importBookFromFile(BookDto dto) {
        Map<String,Object> res = new HashMap();
        res.put("type", ResStatus.ERROR.getType());
        if(dto.getNoteBookId() == null || dto.getBookName() == null ){
            res.put("message","参数缺失，请重试！");
            return res;
        }
        String name = dto.getBookName().substring(0, dto.getBookName().length() - 3);
        Book book = new Book(name,dto.getText(), BookStatus.UNDELETE.getCode(), BookStatus.UNSHARE.getCode(),new Date(),new Date(),dto.getNoteBookId());
        int cnt = bookMapper.insertBook(book);
        if(cnt!=1){
            res.put("message","笔记导入失败，请重试！");
            return res;
        }
        res.put("type",ResStatus.Success.getType());
        res.put("message","笔记导入成功！");
        return res;
    }

    @Override
    public Map<String, Object> examineBookShareStatus(BookDto dto) {
        Map<String,Object> res = new HashMap();
        res.put("type", ResStatus.ERROR.getType());
        User user = hostHolder.getUser();
        if(user.getIsAdmin() != UserStatus.ADMIN.getCode()){
            res.put("message","该功能，只能由管理员进行操作！");
            return res;
        }
        if(dto.getBookId()==null||dto.getShareStatus()==null||(dto.getShareStatus()!=0&&dto.getShareStatus()!=2) || dto.getMessage()==null){
            res.put("message","参数缺失，请重试！");
            return res;
        }
        Book book = bookMapper.selectBookById(dto.getBookId());
        if(book==null){
            res.put("message","要操作的笔记不存在，请重试！");
            return res;
        }
        NoteBook noteBook = noteBookMapper.selectNoteBookById(book.getNoteId());
        if(noteBook==null){
            res.put("message","笔记数据异常，请重试！");
            return res;
        }
        Notice notice = new Notice(NoticeStatus.UNREADER.getCode(), dto.getMessage(), noteBook.getUserId(), new Date());
        MessageDto messageDto = new MessageDto("notice", JSON.toJSONString(notice));
        int cnt = bookMapper.shareBook(book.getId(), dto.getShareStatus());
        if(cnt != 1){
            throw new RuntimeException("笔记共享审核操作失败，请重试！");
        }
        MyQueueBroker.send(messageDto);
        res.put("type",ResStatus.Success.getType());
        res.put("message","笔记共享审核操作成功！");
        return res;
    }

    @Override
    public Map<String, Object> checkIsAdmin() {
        Map<String,Object> res = new HashMap();
        res.put("type", ResStatus.ERROR.getType());
        User user = hostHolder.getUser();
        if(user.getIsAdmin() != UserStatus.ADMIN.getCode()){
            res.put("message","该功能，只能由管理员进行操作！");
            return res;
        }
        res.put("type",ResStatus.Success.getType());
        res.put("message","权限校验成功！");
        return res;
    }

    @Override
    public Map<String, Object> shareFriend(BookDto dto) {
        Map<String,Object> res = new HashMap();
        res.put("type", ResStatus.ERROR.getType());
        if(dto.getBookId()==null){
            res.put("message","参数缺失，请重试！");
            return res;
        }
        Book book = bookMapper.selectBookById(dto.getBookId());
        if(book==null){
            res.put("message","笔记不存在，请重试！");
            return res;
        }
        String uuid = Md5Util.generateUUID();
        uuid=uuid.substring(0,6);
        String shareTicket = RedisKeyUtil.getShareTicket(uuid);
        redisTemplate.opsForValue().set(shareTicket,book,24, TimeUnit.HOURS);
        res.put("message","生成分享连接成功！");
        res.put("type",ResStatus.Success.getType());
        res.put("data",uuid);
        return res;
    }

    @Override
    public Map<String, Object> getShareBook(String uuid) {
        Map<String,Object> res = new HashMap();
        res.put("type", ResStatus.ERROR.getType());
        if(uuid==null){
            res.put("message","参数缺失，请重试！");
            return res;
        }
        String shareTicket = RedisKeyUtil.getShareTicket(uuid);
        Book book = (Book) redisTemplate.opsForValue().get(shareTicket);
        res.put("message","获取分享笔记数据成功！");
        res.put("type",ResStatus.Success.getType());
        res.put("data",book);
        return res;
    }

    public List<ResBookDto> getResBookList(List<Book> list){
        List<ResBookDto> res = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(Book book : list){
            ResBookDto resBookDto = new ResBookDto();
            resBookDto.setBookId(book.getId());
            resBookDto.setBookName(book.getName());
            String str="";
            if(book.getShare()==0){
                str="未共享";
            }
            else if(book.getShare()==1){
                str="共享中，待审核";
            }
            else{
                str="已共享";
            }
            resBookDto.setCreateTime("创建于："+simpleDateFormat.format(book.getCreateTime())+"  " + str);
            resBookDto.setShare(book.getShare());
            res.add(resBookDto);
        }
        return res;
    }
    //判断该笔记是否是当前用户的
    public boolean isUserBook(Book book){
        User user = hostHolder.getUser();
        if(user==null)
            return false;
        List<NoteBook> noteBooks = noteBookMapper.selectNotes(user.getId());
        for(NoteBook noteBook : noteBooks){
            //如果该用户拥有该笔记本就返回true
            if(noteBook.getId()==book.getNoteId())
                return true;
        }
        return false;
    }
}

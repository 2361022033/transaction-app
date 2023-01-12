package junfukt.com.service.impl;

import junfukt.com.controller.book.convert.BookConvert;
import junfukt.com.controller.book.dto.BookAddReq;
import junfukt.com.controller.book.dto.BookDetailResp;
import junfukt.com.domain.entity.BookInfo;
import junfukt.com.domain.mapper.BookInfoMapper;
import junfukt.com.service.BookService;
import junfukt.com.utils.ServiceExcpetion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Slf4j
@Service
public class BookServiceImpl implements BookService {
    @Resource
    private BookInfoMapper bookInfoMapper;

    /**
     * 添加图书
     * @param bookAddReq
     */
    @Override
    public void add(BookAddReq bookAddReq) {
        // 用户信息应该去缓存中获取 todo
        bookInfoMapper.insertSelective(BookConvert.INSTANCE.convert(bookAddReq));
    }


    /**
     * 图书详情
     * @param bookId
     * @return
     */
    @Override
    public BookDetailResp detail(Long bookId) {
        if (Objects.isNull(bookId)){
            throw new ServiceExcpetion(500,"图书id不能为空!");
        }
        BookInfo bookInfo = bookInfoMapper.selectByPrimaryKey(bookId);
        if (Objects.isNull(bookInfo)){
            throw new ServiceExcpetion(500,"图书已不存在!");
        }
        return BookConvert.INSTANCE.convert(bookInfo);
    }
}

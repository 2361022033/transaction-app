package com.service.impl;

import com.controller.book.convert.BookConvert;
import com.controller.book.dto.req.BookAddReq;
import com.controller.book.dto.req.BookPageReq;
import com.controller.book.dto.req.BookUpdateReq;
import com.controller.book.dto.resp.BookDetailResp;
import com.dictionaries.BookStatus;
import com.domain.entity.BookInfo;
import com.domain.mapper.BookInfoMapper;
import com.service.BookService;
import com.infrastructure.page.BasePageResp;
import com.infrastructure.page.PageResultUtils;
import com.infrastructure.ServiceExcpetion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class BookServiceImpl implements BookService {
    @Resource
    private BookInfoMapper bookInfoMapper;

    /**
     * 上架图书
     *
     * @param bookAddReq
     */
    @Override
    public void add(BookAddReq bookAddReq) {
        // 用户信息应该去缓存中获取 todo
//        bookAddReq.setSallerId();
        BookInfo info = BookConvert.INSTANCE.convert(bookAddReq);
        info.setStatus(BookStatus.ON_SALE);
        bookInfoMapper.insertSelective(info);
        // 用户的在售数量+1

    }


    /**
     * 图书分页列表
     *
     * @param req
     * @return
     */
    @Override
    public BasePageResp<BookDetailResp> page(BookPageReq req) {
        Long count = bookInfoMapper.pageCount(req);
        if (count < 1) {
            return PageResultUtils.defaultPageResult(req);
        }
        List<BookInfo> list = bookInfoMapper.page(req);
        return PageResultUtils.buildPageResult(req, count, BookConvert.INSTANCE.convert(list));
    }

    /**
     * 图书详情
     *
     * @param bookId
     * @return
     */
    @Override
    public BookDetailResp detail(Long bookId) {
        if (Objects.isNull(bookId)) {
            throw new ServiceExcpetion(500, "图书id不能为空!");
        }
        BookInfo bookInfo = bookInfoMapper.selectByPrimaryKey(bookId);
        if (Objects.isNull(bookInfo)) {
            throw new ServiceExcpetion(500, "图书已不存在!");
        }
        return BookConvert.INSTANCE.convert(bookInfo);
    }

    /**
     * 编辑图书
     * @param req
     */
    @Override
    public void update(BookUpdateReq req) {
        BookInfo book = bookInfoMapper.selectByPrimaryKey(req.getId());
        if (book==null){
            throw new ServiceExcpetion(500, "图书已不存在!");
        }
        bookInfoMapper.updateByPrimaryKeySelective(BookConvert.INSTANCE.convert(req));
    }

}

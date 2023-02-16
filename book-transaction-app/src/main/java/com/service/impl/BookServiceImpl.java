package com.service.impl;

import com.controller.book.convert.BookConvert;
import com.controller.book.dto.req.BookAddReq;
import com.controller.book.dto.req.BookPageReq;
import com.controller.book.dto.req.BookUpdateReq;
import com.controller.book.dto.resp.BookDetailResp;
import com.dictionaries.BookStatus;
import com.domain.entity.BookInfo;
import com.domain.mapper.BookInfoMapper;
import com.domain.mapper.UserInfoMapper;
import com.infrastructure.ServiceExcpetion;
import com.infrastructure.page.BasePageResp;
import com.infrastructure.page.PageResultUtils;
import com.infrastructure.threadPool.ThreadPoolConfig;
import com.service.BookService;
import com.service.in.BookAddIn;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.Executor;

@Slf4j
@Service
public class BookServiceImpl implements BookService {
    @Resource
    private BookInfoMapper bookInfoMapper;
    @Resource
    private UserInfoMapper userInfoMapper;

    @Autowired
    @Qualifier("onSaleNumServiceExecutor")
    private Executor onSaleNumServiceExecutor;
    /**
     * 上架图书
     *
     * @param in
     */
    @Override
    public void add(BookAddIn in) {
        BookInfo info =new BookInfo();
        BeanUtils.copyProperties(in,info);
        info.setStatus(BookStatus.ON_SALE);
        bookInfoMapper.insertSelective(info);
        // 用户的在售数量+1
        onSaleNumServiceExecutor.execute(()->userInfoMapper.updateOnSaleNumber(in.getSallerId()));
    }


    /**
     * 图书分页列表
     *
     * @param req
     * @return
     */
    @Override
    public BasePageResp<BookInfo> page(BookPageReq req) {
        Long count = bookInfoMapper.pageCount(req);
        if (count < 1) {
            return PageResultUtils.defaultPageResult(req);
        }
        return PageResultUtils.buildPageResult(req, count, bookInfoMapper.page(req));
    }

    /**
     * 图书详情
     *
     * @param bookId
     * @return
     */
    @Override
    public BookInfo detail(Long bookId) {
        if (Objects.isNull(bookId)) {
            throw new ServiceExcpetion(500, "图书id不能为空!");
        }
        BookInfo bookInfo = bookInfoMapper.selectByPrimaryKey(bookId);
        if (Objects.isNull(bookInfo)) {
            throw new ServiceExcpetion(500, "图书已不存在!");
        }
        return bookInfo;
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

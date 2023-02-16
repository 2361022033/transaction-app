package com.service;


import com.controller.book.dto.req.BookPageReq;
import com.controller.book.dto.req.BookUpdateReq;
import com.domain.entity.BookInfo;
import com.infrastructure.page.BasePageResp;
import com.service.in.BookAddIn;

public interface BookService {
    /**
     * 上架图书
     * @param in
     */
    void add(BookAddIn in);
    /**
     * 图书分页列表
     * @param req
     * @return
     */
    BasePageResp<BookInfo> page(BookPageReq req);
    /**
     * 图书详情
     * @param bookId
     */
    BookInfo detail(Long bookId);

    /**
     * 编辑图书
     * @param req
     */
    void update(BookUpdateReq req);
}

package com.service;


import com.controller.book.dto.req.BookAddReq;
import com.controller.book.dto.req.BookPageReq;
import com.controller.book.dto.req.BookUpdateReq;
import com.controller.book.dto.resp.BookDetailResp;
import com.domain.entity.BookInfo;
import com.infrastructure.page.BasePageResp;

public interface BookService {
    /**
     * 上架图书
     * @param bookAddReq
     */
    void add(BookAddReq bookAddReq);
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

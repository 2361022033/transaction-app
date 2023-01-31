package junfukt.com.service;


import junfukt.com.controller.book.dto.req.BookAddReq;
import junfukt.com.controller.book.dto.req.BookPageReq;
import junfukt.com.controller.book.dto.req.BookUpdateReq;
import junfukt.com.controller.book.dto.resp.BookDetailResp;
import junfukt.com.utils.BasePageResp;

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
    BasePageResp<BookDetailResp> page(BookPageReq req);
    /**
     * 图书详情
     * @param bookId
     */
    BookDetailResp detail(Long bookId);

    /**
     * 编辑图书
     * @param req
     */
    void update(BookUpdateReq req);
}

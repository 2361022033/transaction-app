package junfukt.com.service;


import junfukt.com.controller.book.dto.BookAddReq;
import junfukt.com.controller.book.dto.BookDetailResp;

public interface BookService {
    /**
     * 添加图书
     * @param bookAddReq
     */
    void add(BookAddReq bookAddReq);

    /**
     * 图书详情
     * @param bookId
     */
    BookDetailResp detail(Long bookId);



}

package booktransaction.com.controller.book;

import booktransaction.com.controller.book.dto.req.BookAddReq;
import booktransaction.com.controller.book.dto.req.BookPageReq;
import booktransaction.com.controller.book.dto.req.BookUpdateReq;
import booktransaction.com.controller.book.dto.resp.BookDetailResp;
import booktransaction.com.service.BookService;
import booktransaction.com.utils.BasePageResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/book")
public class BookController {
    @Resource
    BookService bookService;

    @PostMapping(value = "/add", name = "上架图书")
    public void add(BookAddReq req) {
        bookService.add(req);
    }

    @GetMapping(value = "/page", name = "图书分页列表")
    public BasePageResp<BookDetailResp> page(BookPageReq req) {
        return bookService.page(req);
    }

    @GetMapping(value = "/detail", name = "图书详情")
    public BookDetailResp detail(@RequestParam("bookId") Long bookId) {
        return bookService.detail(bookId);
    }

    @PostMapping(value = "/update", name = "编辑图书")
    public void update(BookUpdateReq req) {
        bookService.update(req);
    }

}
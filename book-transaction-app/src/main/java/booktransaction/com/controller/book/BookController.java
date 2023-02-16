package booktransaction.com.controller.book;

import booktransaction.com.controller.book.dto.req.BookAddReq;
import booktransaction.com.controller.book.dto.req.BookPageReq;
import booktransaction.com.controller.book.dto.req.BookUpdateReq;
import booktransaction.com.controller.book.dto.resp.BookDetailResp;
import booktransaction.com.infrastructure.HttpResult;
import booktransaction.com.infrastructure.page.BasePageResp;
import booktransaction.com.service.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
@Api(tags = {"图书接口"})
@RestController
@RequestMapping("/book")
public class BookController {
    @Resource
    BookService bookService;

    @ApiOperation(value = "上架图书")
    @PostMapping(value = "/add", name = "上架图书")
    public HttpResult<Void> add(BookAddReq req) {
        bookService.add(req);
        return HttpResult.success();
    }

    @ApiOperation(value = "图书分页列表")
    @GetMapping(value = "/page", name = "图书分页列表")
    public HttpResult<BasePageResp<BookDetailResp>> page(BookPageReq req) {
        return HttpResult.success(bookService.page(req));
    }

    @ApiOperation(value = "图书详情")
    @GetMapping(value = "/detail", name = "图书详情")
    public HttpResult<BookDetailResp> detail(@RequestParam("bookId") Long bookId) {
        return HttpResult.success(bookService.detail(bookId));
    }

    @ApiOperation(value = "编辑图书")
    @PostMapping(value = "/update", name = "编辑图书")
    public HttpResult<Void> update(BookUpdateReq req) {
        bookService.update(req);
        return HttpResult.success();
    }

}

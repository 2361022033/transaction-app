package com.controller.book;

import com.controller.book.convert.BookConvert;
import com.controller.book.dto.req.BookAddReq;
import com.controller.book.dto.req.BookPageReq;
import com.controller.book.dto.req.BookUpdateReq;
import com.controller.book.dto.resp.BookDetailResp;
import com.infrastructure.HttpResult;
import com.infrastructure.page.BasePageResp;
import com.service.BookService;
import com.service.in.BookAddIn;
import com.utils.UserInfoUtil;
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
        BookAddIn in = BookConvert.INSTANCE.bookAddReq2In(req);
        in.setSallerId(UserInfoUtil.getUserId());
        bookService.add(in);
        return HttpResult.success();
    }

    @ApiOperation(value = "图书分页列表")
    @GetMapping(value = "/page", name = "图书分页列表")
    public HttpResult<BasePageResp<BookDetailResp>> page(BookPageReq req) {
        return HttpResult.success(BookConvert.INSTANCE.convert(bookService.page(req)));
    }

    @ApiOperation(value = "图书详情")
    @GetMapping(value = "/detail", name = "图书详情")
    public HttpResult<BookDetailResp> detail(@RequestParam("bookId") Long bookId) {
        return HttpResult.success(BookConvert.INSTANCE.convert(bookService.detail(bookId)));
    }

    @ApiOperation(value = "编辑图书")
    @PostMapping(value = "/update", name = "编辑图书")
    public HttpResult<Void> update(BookUpdateReq req) {
        bookService.update(req);
        return HttpResult.success();
    }

}

package junfukt.com.controller.book;

import junfukt.com.controller.book.dto.BookAddReq;
import junfukt.com.controller.book.dto.BookDetailResp;
import junfukt.com.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
@Slf4j
@RestController
@RequestMapping("/book")
public class BookController {
        @Resource
        BookService bookService;

        @RequestMapping("/test")
        public void test() {
            log.info("这是测试成功的slf4j日志:"+(20L/10L+ 1L));
        }

        @PostMapping(value = "/add", name = "添加图书")
        public void add(BookAddReq req) {
            bookService.add(req);
        }

        @GetMapping(value = "/detail", name = "图书详情")
        public BookDetailResp detail(@RequestParam("bookId") Long bookId) {
            return  bookService.detail(bookId);
        }
    }

package com.controller.bookType;

import com.controller.bookType.dto.resp.BookTypeConfigResp;
import com.infrastructure.HttpResult;
import com.service.BookTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = {"图书类型接口"})
@RestController
@RequestMapping("/bookType")
public class BookTypeController {
    @Resource
    BookTypeService bookTypeService;

    @ApiOperation(value = "所有图书类型")
    @GetMapping(value = "/list", name = "所有图书类型")
    public HttpResult<List<BookTypeConfigResp>> listAll() {
        return HttpResult.success(bookTypeService.listAll());
    }

}

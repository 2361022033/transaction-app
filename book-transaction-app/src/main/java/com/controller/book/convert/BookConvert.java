package com.controller.book.convert;

import com.controller.book.dto.req.BookAddReq;
import com.controller.book.dto.resp.BookDetailResp;
import com.domain.entity.BookInfo;
import com.infrastructure.page.BasePageResp;
import com.service.in.BookAddIn;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BookConvert {
    BookConvert INSTANCE = Mappers.getMapper(BookConvert.class);

    BookDetailResp convert(BookInfo bookInfo);

    BasePageResp<BookDetailResp> convert(BasePageResp<BookInfo> bookInfo);

    BookInfo convert(BookAddReq req);

    List<BookDetailResp> convert(List<BookInfo> list);

    BookAddIn bookAddReq2In(BookAddReq req);
}

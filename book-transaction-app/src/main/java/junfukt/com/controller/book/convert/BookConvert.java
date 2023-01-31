package junfukt.com.controller.book.convert;

import junfukt.com.controller.book.dto.req.BookAddReq;
import junfukt.com.controller.book.dto.req.BookUpdateReq;
import junfukt.com.controller.book.dto.resp.BookDetailResp;
import junfukt.com.domain.entity.BookInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BookConvert {
    BookConvert INSTANCE = Mappers.getMapper(BookConvert.class);

    BookDetailResp convert(BookInfo bookInfo);

    BookInfo convert(BookAddReq req);

    List<BookDetailResp> convert( List<BookInfo> list);

    BookInfo convert(BookUpdateReq req);


}

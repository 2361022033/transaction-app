package junfukt.com.controller.book.convert;

import junfukt.com.controller.book.dto.BookAddReq;
import junfukt.com.controller.book.dto.BookDetailResp;
import junfukt.com.domain.entity.BookInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookConvert {
    BookConvert INSTANCE = Mappers.getMapper(BookConvert.class);

    BookDetailResp convert(BookInfo bookInfo);

    BookInfo convert(BookAddReq bookInfo);

}

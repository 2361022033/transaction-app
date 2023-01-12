package junfukt.com.controller.book.convert;

import javax.annotation.Generated;
import junfukt.com.controller.book.dto.BookAddReq;
import junfukt.com.controller.book.dto.BookDetailResp;
import junfukt.com.domain.entity.BookInfo;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-01-12T17:03:28+0800",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 1.8.0_191 (Oracle Corporation)"
)
public class BookConvertImpl implements BookConvert {

    @Override
    public BookDetailResp convert(BookInfo bookInfo) {
        if ( bookInfo == null ) {
            return null;
        }

        BookDetailResp bookDetailResp = new BookDetailResp();

        bookDetailResp.setId( bookInfo.getId() );
        bookDetailResp.setBookName( bookInfo.getBookName() );
        bookDetailResp.setWriter( bookInfo.getWriter() );
        bookDetailResp.setPublishingHouse( bookInfo.getPublishingHouse() );
        bookDetailResp.setDescription( bookInfo.getDescription() );
        bookDetailResp.setPriceOriginal( bookInfo.getPriceOriginal() );
        bookDetailResp.setPriceNow( bookInfo.getPriceNow() );
        bookDetailResp.setTypeFirst( bookInfo.getTypeFirst() );
        bookDetailResp.setTypeSecond( bookInfo.getTypeSecond() );
        bookDetailResp.setImageOne( bookInfo.getImageOne() );
        bookDetailResp.setImageTwo( bookInfo.getImageTwo() );
        bookDetailResp.setImageThree( bookInfo.getImageThree() );
        bookDetailResp.setImageFour( bookInfo.getImageFour() );
        bookDetailResp.setVideo( bookInfo.getVideo() );
        bookDetailResp.setSallerId( bookInfo.getSallerId() );
        bookDetailResp.setCreateTime( bookInfo.getCreateTime() );
        bookDetailResp.setUpdateTime( bookInfo.getUpdateTime() );

        return bookDetailResp;
    }

    @Override
    public BookInfo convert(BookAddReq bookInfo) {
        if ( bookInfo == null ) {
            return null;
        }

        BookInfo bookInfo1 = new BookInfo();

        bookInfo1.setBookName( bookInfo.getBookName() );
        bookInfo1.setWriter( bookInfo.getWriter() );
        bookInfo1.setPublishingHouse( bookInfo.getPublishingHouse() );
        bookInfo1.setDescription( bookInfo.getDescription() );
        bookInfo1.setPriceOriginal( bookInfo.getPriceOriginal() );
        bookInfo1.setPriceNow( bookInfo.getPriceNow() );
        bookInfo1.setTypeFirst( bookInfo.getTypeFirst() );
        bookInfo1.setTypeSecond( bookInfo.getTypeSecond() );
        bookInfo1.setImageOne( bookInfo.getImageOne() );
        bookInfo1.setImageTwo( bookInfo.getImageTwo() );
        bookInfo1.setImageThree( bookInfo.getImageThree() );
        bookInfo1.setImageFour( bookInfo.getImageFour() );
        bookInfo1.setVideo( bookInfo.getVideo() );
        bookInfo1.setSallerId( bookInfo.getSallerId() );

        return bookInfo1;
    }
}

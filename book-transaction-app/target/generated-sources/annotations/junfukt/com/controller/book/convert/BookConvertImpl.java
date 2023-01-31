package junfukt.com.controller.book.convert;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import junfukt.com.controller.book.dto.req.BookAddReq;
import junfukt.com.controller.book.dto.req.BookUpdateReq;
import junfukt.com.controller.book.dto.resp.BookDetailResp;
import junfukt.com.domain.entity.BookInfo;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-01-31T13:33:33+0800",
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
        bookDetailResp.setStatus( bookInfo.getStatus() );
        bookDetailResp.setIsDeleted( bookInfo.getIsDeleted() );
        bookDetailResp.setCreateTime( bookInfo.getCreateTime() );
        bookDetailResp.setUpdateTime( bookInfo.getUpdateTime() );

        return bookDetailResp;
    }

    @Override
    public BookInfo convert(BookAddReq req) {
        if ( req == null ) {
            return null;
        }

        BookInfo bookInfo = new BookInfo();

        bookInfo.setBookName( req.getBookName() );
        bookInfo.setWriter( req.getWriter() );
        bookInfo.setPublishingHouse( req.getPublishingHouse() );
        bookInfo.setDescription( req.getDescription() );
        bookInfo.setPriceOriginal( req.getPriceOriginal() );
        bookInfo.setPriceNow( req.getPriceNow() );
        bookInfo.setTypeFirst( req.getTypeFirst() );
        bookInfo.setTypeSecond( req.getTypeSecond() );
        bookInfo.setImageOne( req.getImageOne() );
        bookInfo.setImageTwo( req.getImageTwo() );
        bookInfo.setImageThree( req.getImageThree() );
        bookInfo.setImageFour( req.getImageFour() );
        bookInfo.setVideo( req.getVideo() );
        bookInfo.setSallerId( req.getSallerId() );

        return bookInfo;
    }

    @Override
    public List<BookDetailResp> convert(List<BookInfo> list) {
        if ( list == null ) {
            return null;
        }

        List<BookDetailResp> list1 = new ArrayList<BookDetailResp>( list.size() );
        for ( BookInfo bookInfo : list ) {
            list1.add( convert( bookInfo ) );
        }

        return list1;
    }

    @Override
    public BookInfo convert(BookUpdateReq req) {
        if ( req == null ) {
            return null;
        }

        BookInfo bookInfo = new BookInfo();

        bookInfo.setId( req.getId() );
        bookInfo.setBookName( req.getBookName() );
        bookInfo.setWriter( req.getWriter() );
        bookInfo.setPublishingHouse( req.getPublishingHouse() );
        bookInfo.setDescription( req.getDescription() );
        bookInfo.setPriceOriginal( req.getPriceOriginal() );
        bookInfo.setPriceNow( req.getPriceNow() );
        bookInfo.setTypeFirst( req.getTypeFirst() );
        bookInfo.setTypeSecond( req.getTypeSecond() );
        bookInfo.setImageOne( req.getImageOne() );
        bookInfo.setImageTwo( req.getImageTwo() );
        bookInfo.setImageThree( req.getImageThree() );
        bookInfo.setImageFour( req.getImageFour() );
        bookInfo.setVideo( req.getVideo() );

        return bookInfo;
    }
}

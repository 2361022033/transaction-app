package junfukt.com.domain.mapper;


import junfukt.com.controller.book.dto.req.BookPageReq;
import junfukt.com.domain.entity.BookInfo;
import org.apache.ibatis.annotations.Mapper;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

@Mapper
public interface BookInfoMapper extends BaseMapper<BookInfo> {

    List<BookInfo> page(BookPageReq req);

    Long pageCount(BookPageReq req);
}
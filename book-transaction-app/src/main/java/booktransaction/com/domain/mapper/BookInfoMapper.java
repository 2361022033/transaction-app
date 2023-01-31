package booktransaction.com.domain.mapper;


import booktransaction.com.controller.book.dto.req.BookPageReq;
import booktransaction.com.domain.entity.BookInfo;
import org.apache.ibatis.annotations.Mapper;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

@Mapper
public interface BookInfoMapper extends BaseMapper<BookInfo> {

    List<BookInfo> page(BookPageReq req);

    Long pageCount(BookPageReq req);
}
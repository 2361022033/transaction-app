package com.domain.mapper;


import com.controller.book.dto.req.BookPageReq;
import com.domain.entity.BookInfo;
import org.apache.ibatis.annotations.Mapper;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

@Mapper
public interface BookInfoMapper extends BaseMapper<BookInfo> {
    Long pageCount(BookPageReq req);
    List<BookInfo> page(BookPageReq req);

}
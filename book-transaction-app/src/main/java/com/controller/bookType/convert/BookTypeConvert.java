package com.controller.bookType.convert;

import com.controller.bookType.dto.resp.BookTypeConfigResp;
import com.domain.entity.BookTypeConfig;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BookTypeConvert {
    BookTypeConvert INSTANCE = Mappers.getMapper(BookTypeConvert.class);

    List<BookTypeConfigResp> bookTypeConfigList2Resp(List<BookTypeConfig> bookTypeConfigs);

    BookTypeConfigResp bookTypeConfig2Resp(BookTypeConfig bookTypeConfig);
}

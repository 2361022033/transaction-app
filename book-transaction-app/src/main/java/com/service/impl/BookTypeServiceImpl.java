package com.service.impl;

import com.controller.bookType.convert.BookTypeConvert;
import com.controller.bookType.dto.resp.BookTypeConfigResp;
import com.domain.entity.BookTypeConfig;
import com.domain.mapper.BookTypeConfigMapper;
import com.service.BookTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BookTypeServiceImpl implements BookTypeService {

    @Autowired
    private BookTypeConfigMapper bookTypeConfigMapper;

    /**
     * 所有图书类型
     * @return
     */
    @Cacheable(value = "BookTypeConfigList:List",key="'BookTypeConfigList'")
    @Override
    public List<BookTypeConfigResp> listAll() {
        List<BookTypeConfig> bookTypeConfigs = bookTypeConfigMapper.selectAllThirdType();
        return BookTypeConvert.INSTANCE.bookTypeConfigList2Resp(bookTypeConfigs);
    }
}

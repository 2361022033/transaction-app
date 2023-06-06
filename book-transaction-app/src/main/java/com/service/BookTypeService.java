package com.service;


import com.controller.bookType.dto.resp.BookTypeConfigResp;

import java.util.List;

public interface BookTypeService {
    /**
     * 获取所有图书三级类型
     *
     * @return
     */
    List<BookTypeConfigResp> listAll();

}

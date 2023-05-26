package com.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.NumberUtil;
import com.controller.book.dto.req.BookPageReq;
import com.controller.book.dto.req.BookUpdateReq;
import com.dictionaries.BookStatus;
import com.domain.entity.BookInfo;
import com.domain.entity.BookTypeConfig;
import com.domain.mapper.BookInfoMapper;
import com.domain.mapper.BookTypeConfigMapper;
import com.domain.mapper.UserInfoMapper;
import com.infrastructure.DistributedLock;
import com.infrastructure.ServiceExcpetion;
import com.infrastructure.page.BasePageResp;
import com.infrastructure.page.PageResultUtils;
import com.service.BookService;
import com.service.in.BookAddIn;
import com.utils.UserInfoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BookServiceImpl implements BookService {
    @Resource
    private BookInfoMapper bookInfoMapper;
    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private BookTypeConfigMapper bookTypeConfigMapper;

    @Autowired
    @Qualifier("onSaleNumServiceExecutor")
    private Executor onSaleNumServiceExecutor;

    /**
     * 上架图书
     *
     * @param in
     */
    @Override
    public void add(BookAddIn in) {
        BookInfo info = new BookInfo();
        BeanUtils.copyProperties(in, info);
        info.setStatus(BookStatus.ON_SALE);
        fillTypeCodeAndName(info, in.getThirdTypeCode());
        bookInfoMapper.insertSelective(info);
        // 用户的在售数量+1
        onSaleNumServiceExecutor.execute(() -> userInfoMapper.updateOnSaleNumber(in.getSallerId()));
    }

    /**
     * 填充图书一二三级类型
     *
     * @param info
     * @param thirdTypeCode
     */
    public void fillTypeCodeAndName(BookInfo info, String thirdTypeCode) {
        List<BookTypeConfig> bookTypeConfigs = bookTypeConfigMapper.selectAllThirdType();
        Map<String, BookTypeConfig> typeConfigMap = bookTypeConfigs.stream().collect(Collectors.toMap(BookTypeConfig::getThirdTypeCode, e -> e));
        BookTypeConfig bookTypeConfig = typeConfigMap.get(thirdTypeCode);
        Assert.notNull(bookTypeConfig, () -> new ServiceExcpetion(500, "类型编码不正确"));
        info.setFirstTypeCode(bookTypeConfig.getFirstTypeCode());
        info.setFirstTypeName(bookTypeConfig.getFirstTypeName());
        info.setSecondTypeCode(bookTypeConfig.getSecondTypeCode());
        info.setSecondTypeName(bookTypeConfig.getSecondTypeName());
        info.setThirdTypeCode(bookTypeConfig.getThirdTypeCode());
        info.setThirdTypeName(bookTypeConfig.getThirdTypeName());
    }

    /**
     * 图书分页列表
     *
     * @param req
     * @return
     */
    @Override
    public BasePageResp<BookInfo> page(BookPageReq req) {
        Long count = bookInfoMapper.pageCount(req);
        if (count < 1) {
            return PageResultUtils.defaultPageResult(req);
        }
        return PageResultUtils.buildPageResult(req, count, bookInfoMapper.page(req));
    }

    /**
     * 图书详情
     *
     * @param bookId
     * @return
     */
    @Override
    public BookInfo detail(Long bookId) {
        if (Objects.isNull(bookId)) {
            throw new ServiceExcpetion(500, "图书id不能为空!");
        }
        BookInfo bookInfo = bookInfoMapper.selectByPrimaryKey(bookId);
        if (Objects.isNull(bookInfo)) {
            throw new ServiceExcpetion(500, "图书已不存在!");
        }
        return bookInfo;
    }

    /**
     * 编辑图书
     *
     * @param req
     */
    @Override
    public void update(BookUpdateReq req) {
        BookInfo book = bookInfoMapper.selectByPrimaryKey(req.getId());
        Assert.notNull(book, () -> new ServiceExcpetion(500, "图书不存在"));
        if (NumberUtil.equals(book.getSallerId(), UserInfoUtil.getUserId())) {
            throw new ServiceExcpetion(400, "没有编辑此书的权限");
        }
        BookInfo bookInfo = new BookInfo();
        BeanUtils.copyProperties(req, bookInfo);
        fillTypeCodeAndName(bookInfo, req.getThirdTypeCode());
        DistributedLock.tryLock("book:" + req.getId(), 0L, (succes) -> {
            if (!succes) {
                throw new ServiceExcpetion(400, "系统正忙,图书被锁定状态");
            }
            try {
                bookInfoMapper.updateByPrimaryKeySelective(bookInfo);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        });
    }

}

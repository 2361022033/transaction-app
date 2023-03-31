package com.domain.mapper;

import com.domain.entity.BookTypeConfig;
import org.apache.ibatis.annotations.Mapper;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

/**
 * <p>
 * 图书类型配置表 Mapper 接口
 * </p>
 *
 * @author sjf
 * @since 2023-03-29
 */
@Mapper
public interface BookTypeConfigMapper extends BaseMapper<BookTypeConfig> {
    /**
     * 查询所有类型
     * @return
     */
    List<BookTypeConfig> selectAllThirdType();


}

package junfukt.com.domain.mapper;


import junfukt.com.domain.entity.BookInfo;
import org.apache.ibatis.annotations.Mapper;
import tk.mybatis.mapper.common.BaseMapper;

@Mapper
public interface BookInfoMapper extends BaseMapper<BookInfo> {

}
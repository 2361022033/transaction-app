package junfukt.com.utils;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class PageResultUtils {

    public static <T> BasePageResp<T> buildPageResult(BasePageReq pageParam, Long total, List<T> list) {
        BasePageResp pageBo = new BasePageResp();
        pageBo.setPageNo(pageParam.getPageNo());
        pageBo.setPageSize(pageParam.getPageSize());
        pageBo.setTotal(total);
        pageBo.setPages((pageBo.getTotal() - 1) / pageBo.getPageSize() + 1);
        pageBo.setData(list);
        return pageBo;
    }

    public static <T> BasePageResp<T> defaultPageResult(BasePageReq pageParam) {
        BasePageResp pageBo = new BasePageResp();
        pageBo.setPageNo(pageParam.getPageNo());
        pageBo.setPageSize(pageParam.getPageSize());
        pageBo.setTotal(0L);
        pageBo.setPages(0L);
        pageBo.setData(Lists.newArrayList());
        return pageBo;
    }

}

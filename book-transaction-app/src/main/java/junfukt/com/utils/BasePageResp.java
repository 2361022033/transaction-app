package junfukt.com.utils;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class BasePageResp<T> implements Serializable {
    static final long serialVersionUID = 8980650536141157900L;
    private Long pageNo;
    private Long pageSize;
    private Long total;
    private Long pages;
    private List<T> data;

    public BasePageResp() {
    }

    public Long getPageNo() {
        return this.pageNo;
    }

    public void setPageNo(Long pageNo) {
        this.pageNo = pageNo;
    }

    public Long getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotal() {
        return this.total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getPages() {
        return this.pages;
    }

    public void setPages(Long pages) {
        this.pages = pages;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BasePageResp{" +
                "pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                ", total=" + total +
                ", pages=" + pages +
                ", data=" + data +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        BasePageResp<?> that = (BasePageResp<?>) o;
        return pageNo.equals(that.pageNo) && pageSize.equals(that.pageSize) && total.equals(that.total) && pages.equals(that.pages) && data.equals(that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pageNo, pageSize, total, pages, data);
    }

}

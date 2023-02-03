package booktransaction.com.infrastructure.page;

import java.io.Serializable;
import java.util.Objects;

public class BasePageReq implements Serializable {

    private static final long serialVersionUID = -6077627786765315116L;
    private long pageNo = 1L;
    private long pageSize = 10L;
    private String sorted;


    public long getPageNo() {
        return this.pageNo;
    }

    public void setPageNo(long pageNo) {
        this.pageNo = pageNo;
    }

    public long getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public String getSorted() {
        return this.sorted;
    }

    public void setSorted(String sorted) {
        this.sorted = sorted;
    }

    public long getOffset() {
        return (this.pageNo - 1L) * this.pageSize;
    }

    @Override
    public String toString() {
        return "BasePage{" +
                "pageNo=" + this.pageNo +
                ", pageSize=" + this.pageSize +
                ", sorted='" + this.sorted + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        BasePageReq basePage = (BasePageReq) o;
        return pageNo == basePage.pageNo && pageSize == basePage.pageSize && Objects.equals(sorted, basePage.sorted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pageNo, pageSize, sorted);
    }
}

package booktransaction.com.controller.book.dto.req;

import booktransaction.com.infrastructure.BasePageReq;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class BookPageReq extends BasePageReq implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 书名
     */
    private String bookName;

    /**
     * 作者
     */
    private String writer;

    /**
     * 最低价格
     */
    private BigDecimal minPriceNow;
    /**
     * 最高价格
     */
    private BigDecimal maxPriceNow;

    /**
     * 一级类型
     */
    private String typeFirst;

    /**
     * 二级类型
     */
    private String typeSecond;

}
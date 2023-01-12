package junfukt.com.controller.book.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Accessors(chain = true)
public class BookAddReq implements Serializable {
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
     * 出版社
     */
    private String publishingHouse;

    /**
     * 描述
     */
    private String description;

    /**
     * 原价
     */
    private BigDecimal priceOriginal;

    /**
     * 在售价格
     */
    private BigDecimal priceNow;

    /**
     * 一级类型
     */
    private String typeFirst;

    /**
     * 二级类型
     */
    private String typeSecond;

    /**
     * 图片一地址
     */
    private String imageOne;

    /**
     * 图片二地址
     */
    private String imageTwo;

    /**
     * 图片三地址
     */
    private String imageThree;

    /**
     * 图片四地址
     */
    private String imageFour;

    /**
     * 视频地址
     */
    private String video;

    /**
     * 卖家id
     */
    private Long sallerId;
}
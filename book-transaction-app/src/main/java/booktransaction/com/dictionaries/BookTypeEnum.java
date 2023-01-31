package booktransaction.com.dictionaries;

public enum BookTypeEnum {
    // 一类型
    JIAOCAI("教材"),
    XITI("习题"),
    XIAOSHUO("小说"),
    ZAZHI("杂志"),
    // 二级类型
    YUWEN("语文"),
    SHUXUE("数学"),
    YINGYU("英语"),
    WUXIA("武侠"),
    KEHUAN("科幻"),
    YANQING("言情");

    private final String value;
    BookTypeEnum(String value){
        this.value = value;
    }
    public String getValue(){
        return value;
    }

}

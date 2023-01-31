package booktransaction.com.dictionaries;

public enum SexEnum {
    JIAOCAI("男"),
    XITI("女");
    private final String value;
    SexEnum(String value){
        this.value = value;
    }
    public String getValue(){
        return value;
    }

}

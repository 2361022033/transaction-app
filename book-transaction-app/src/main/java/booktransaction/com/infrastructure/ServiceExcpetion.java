package booktransaction.com.infrastructure;

public final class ServiceExcpetion extends RuntimeException{
    private static final long serialVersionUID = 5976206518383488589L;
    private final int code;
    private final String message;

    public ServiceExcpetion(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}

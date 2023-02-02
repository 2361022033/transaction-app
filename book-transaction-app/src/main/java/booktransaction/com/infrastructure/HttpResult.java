package booktransaction.com.infrastructure;
import java.io.Serializable;

/**
 * @author sjf
 */
public final class HttpResult<T> implements Serializable {
    private static final long serialVersionUID = -753197112712711365L;
    private int code;
    private T data;
    private String message;

    private HttpResult() {
    }

    public static <T> HttpResult<T> success() {
        HttpResult<T> result = new HttpResult();
        result.setCode(0);
        result.setMessage("操作成功");
        return result;
    }

    public static <T> HttpResult<T> success(T data) {
        HttpResult<T> result = new HttpResult();
        result.setCode(0);
        result.setData(data);
        return result;
    }

    public static <T> HttpResult<T> success(String message, T data) {
        HttpResult<T> result = new HttpResult();
        result.setCode(0);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    public static <T> HttpResult<T> success(String message) {
        HttpResult<T> result = new HttpResult();
        result.setCode(0);
        result.setMessage(message);
        return result;
    }

    public static <T> HttpResult<T> failure(int code, String message) {
        HttpResult<T> result = new HttpResult();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public static <T> HttpResult<T> failure(int code, String message, T data) {
        HttpResult<T> result = new HttpResult();
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

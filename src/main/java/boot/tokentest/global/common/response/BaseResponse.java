package boot.tokentest.global.common.response;

import org.springframework.http.HttpStatus;

public class BaseResponse<T> {

    private HttpStatus status;
    private String message;
    private T data;

    public BaseResponse(final HttpStatus status, final String message, final T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}

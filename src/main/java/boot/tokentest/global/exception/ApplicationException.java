package boot.tokentest.global.exception;

public class ApplicationException extends RuntimeException {

    public ApplicationException(final ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}

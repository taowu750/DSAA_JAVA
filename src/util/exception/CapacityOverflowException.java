package util.exception;

/**
 * 当容量超出最大上限时，抛出此异常
 *
 * @author wutao
 */
public class CapacityOverflowException extends RuntimeException {
    public CapacityOverflowException(String message) {
        super(message);
    }
}

package work.idler.jilijili.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * 项目的基本异常
 *
 * @Auther:https://blog.idler.work
 * @Date:2019/5/6
 * @Description:com.iscolt.coltish.exception
 * @version:1.0
 */
public abstract class DtaiException extends RuntimeException {

    /**
     * 错误数据
     */
    private Object errorData;

    public DtaiException(String message) {
        super(message);
    }

    public DtaiException(String message, Throwable cause) {
        super(message, cause);
    }

    @NonNull
    public abstract HttpStatus getStatus();

    @Nullable
    public Object getErrorData() {
        return errorData;
    }

    /**
     * 设置错误数据
     *
     * @param errorData
     * @return 当前异常
     */
    @NonNull
    public DtaiException setErrorData(@Nullable Object errorData) {
        this.errorData = errorData;
        return this;
    }
}
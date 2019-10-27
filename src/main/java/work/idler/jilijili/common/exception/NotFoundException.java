package work.idler.jilijili.common.exception;

import org.springframework.http.HttpStatus;

/**
 * 未找到实体的异常
 *
 * @Auther:https://blog.idler.work
 * @Date:2019/5/6
 * @Description:com.iscolt.coltish.exception
 * @version:1.0
 */
public class NotFoundException extends DtaiException {


    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }
}

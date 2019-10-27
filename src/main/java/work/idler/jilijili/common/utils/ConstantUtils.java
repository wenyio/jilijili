package work.idler.jilijili.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 公共常量
 *
 * @Auther:https://blog.idler.work
 * @Date:2019/5/11
 * @Description:com.iscolt.coltish.content.constant
 * @version:1.0
 */
public class ConstantUtils {

    /**
     * user_session
     */
    public static String USER_SESSION_KEY = "user_session";

    /**
     * 登录错误提示
     */
    public static final String LOGIN_MSG = "message";

    /**
     * 修改信息成功提示 (成功后重新登录)
     */
    public static final String SUCCESS_MSG = "success_message";

    /**
     * 当前跳转得地址
     */
    public static final String ACTIVEURI = "activeUri";

    /**
     * 所有设置选项 (key, value)
     */
    public static Map<String, String> OPTIONS = new HashMap<>();
}
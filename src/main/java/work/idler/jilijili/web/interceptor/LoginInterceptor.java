package work.idler.jilijili.web.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static work.idler.jilijili.common.utils.ConstantUtils.USER_SESSION_KEY;

/**
 * 后台登录控制器
 *
 * @Auther:https://blog.idler.work
 * @Date:2019/5/11
 * @Description:com.iscolt.coltish.web.interceptor
 * @version:1.0
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final Object obj = request.getSession().getAttribute(USER_SESSION_KEY);
        // 未登录
        if (obj == null) {
            response.sendRedirect("/a/login");
            return false;
        } else {
            // 放行
            return true;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
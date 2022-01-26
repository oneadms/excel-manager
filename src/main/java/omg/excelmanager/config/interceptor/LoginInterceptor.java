package omg.excelmanager.config.interceptor;

import io.jsonwebtoken.Jwts;
import omg.excelmanager.common.api.ApiResult;
import omg.excelmanager.common.exception.ApiAsserts;
import omg.excelmanager.jwt.jwtUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取token
        String token = request.getHeader(jwtUtil.AUTH_NAME);
        if (null == token || token.equals("")) {
            //直接拒绝
            ApiAsserts.fail("token无效");

            return false;

        }
        Map<String, Object> map =
                jwtUtil.validateToken(token);

        request.setAttribute("user", map);

        return true;


    }
}

package omg.excelmanager.config.interceptor;

import omg.excelmanager.jwt.jwtUtil;
import omg.excelmanager.model.entity.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class PermissionInterceptor {
    //    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//
//    }
    public static class MasterCompanyPermission implements HandlerInterceptor {
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            Map<String, Object> user = (Map<String, Object>) request.getAttribute("user");
            Integer userType = (Integer) user.get(jwtUtil.USER_TYPE);
            if (userType == 1) {
                //总公司 放行
                return true;
            }
            return false;
        }
    }

    public static class BranchCompanyPermission implements HandlerInterceptor {
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            Map<String, Object> user = (Map<String, Object>) request.getAttribute("user");
            Integer userType = (Integer) user.get(jwtUtil.USER_TYPE);
            if (userType == 2 || userType == 1) {

                return true;
            }
            return false;
        }
    }

    public static class UserPermission implements HandlerInterceptor {
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            Map<String, Object> user = (Map<String, Object>) request.getAttribute("user");
            Integer userType = (Integer) user.get(jwtUtil.USER_TYPE);
            if (userType == 3 || userType == 1 || userType == 2) {

                return true;
            }
            return false;
        }
    }
}

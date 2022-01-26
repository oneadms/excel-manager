package omg.excelmanager.config;

import omg.excelmanager.config.interceptor.LoginInterceptor;
import omg.excelmanager.config.interceptor.PermissionInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class PermissionConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/admin/delExcel");
        //总公司拥有所有权限
        registry.addInterceptor(new PermissionInterceptor.MasterCompanyPermission()).addPathPatterns("*");
        //分公司只有上传权限
        registry.addInterceptor(new PermissionInterceptor.BranchCompanyPermission()).addPathPatterns("/company_excel/upload");
        //客户只能查看最终结果
        registry.addInterceptor(new PermissionInterceptor.UserPermission()).addPathPatterns("/company_excel/list");
    }
}

package omg.excelmanager.config;

import omg.excelmanager.config.interceptor.LoginInterceptor;
import omg.excelmanager.config.interceptor.PermissionInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
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

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8081")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true)
                .allowedHeaders("*");
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config=new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:8081");
        config.setAllowCredentials(true);
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",config);
        return new CorsFilter(source);
    }
}

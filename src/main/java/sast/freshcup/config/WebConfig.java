package sast.freshcup.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import sast.freshcup.interceptor.AccountInterceptor;
import sast.freshcup.interceptor.RequestInterceptor;

/**
 * @author: 風楪fy
 * @create: 2022-01-20 01:29
 **/
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AccountInterceptor accountInterceptor;

    @Autowired
    private RequestInterceptor requestInterceptor;


//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(accountInterceptor)
//                .excludePathPatterns("/**/login/**", "/**/getValidateCode/**","/**/test/**","/**/resources/**");
//
//        registry.addInterceptor(requestInterceptor)
//                .excludePathPatterns("/**/login/**", "/**/getValidateCode/**","/**/test/**","/**/resources/**");
//    }


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("login").setViewName("login");
        registry.addViewController("index").setViewName("index");
        registry.addViewController("addOrder").setViewName("addOrder");
        registry.addViewController("dishList").setViewName("dishList");
        registry.addViewController("homePage").setViewName("homePage");
    }
}

package cn.aynu.java2.weibo.config;

import cn.aynu.java2.weibo.utils.LoginInterceptor;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/admin").setViewName("/system/login");
        registry.addViewController("/admin/main").setViewName("/system/main");
        registry.addViewController("/admin/userList").setViewName("/system/mem_list");
        registry.addViewController("/admin/addAdmin").setViewName("/system/addAdmin");
        registry.addViewController("/admin/post").setViewName("/system/post_list");
        registry.addViewController("/admin/postInfo").setViewName("/system/post_info");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/register").setViewName("register");
        registry.addViewController("/test").setViewName("/system/mem_list");
        registry.addViewController("/main").setViewName("main");
        registry.addViewController("/connection").setViewName("connection");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/**/*.html",
                        "/**/*.js",              //js静态资源
                        "/**/*.css",             //css静态资源
                        "/**/*.woff",
                        "/**/*.ttf");
    }
}

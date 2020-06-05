package cn.aynu.java2.weibo.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/admin").setViewName("/system/login");
        registry.addViewController("/admin/menu").setViewName("/system/mem_list");
        registry.addViewController("/admin/adminUserAdd").setViewName("/adminUserAdd");
        registry.addViewController("/admin/adminUserCancel").setViewName("/adminUserCancel");
        registry.addViewController("/admin/adminUserList").setViewName("/adminUserList");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/register").setViewName("register");
        registry.addViewController("/test").setViewName("/system/mem_list");
        registry.addViewController("/main").setViewName("main");
        registry.addViewController("/main/profile").setViewName("profile");
    }
}

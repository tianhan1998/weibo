package cn.aynu.java2.weibo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/admin").setViewName("/system/login");
        registry.addViewController("/admin/adminUserAdd").setViewName("/adminUserAdd");
        registry.addViewController("/admin/adminUserCancel").setViewName("/adminUserCancel");
        registry.addViewController("/admin/adminUserList").setViewName("/adminUserList");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/register").setViewName("register");
        registry.addViewController("/test").setViewName("/system/mem_list");
    }
}

package cn.aynu.java2.weibo.utils;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration//当前类是一个配置类，充当的spring的配置文件
public class LoginInterceptorConfig implements WebMvcConfigurer {
    //注册拦截器
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
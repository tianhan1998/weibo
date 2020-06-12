package cn.aynu.java2.weibo.utils;


import cn.aynu.java2.weibo.entity.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url=request.getRequestURI();
        if(url.contains("/admin")){//如果是管理端页面
            System.out.println("执行admin拦截器");
            if(url.endsWith("/adminLogin")||url.endsWith("/admin")){
                return true;
            }
            User login_admin=(User) request.getSession().getAttribute("login_admin");
            if(login_admin!=null){
                return true;
            }
            else {
                response.sendRedirect(request.getContextPath()+"/admin");
                return false;
            }
        }
        else{//如果是前端页面
            if(url.endsWith("/login")||url.endsWith("/logout")){
                return true;
            }
            User login_user= (User) request.getSession().getAttribute("login_user");
            if(login_user!=null){
                return true;
            }
            else {
                response.sendRedirect(request.getContextPath()+"/login");
                return false;
            }
        }

        //System.out.println("执行拦截器");
        //User login_admin=(User) request.getSession().getAttribute("login_admin");
    }
}

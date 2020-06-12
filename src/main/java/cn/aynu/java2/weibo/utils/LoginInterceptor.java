package cn.aynu.java2.weibo.utils;


import cn.aynu.java2.weibo.entity.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url=request.getRequestURI();
        if(url.endsWith("/login")||url.endsWith("/logout")||url.endsWith("/adminLogin")||url.endsWith("/admin")){
            return true;
        }
        //System.out.println("执行拦截器");
        User login_user= (User) request.getSession().getAttribute("login_user");
        if(login_user!=null){
           /* if("0".equals(login_user.getRole())){
                return true;
            }else {
                response.sendRedirect(request.getContextPath()+"/admin/error/privilege.jsp");
                return false;
            }*/
           return true;
        }
        else {
            response.sendRedirect(request.getContextPath()+"/login");
            return false;
        }
    }
}

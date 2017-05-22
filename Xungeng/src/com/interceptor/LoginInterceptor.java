package com.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


public class LoginInterceptor implements HandlerInterceptor{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String URI = request.getRequestURI();
		
		//防止相关资源被拦截
		if(URI.indexOf("/static/")>0)
			return true;
		//前台页面
		if(URI.indexOf("login")>0||URI.indexOf("login.jsp")>0){
			return true;
		}
		
		//登陆验证请求，放行。
		if(URI.indexOf("login")>0)
			return true;

        if(request.getSession().getAttribute("admin")!=null){
            return true;
        }
        
        response.sendRedirect("/Xungeng/login");
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView arg3)
			throws Exception {
	}

	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception arg3)
			throws Exception {
	}
	
}

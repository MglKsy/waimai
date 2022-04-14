package com.liang.regiee.filter;

import com.alibaba.fastjson.JSON;
import com.liang.regiee.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String requestURI = httpServletRequest.getRequestURI();

        //定义不需要处理的路径
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**"
        };
        boolean check = check(requestURI,urls);
        if (check){
            chain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }
        if (httpServletRequest.getSession().getAttribute("employee")!=null) {
            chain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }

        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;

    }
    public boolean check(String requestURI,String[] urls){
        for (String url : urls) {
            if (PATH_MATCHER.match(url,requestURI)) {
                return true;
            }
        }
        return false;
    }
}

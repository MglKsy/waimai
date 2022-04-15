package com.liang.regiee.interceptor;

import com.alibaba.fastjson.JSON;
import com.liang.regiee.common.BaseContext;
import com.liang.regiee.common.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Long empId = (Long) request.getSession().getAttribute("employee");
        BaseContext.setCurrentId(empId);
        log.info("拦截器线程id---->{}",Thread.currentThread().getId());
        if (empId!=null){
            return true;
        }
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return false;
    }
}

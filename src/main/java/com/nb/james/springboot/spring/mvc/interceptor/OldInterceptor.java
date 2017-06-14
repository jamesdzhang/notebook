package com.nb.james.springboot.spring.mvc.interceptor;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * Created by zhangyaping on 2017/3/31.
 */
//@Component
public class OldInterceptor implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println(this.getClass().getName()+" init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println(this.getClass().getName()+" doFilter");
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {
        System.out.println(this.getClass().getName()+" destroy");
    }
}

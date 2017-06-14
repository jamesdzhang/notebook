package com.nb.james.spring.mvc.support.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nb.james.spring.mvc.support.wrapper.Base64HttpServletRequestWrapper;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

/**
 * 
 * @copyright(C)
 * @author James
 */
@Component
@WebListener("/**")
public class Base64DecodingFilter implements Filter {
    private PathMatcher matcher = new AntPathMatcher();
	private List<String> noDecodeList = new ArrayList<String>(); 
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		String noDecodeStr = filterConfig.getInitParameter("noDecode");
		if ( null != noDecodeStr) {
			StringTokenizer st = new StringTokenizer(noDecodeStr,",");
			noDecodeList.clear();
			while (st.hasMoreTokens()) {
				noDecodeList.add(st.nextToken());
			}
		}
	}
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
	 	HttpServletResponse resp = (HttpServletResponse)response;
	 	//if (noDecodeList.contains(req.getServletPath())) {
        if (ifContainsInNoDecodeList(req.getServletPath())) {
	 		chain.doFilter(req, resp);
	 	} else {
            Base64HttpServletRequestWrapper DecodingRequest = new Base64HttpServletRequestWrapper(req);
            chain.doFilter(DecodingRequest, resp);
	 	}
	}
	@Override
	public void destroy() {
		matcher = null;
		noDecodeList = null;
	}

    /**
     * 判断一个url是否在排除列表内
     * @param servletPath
     * @return
     * true 在排除列表中； false 不在排除列表中
     */
    private boolean ifContainsInNoDecodeList(String servletPath) {
        boolean ret = false;
        if (noDecodeList.contains(servletPath)) {
            ret = true;
        } else if (!noDecodeList.isEmpty()) {
            for (String filtermapping : noDecodeList) {
                if (matcher.match(filtermapping,servletPath)) {
                    ret = true;
                    break;
                }
            }
        }
        return ret;
    }

}

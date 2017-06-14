package com.nb.james.spring.mvc.support.resolver;

import javafx.scene.control.Pagination;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


/**
 * 分页对象生成
 * 
 * @copyright(C) 2006-2012 James
 * @author James
 */
public class PaginationArgumentResolver implements HandlerMethodArgumentResolver {

    public boolean supportsParameter(MethodParameter parameter) {
        return Pagination.class.isAssignableFrom(parameter.getParameterType());
    }

    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Pagination pagination = new Pagination();
        String start = webRequest.getParameter("start");
        String limit = webRequest.getParameter("limit");
        if (start != null && limit != null) {
            pagination.setMaxPageIndicatorCount(Integer.valueOf(limit));
            pagination.setCurrentPageIndex(Integer.valueOf(start) / Integer.valueOf(limit) + 1);
        }
        String count = webRequest.getParameter("count");
        if (count != null) {
            pagination.setPageCount(Integer.valueOf(count));
        }
        return pagination;
    }

}

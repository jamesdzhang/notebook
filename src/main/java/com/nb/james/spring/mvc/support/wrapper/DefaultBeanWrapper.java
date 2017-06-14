package com.nb.james.spring.mvc.support.wrapper;

import com.nb.james.utils.dto.ResponseVo;
import org.springframework.core.MethodParameter;


/**
 * 优先级最低的默认bean包装
 * 
 * @copyright(C) 2006-2012 James
 * @author James
 */
public class DefaultBeanWrapper extends AbstractBeanWrapper {

    public Object wrap(Object bean) {
        return new ResponseVo(bean);
    }

    @Override
    public boolean supports(MethodParameter returnType) {
        return !ResponseVo.class.isAssignableFrom(returnType.getParameterType());
    }

}

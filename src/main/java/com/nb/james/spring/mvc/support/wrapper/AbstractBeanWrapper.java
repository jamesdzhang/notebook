package com.nb.james.spring.mvc.support.wrapper;

import com.nb.james.utils.dto.ResponseVo;
import org.springframework.core.MethodParameter;


/**
 * 
 * @copyright(C) 2006-2012 James
 * @author James
 */
public abstract class AbstractBeanWrapper implements BeanWrapper {

    public boolean supportsType(MethodParameter returnType) {
        if (ResponseVo.class.isAssignableFrom(returnType.getParameterType())) {
            return false;
        }
        return supports(returnType);
    }

    public abstract boolean supports(MethodParameter returnType);

}

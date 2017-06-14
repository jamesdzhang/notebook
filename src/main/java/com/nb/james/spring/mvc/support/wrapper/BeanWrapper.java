package com.nb.james.spring.mvc.support.wrapper;

import org.springframework.core.MethodParameter;

/**
 * 对象包装器
 * 
 * @copyright(C) 2006-2012 James
 * @author James
 */
public interface BeanWrapper {

    /**
     * 支持性判断
     * 
     * @param returnType
     * @return
     */
    boolean supportsType(MethodParameter returnType);

    /**
     * 对象包装
     * 
     * @param bean
     * @return
     */
    Object wrap(Object bean);
}

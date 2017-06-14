package com.nb.james.spring.mvc.support.bean.translate;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.format.support.DefaultFormattingConversionService;

/**
 * 
 * @copyright(C) 2006-2012 James
 * @author James
 */
public abstract class AbstractConverFieldTranslater implements FieldTranslater, InitializingBean {

    protected ConfigurableConversionService converService;

    public ConfigurableConversionService getConverService() {
        return converService;
    }

    public void setConverService(ConfigurableConversionService converService) {
        this.converService = converService;
    }

    public void afterPropertiesSet() {
        if (this.converService == null) {
            converService = new DefaultFormattingConversionService();
        }
    }

}

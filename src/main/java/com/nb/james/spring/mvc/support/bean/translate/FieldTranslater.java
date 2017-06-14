package com.nb.james.spring.mvc.support.bean.translate;

/**
 * 
 * @copyright(C) 2006-2012 James
 * @author James
 */
public interface FieldTranslater {

    public <T> T translate(final Object targetObj, final String srcFieldName, final String additional,
                           final Class<? extends Object> additionalClass, final Class<T> type) throws TranslateException;

}

package com.nb.james.spring.mvc.support.bean.translate;

import com.nb.james.spring.mvc.support.utils.ReflectUtils;

import java.lang.reflect.Field;


/**
 * 
 * @copyright(C) 2006-2012 James
 * @author James
 */
public abstract class AbstractFieldTranslater<S> extends AbstractConverFieldTranslater {

    public <T> T translate(final Object targetObj, final String srcFieldName, final String additional,
            final Class<? extends Object> additionalClass, final Class<T> type) throws TranslateException {
        Field srcField = ReflectUtils.getFieldByFieldName(targetObj, srcFieldName);
        if (srcField == null) {
            throw new TranslateException("can't find field " + srcFieldName + " in Class "
                    + targetObj.getClass().getName());
        }
        try {
            Object srcVal = ReflectUtils.invokeGetterMethod(targetObj, srcFieldName);
            Object targetVal = translate(converService.convert(srcVal, getSourceType()), additional, additionalClass);
            return converService.convert(targetVal, type);
        } catch (Exception e) {
            throw new TranslateException(e);
        }
    }

    /**
     * 定义源数据类型
     * 
     * @return
     */
    public abstract Class<S> getSourceType();

    /**
     * 根据源数据及额外参数定义进行翻译
     * 
     * @param source
     *            源数据值
     * @param additional
     *            额外参数定义
     * @return
     * @throws TranslateException
     */
    public abstract Object translate(final S source, final String additional,
            final Class<? extends Object> additionalClass) throws TranslateException;

}

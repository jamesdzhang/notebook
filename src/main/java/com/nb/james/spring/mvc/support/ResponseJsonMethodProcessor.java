package com.nb.james.spring.mvc.support;

import com.nb.james.spring.mvc.support.annotations.ResponseJson;
import com.nb.james.spring.mvc.support.bean.translate.BeanTranslateProcessor;
import com.nb.james.spring.mvc.support.wrapper.BeanWrapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * returnValue ResponseJson 处理器
 * 
 * @copyright(C) 2006-2012 James
 * @author James
 */
public class ResponseJsonMethodProcessor implements HandlerMethodReturnValueHandler, InitializingBean {

    private HttpMessageConverter messageConverter;

    private List<BeanWrapper> beanWrappers;

    private BeanTranslateProcessor beanTranslateProcessor;

    public BeanTranslateProcessor getBeanTranslateProcessor() {
        return beanTranslateProcessor;
    }

    public void setBeanTranslateProcessor(BeanTranslateProcessor beanTranslateProcessor) {
        this.beanTranslateProcessor = beanTranslateProcessor;
    }

    public List<BeanWrapper> getBeanWrappers() {
        return beanWrappers;
    }

    public void setBeanWrappers(List<BeanWrapper> beanWrappers) {
        this.beanWrappers = beanWrappers;
    }

    public HttpMessageConverter getMessageConverter() {
        return messageConverter;
    }

    public void setMessageConverter(HttpMessageConverter messageConverter) {
        this.messageConverter = messageConverter;
    }

    public boolean supportsReturnType(MethodParameter returnType) {
        return returnType.getMethodAnnotation(ResponseJson.class) != null;
    }

    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest) throws Exception {
        Object result = returnValue;
        mavContainer.setRequestHandled(true);
        ServletServerHttpResponse outputMessage = createOutputMessage(webRequest);
        if (returnValue == null) {
            Map<String, Object> message = new HashMap<String, Object>();
            message.put("success", true);
            message.put("data", new HashMap<String, String>());
            result = message;
        } else {
            ResponseJson responseJson = returnType.getMethodAnnotation(ResponseJson.class);
            if ((responseJson.location() == ResponseJson.Location.MESSAGE)
                    || ((returnValue instanceof String) && (responseJson.location() == ResponseJson.Location.UNDEFINED))) {
                Map<String, Object> message = new HashMap<String, Object>();
                message.put("success", true);
                message.put("msg", returnValue);
                result = message;
            } else {
                if (responseJson.translate() && beanTranslateProcessor != null) {
                    beanTranslateProcessor.translate(returnValue);
                }
                for (BeanWrapper beanWrapper : beanWrappers) {
                    if (beanWrapper.supportsType(returnType)) {
                        result = beanWrapper.wrap(returnValue);
                        break;
                    }
                }
            }
        }
        messageConverter.write(result,
                new MediaType(MediaType.APPLICATION_JSON, Collections.singletonMap("charset", "UTF-8")), outputMessage);
    }

    protected ServletServerHttpResponse createOutputMessage(NativeWebRequest webRequest) {
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
        return new ServletServerHttpResponse(response);
    }

    public void afterPropertiesSet() throws Exception {
        if (beanWrappers == null || beanWrappers.size() == 0) {
            throw new Exception("beanWrappers undefined");
        }
    }
}

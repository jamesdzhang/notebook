package com.nb.james.spring.mvc.support.resolver;

import com.nb.james.utils.dto.ResponseVo;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Resolve exception during runtime
 * Created by James on 2017/4/21.
 */
@Component
public class UnifiedExceptionResolver extends SimpleMappingExceptionResolver {
    @Override
    protected ModelAndView doResolveException(HttpServletRequest httpServletRequest,
                                              HttpServletResponse httpServletResponse, Object o, Exception e) {
        if(!(e instanceof IllegalStateException)){
            ResponseVo exceptionVo = new ResponseVo();
            exceptionVo.setMsg(e.getMessage());
            write(httpServletResponse,exceptionVo);
        }
        return null;
    }


    /**
     * @param response
     * @param result <br>
     */
    protected void write(HttpServletResponse response, ResponseVo result) {
        PrintWriter out = null;
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("success", result.isSuccess());
            map.put("msg", result.getMsg());
            map.put("errorCode", result.getErrorCode());

//            String resStr = Base64.encodeBase64String(JsonUtil.toString(map).getBytes("utf-8"));
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Content-Type","application/json;charset=UTF-8");
            response.setHeader("Access-Control-Allow-Methods", "*");
            out = response.getWriter();
            out.write("");
        } catch (UnsupportedEncodingException e) {

        } catch (IOException e) {

        } finally {
            if (null != out) {
                out.flush();
                out.close();
            }
        }
    }
}

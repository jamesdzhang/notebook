package com.nb.james.utils.tools;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.json.JSONObject;
import org.springframework.boot.json.GsonJsonParser;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Created by zhangyaping on 2017/4/21.
 */
public class JsonUtil {

    private static final ThreadLocal<ObjectMapper> INCLUDE_NULL_MAPPER = new ThreadLocal();
    private static final ThreadLocal<ObjectMapper> NOT_INCLUDE_NULL_MAPPER = new ThreadLocal();

    public static <T> T toBean(String jsonStr, Class<T> clazz){
        try{
            return getMapper(false).readValue(jsonStr, clazz);
        }catch (Exception e){
            // error
            return null;
        }
    }

    public static String toJsonString(Object obj) {
        try{
            return getMapper(true).writeValueAsString(obj);
        }catch (Exception e){
            return null;
        }
    }


    private static ObjectMapper getMapper(boolean serializeNull) {
        ThreadLocal tl = serializeNull?INCLUDE_NULL_MAPPER:NOT_INCLUDE_NULL_MAPPER;
        if(null == tl.get()) {
            ObjectMapper mapper = new ObjectMapper();
//            mapper.disable(new JsonParser.Feature[]{JsonGenerator.Feature.FAIL_ON_UNKNOWN_PROPERTIES});
            mapper.disable(new JsonParser.Feature[]{JsonParser.Feature.IGNORE_UNDEFINED});
            mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
            if(!serializeNull) {
                mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            }

            tl.set(mapper);
        }

        return (ObjectMapper)tl.get();
    }

}

package cn.geng.util;

import java.util.HashMap;

/**
 * @Author: geng
 * @Date: 2021/6/29 11:06
 */
public class ResultUtil {

    public static HashMap<String, Object> success(Object object) {
        HashMap<String, Object> resutlMap = new HashMap<String, Object>();
        resutlMap.put("code", "200");
        resutlMap.put("data", object);
        return resutlMap;
    }

    public static  HashMap<String, Object> success()  {
        HashMap<String, Object> resutlMap = new HashMap<String, Object>();
        resutlMap.put("code", "200");
        return resutlMap;
    }

    public static  HashMap<String, Object> fail(String code, String message)  {
        HashMap<String, Object> resutlMap = new HashMap<String, Object>();
        resutlMap.put("code", code);
        resutlMap.put("message", message);
        return resutlMap;
    }

    public static  HashMap<String, Object> fail()  {
        HashMap<String, Object> resutlMap = new HashMap<String, Object>();
        resutlMap.put("code", "500");
        resutlMap.put("message", "系统错误");
        return resutlMap;
    }
}

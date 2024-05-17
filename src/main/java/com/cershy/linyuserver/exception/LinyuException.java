package com.cershy.linyuserver.exception;


import cn.hutool.json.JSONUtil;
import com.cershy.linyuserver.utils.ResultUtil;

import java.util.HashMap;

/**
 * @author: dwh
 **/
public class LinyuException extends RuntimeException {

    private int code;
    private String message;
    private HashMap<String, Object> param;

    public LinyuException(String message) {
        this.code = ResultUtil.ResponseEnum.FAIL.getType();
        this.message = message;
    }

    /***
     * 添加异常信息 键值对
     */
    public LinyuException param(String key, Object value) {
        if (null == this.param) {
            this.param = new HashMap<>();
        }
        param.put(key, value);
        return this;
    }

    /***
     * 置空param
     */
    public LinyuException empty() {
        this.param = new HashMap<>();
        return this;
    }

    public LinyuException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String paramToString() {
        if (null == this.param || this.param.size() <= 0)
            return null;
        return JSONUtil.toJsonStr(this.param);
    }
}

package com.cershy.linyuserver.utils;


import cn.hutool.json.JSONObject;

public class ResultUtil {

    public enum ResponseEnum {
        SUCCEED(0),
        FAIL(1),
        TOKEN_INVALID(-1), //token失效
        FORBIDDEN(-2); //没有权限


        private int type;

        ResponseEnum(int type) {
            this.type = type;
        }

        public int getType() {
            return this.type;
        }
    }

    public static String CODE = "code";
    public static String MSG = "msg";
    public static String DATA = "data";

    /**
     * 根据条件返回
     *
     * @return
     */
    public static JSONObject ResultByFlag(boolean flag) {
        if (flag) {
            return Succeed();
        } else {
            return Fail();
        }
    }

    /**
     * 根据条件返回
     *
     * @return
     */
    public static JSONObject ResultByFlag(boolean flag, String msg, Object data) {
        if (flag) {
            return Succeed();
        } else {
            return Fail();
        }
    }


    /**
     * 成功没有返回数据
     *
     * @return
     */
    public static JSONObject Succeed() {
        JSONObject result = new JSONObject();
        result.put(CODE, ResponseEnum.SUCCEED.getType());
        result.put(MSG, "操作成功");
        return result;
    }

    /**
     * 成功有返回数据
     *
     * @return
     */
    public static JSONObject Succeed(Object data) {
        JSONObject result = new JSONObject();
        result.put(CODE, ResponseEnum.SUCCEED.getType());
        result.put(DATA, data);
        return result;
    }

    /**
     * 成功有返回消息和返回数据和提示
     *
     * @return
     */
    public static JSONObject Succeed(String msg, Object data) {
        JSONObject result = new JSONObject();
        result.put(CODE, ResponseEnum.SUCCEED.getType());
        result.put(MSG, msg);
        result.put(DATA, data);
        return result;
    }

    /**
     * 失败没有返回数据
     *
     * @return
     */
    public static JSONObject Fail() {
        JSONObject result = new JSONObject();
        result.put(CODE, ResponseEnum.FAIL.getType());
        result.put(MSG, "操作失败");
        return result;
    }

    /**
     * 失败有返回数据
     *
     * @return
     */
    public static JSONObject Fail(String msg) {
        JSONObject result = new JSONObject();
        result.put(CODE, ResponseEnum.FAIL.getType());
        result.put(MSG, msg);
        return result;
    }

    /**
     * 自定义的返回
     *
     * @return
     */
    public static JSONObject Result(int code, String msg, Object data) {
        JSONObject result = new JSONObject();
        result.put(CODE, code);
        result.put(MSG, msg);
        result.put(DATA, data);
        return result;
    }

    /**
     * 自定义的返回
     *
     * @return
     */
    public static JSONObject Result(int code, String msg) {
        JSONObject result = new JSONObject();
        result.put(CODE, code);
        result.put(MSG, msg);
        return result;
    }

    /**
     * 失败有返回消息和返回数据
     *
     * @return
     */
    public static JSONObject Fail(String msg, Object data) {
        JSONObject result = new JSONObject();
        result.put(CODE, ResponseEnum.FAIL.getType());
        result.put(MSG, msg);
        result.put(DATA, data);
        return result;
    }

    /**
     * 失败有返回消息和返回数据
     *
     * @return
     */
    public static JSONObject TokenInvalid() {
        JSONObject result = new JSONObject();
        result.put(CODE, ResponseEnum.TOKEN_INVALID.getType());
        result.put(MSG, "认证失效,请重新登录~");
        return result;
    }

    /**
     * 失败有返回消息和返回数据
     *
     * @return
     */
    public static JSONObject Forbidden() {
        JSONObject result = new JSONObject();
        result.put(CODE, ResponseEnum.FORBIDDEN.getType());
        result.put(MSG, "该用户没有权限~");
        return result;
    }
}

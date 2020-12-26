package com.chuonye.syso.domain;

import com.chuonye.syso.utils.Tools;

/**
 * Json 格式响应
 * 
 * @author chuonye@foxmail.com
 */
public class JsonResponse {

    public static JsonResponse fail(String reson, Object... args) {
        JsonResponse jr = new JsonResponse();
        jr.setCode(0);
        jr.setMsg(Tools.format(reson, args));
        return jr;
    }

    public static JsonResponse ok(String msg) {
        JsonResponse jr = new JsonResponse();
        jr.setCode(1);
        jr.setMsg(msg);
        return jr;
    }

    /** 返回的状态码，0：失败，1：成功 */
    private Integer code;

    /** 返回的数据 */
    private Object  body;

    /** 返回信息 */
    private String  msg;

    public void setCode(Integer code) {
        this.code = code;
    }

    public JsonResponse setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public JsonResponse setBody(Object body) {
        this.body = body;
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public Object getBody() {
        return body;
    }

    public String getMsg() {
        return msg;
    }
}
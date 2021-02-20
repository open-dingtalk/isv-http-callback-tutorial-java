package com.dingtalk.constant;

/**
 * 钉钉开放接口网关常量
 */
public class UrlConstant {

    /**
     * 获取企业授权凭证 url
     */
    public static final String GET_CORP_ACCESS_TOKEN_URL = "https://oapi.dingtalk.com/service/get_corp_token";
    /**
     * 通过免登授权码获取用户信息 url
     */
    public static final String GET_USER_INFO_URL = "https://oapi.dingtalk.com/topapi/v2/user/getuserinfo";
    /**
     * 根据用户id获取用户详情 url
     */
    public static final String USER_GET_URL = "https://oapi.dingtalk.com/topapi/v2/user/get";
    /**
     * 获取授权信息 url
     */
    public static final String GET_AUTH_INFO = "https://oapi.dingtalk.com/service/get_auth_info";
    /**
     * 发送工作通知 url
     */
    public static final String CONVERSATION_SEND_URL = "https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2";
}

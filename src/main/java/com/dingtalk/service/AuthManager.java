package com.dingtalk.service;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiServiceGetAuthInfoRequest;
import com.dingtalk.api.response.OapiServiceGetAuthInfoResponse;
import com.dingtalk.api.response.OapiServiceGetAuthInfoResponse.AuthCorpInfo;
import com.dingtalk.api.response.OapiServiceGetAuthInfoResponse.AuthInfo;
import com.dingtalk.constant.AppConstant;
import com.dingtalk.constant.UrlConstant;
import com.taobao.api.ApiException;
import org.springframework.stereotype.Service;

/**
 * 应用授权管理
 */
@Service
public class AuthManager {

    /**
     * 获取授权企业信息
     *
     * @param authCorpId
     * @return
     * @throws ApiException
     */
    public AuthCorpInfo getCorpInfo(String authCorpId) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient(UrlConstant.GET_AUTH_INFO);
        OapiServiceGetAuthInfoRequest req = new OapiServiceGetAuthInfoRequest();
        req.setAuthCorpid(authCorpId);
        OapiServiceGetAuthInfoResponse rsp = client.execute(
            req, AppConstant.SUITE_KEY, AppConstant.SUITE_SECRET, CallbackManager.getSuiteTicket());

        return rsp.getAuthCorpInfo();
    }

    /**
     * 收取授权信息
     *
     * @param authCorpId
     * @return
     * @throws ApiException
     */
    public AuthInfo getAuthInfo(String authCorpId) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient(UrlConstant.GET_AUTH_INFO);
        OapiServiceGetAuthInfoRequest req = new OapiServiceGetAuthInfoRequest();
        req.setAuthCorpid(authCorpId);
        OapiServiceGetAuthInfoResponse rsp = client.execute(
            req, AppConstant.SUITE_KEY, AppConstant.SUITE_SECRET, CallbackManager.getSuiteTicket());

        return rsp.getAuthInfo();
    }

}

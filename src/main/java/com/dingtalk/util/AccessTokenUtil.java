package com.dingtalk.util;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiServiceGetCorpTokenRequest;
import com.dingtalk.api.response.OapiServiceGetCorpTokenResponse;
import com.dingtalk.constant.AppConstant;
import com.dingtalk.constant.UrlConstant;
import com.taobao.api.ApiException;
import org.springframework.stereotype.Component;

/**
 * 获取access_token工具类
 */
@Component
public class AccessTokenUtil {

    private static String suiteTicket = "";

    /**
     * 获取企业授权凭证
     *
     * @param authCorpId 授权企业id
     * @return
     * @throws ApiException
     */
    public static String getCorpAccessToken(String authCorpId) throws ApiException {
        DefaultDingTalkClient client = new DefaultDingTalkClient(UrlConstant.GET_CORP_ACCESS_TOKEN_URL);
        OapiServiceGetCorpTokenRequest req = new OapiServiceGetCorpTokenRequest();
        req.setAuthCorpid(authCorpId);
        OapiServiceGetCorpTokenResponse rsp = client.execute(
            req, AppConstant.SUITE_KEY, AppConstant.SUITE_SECRET, suiteTicket);

        return rsp.getAccessToken();
    }

    public static void setSuiteTicket(String suiteTicket) {
        AccessTokenUtil.suiteTicket = suiteTicket;
    }
}

package com.dingtalk.service;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.dingtalk.constant.UrlConstant;
import com.dingtalk.util.AccessTokenUtil;
import com.taobao.api.ApiException;
import org.springframework.stereotype.Service;

/**
 * 工作通知管理
 */
@Service
public class ConversationManager {

    /**
     * 发送工作通知消息
     *
     * @param authCorpId
     * @param agentId
     * @param userId
     * @throws ApiException
     */
    public void send(String authCorpId, Long agentId, String userId) throws ApiException {
        // 1. 获取access_token
        String accessToken = AccessTokenUtil.getCorpAccessToken(authCorpId);

        // 2. 发送消息
        DingTalkClient client = new DefaultDingTalkClient(UrlConstant.CONVERSATION_SEND_URL);
        OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
        request.setAgentId(agentId);
        request.setUseridList(userId);

        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setMsgtype("markdown");
        msg.setMarkdown(new OapiMessageCorpconversationAsyncsendV2Request.Markdown());
        msg.getMarkdown().setText("##### 测试消息");
        msg.getMarkdown().setTitle("### 标题");
        request.setMsg(msg);

        OapiMessageCorpconversationAsyncsendV2Response rsp = client.execute(request, accessToken);
    }
}

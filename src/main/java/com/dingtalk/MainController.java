package com.dingtalk;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;

import com.dingtalk.api.response.OapiServiceGetAuthInfoResponse.AuthInfo;
import com.dingtalk.constant.AppConstant;
import com.dingtalk.model.RpcServiceResult;
import com.dingtalk.oapi.lib.aes.DingTalkEncryptor;
import com.dingtalk.service.AuthManager;
import com.dingtalk.service.CallbackManager;
import com.dingtalk.service.ConversationManager;
import com.dingtalk.service.UserManager;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 钉钉第三方应用DEMO, 实现了HTTP回调、身份验证（免登）、发送工作通知消息 功能
 */
@RestController
public class MainController {

    @Resource
    private CallbackManager callbackManager;
    @Resource
    private UserManager userManager;
    @Resource
    private AuthManager authManager;
    @Resource
    private ConversationManager conversationManager;

    /**
     * 欢迎页面, 检查后端服务是否启动
     *
     * @return
     */
    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String welcome() {
        return "welcome";
    }

    @RequestMapping(value = "/callback", method = RequestMethod.POST)
    public Map<String, String> callback(
        @RequestParam(value = "signature") String signature,
        @RequestParam(value = "timestamp") Long timestamp,
        @RequestParam(value = "nonce") String nonce,
        @RequestBody(required = false) JSONObject body
    ) {
        try {
            // 1. 解密消息体
            String encryptMsg = body.getString("encrypt");
            DingTalkEncryptor dingTalkEncryptor = new DingTalkEncryptor(
                AppConstant.TOKEN, AppConstant.ENCODING_AES_KEY, AppConstant.SUITE_KEY);
            String decryptMsg = dingTalkEncryptor.getDecryptMsg(signature, timestamp.toString(), nonce, encryptMsg);

            // 2. 处理回调消息
            callbackManager.process(decryptMsg);

            // 3. 返回成功
            return dingTalkEncryptor.getEncryptedMap("success", timestamp, nonce);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 获取登录用户身份
     *
     * @param authCorpId 授权企业id
     * @param authCode   免登授权码
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public RpcServiceResult login(
        @RequestParam(value = "authCorpId") String authCorpId,
        @RequestParam(value = "authCode") String authCode) {
        try {
            // 1. 获取用户id
            String userId = userManager.getUserId(authCorpId, authCode);

            // 2. 获取用户名称
            String userName = userManager.getUserName(authCorpId, userId);

            // 3. 获取授权企业名称
            String authCorpName = authManager.getCorpInfo(authCorpId).getCorpName();

            // 3. 返回用户身份
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("corpId", authCorpId);
            resultMap.put("corpName", authCorpName);
            resultMap.put("userId", userId);
            resultMap.put("userName", userName);

            return RpcServiceResult.getSuccessResult(resultMap);
        } catch (Exception ex) {
            return RpcServiceResult.getFailureResult("-1", "login exception");
        }
    }

    /**
     * 发送工作通知消息
     *
     * @param userId
     * @param authCorpId
     * @return
     */
    @RequestMapping(value = "/sendMsg", method = RequestMethod.POST)
    public RpcServiceResult sendMsg(
        @RequestParam(value = "userId") String userId,
        @RequestParam(value = "authCorpId") String authCorpId) {
        try {
            // 1. 获取授权信息
            AuthInfo authInfo = authManager.getAuthInfo(authCorpId);

            // 2. 授权方应用id
            Long agentId = authInfo.getAgent().get(0).getAgentid();

            // 3. 发送工作通知消息
            conversationManager.send(authCorpId, agentId, userId);

            return RpcServiceResult.getSuccessResult(null);
        } catch (Exception ex) {
            return RpcServiceResult.getFailureResult("-1", "sendMsg exception");
        }
    }
}

package com.dingtalk.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.dingtalk.util.AccessTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static com.dingtalk.constant.CallbackEventConstant.*;

/**
 * 回调管理
 */
@Service
public class CallbackManager {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 处理回调消息
     * @param msg
     */
    public void process(String msg) {
        JSONObject content = JSON.parseObject(msg);

        switch (content.getString("EventType")) {
            case CHECK_CREATE_SUITE_URL_EVENT:
                logger.info("验证回调URL有效性: " + msg);
                break;
            case CHECK_UPDATE_SUITE_URL_EVENT:
                logger.info("验证更新回调URL有效性: " +msg);
                break;
            case SUITE_TICKET_EVENT:
                logger.info("suite_ticket推送: " + msg);
                /**
                 * 钉钉会定期向本url推送suite_ticket, 用于获取access_token
                 * 本demo中将suite_ticket临时存在本地缓存, 实际开发中, 应将suite_ticket持久化到db
                 */
                AccessTokenUtil.setSuiteTicket(content.getString("SuiteTicket"));
                break;
            case TMP_AUTH_CODE_EVENT:
                logger.info("企业授权开通应用: " + msg);
                break;
            default:
                // todo 处理其他类型事件
                logger.info("其他事件: " + msg);
        }
    }
}

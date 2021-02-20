package com.dingtalk.constant;

/**
 * 应用凭证常量
 */
public class AppConstant {

    /**
     * 开发者后台->应用开发-第三方企业应用->选择您的小程序->凭证与基础信息->SuiteKey
     */
    public static final String SUITE_KEY="***";

    /**
     * 开发者后台->应用开发-第三方企业应用->选择您的小程序->凭证与基础信息->SuiteSecret
     */
    public static final String SUITE_SECRET="***";

    /**
     * 必须为英文或数字，长度为3~32个字符。用于生成签名、校验回调请求的合法性。
     */
    public static final String TOKEN = "***";

    /**
     * 回调消息加解密参数，是AES密钥的Base64编码，用于解密回调消息内容对应的密文。
     */
    public static final String ENCODING_AES_KEY = "***";
}

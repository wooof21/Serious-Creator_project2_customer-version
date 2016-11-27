/**
 * 
 */
package alipay;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import android.util.Log;

/**
 * @author Liming Chu
 * 
 * @param
 * @return
 */
public final class Keys {

    // 商户PID
    public static final String PARTNER = "2088021264341456";
    // 商户收款账号
    public static final String SELLER = "2970345565@qq.com";
    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAMFGVFC9trHCJTe7uK469/Z5AsR0f3aC/yWgSTi88VXC9OOXbzpNa+yFYwiKs55QtwQUDNqXbZKCk7M70EZptet9BQFTBI69/6zyjaU9NO2QNpHBQ/cqB3CLeHkQj937QQrlIcneJX1BRdtkp+10F6IhpHsHpwKTH2tQkJ+2NI6xAgMBAAECgYBRGZ3Ps4ux03GijVbVZVEfT2/l16yUoitGMpgHAuGRxxjTV3wIzuv2d5Iyx2DPkbb6Mx+hqeEElV4822Bt63W1+7bmesMEErDk52b6Wt10l89zsMYS542dUgBVjjZWzoDpgC139JPnWdiFYhpDmBvdWCK4LbJ/rNEDBJh8lmtyMQJBAO9NuNkUHsB8xDYvLNE0QYg9wN3qASwRaZR3xt4J9K0ytLp1Fo65NH3avm/JWjGrEaZQS/idRfXaxuHatpby7E8CQQDOwniIco1al+CA+mZh4EgKKyB/UW/QdBBtLO/f0nW3I79ZanSo3bT0uHXQt1l72PuvbAMdrypII7COelJJqxT/AkEA3fO3e9f2gc/26M6ugcc29NdZ+DmfJrFrvKGvWKfhQXcjO0F4T54luolVUjduKV0ifKBDJm8wwUxOZJLIAY+EbwJBAIBJK25F3SzDpVmBg/rgpPIiNiSpC76D9Rk4U5FFTSKXvIcnRUDtZvScawVYbd4pEGiUlQ5a+9oMwuR0QxBQZVMCQQCDZbrIyx81ZO8PLmWtmpBdShr5TKe27+G8iXllQfQpmWZ08RRYXaSgzmKxH//BuE+GF/L/BvlUuizthWEvC+AW";
    // 支付宝公钥
    public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

    /**
     * get the sign type we use. 获取签名方式
     * 
     */
    public static String getSignType() {
	return "sign_type=\"RSA\"";
    }
    
    /**
     * sign the order info. 对订单信息进行签名
     * 
     * @param content
     *            待签名订单信息
     */
    public static String sign(String content) {
	return SignUtils.sign(content, Keys.RSA_PRIVATE);
    }
}

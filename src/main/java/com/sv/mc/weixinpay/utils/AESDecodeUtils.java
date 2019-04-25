package com.sv.mc.weixinpay.utils;


import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.spec.AlgorithmParameterSpec;

/**
 * @author: lzq
 * @date: 2018年7月3日
 * 小程序AES解密
 */
public class AESDecodeUtils {

//    public static void main(String[] args) throws Exception {
//        byte[] encrypData = Base64.decodeBase64("mgxuts7uEbdnBclp0Qim0TS3MGoaSyE09MqTvbeG9Z1PjxsDwjVH0FxG1Q==");
//        byte[] ivData = Base64.decodeBase64("SG386etdA3sOXHxqfnw==");
//        byte[] sessionKey = Base64.decodeBase64("Fn6r4IOiZJBXn4hQ0w==");
//        System.out.println(decrypt(sessionKey,ivData,encrypData));
//    }

    public String decrypt(byte[] key, byte[] iv, byte[] encData) throws Exception {
        AlgorithmParameterSpec ivSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        //解析解密后的字符串
        return new String(cipher.doFinal(encData), StandardCharsets.UTF_8);
    }

}

package com.cershy.linyuserver.utils;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class SignatureUtils {
    public static String calculateSignature(String method, String path, String accessKey,
                                            String timestamp, String secretKey) throws Exception {
        String stringToSign = method + path + accessKey + timestamp;
        // 使用HMAC-SHA256算法计算签名
        Mac hmacSHA256 = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        hmacSHA256.init(secretKeySpec);
        byte[] hash = hmacSHA256.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8));
        return Hex.encodeHexString(hash);
    }
}

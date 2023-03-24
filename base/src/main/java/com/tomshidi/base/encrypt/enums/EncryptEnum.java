package com.tomshidi.base.encrypt.enums;

import com.tomshidi.base.encrypt.config.EncryptConfig;
import com.tomshidi.base.encrypt.util.SM4EncryptionUtil;
import com.tomshidi.base.exceptions.BaseException;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.util.ObjectUtils;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author tomshidi
 * @since 2023/3/22 17:49
 */
public enum EncryptEnum {
    /**
     * 加密类型
     */
    SM4("sm4", "国密") {
        @Override
        public String encrypt(String plainText, String key, String iv) {
            if (ObjectUtils.isEmpty(plainText)
                    || ObjectUtils.isEmpty(key) ||
                    ObjectUtils.isEmpty(iv)) {
                throw new BaseException("加密传参不能为空");
            }
            byte[] plainTextBytes = plainText.getBytes(StandardCharsets.UTF_8);
            byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
            byte[] ivBytes = iv.getBytes(StandardCharsets.UTF_8);
            byte[] encrypt = SM4EncryptionUtil.encrypt(keyBytes, ivBytes, plainTextBytes);
            byte[] base64Bytes = Base64.encode(encrypt);
            return new String(base64Bytes, StandardCharsets.UTF_8);
        }

        @Override
        public String decrypt(String cipherText, String key, String iv) {
            if (ObjectUtils.isEmpty(cipherText)
                    || ObjectUtils.isEmpty(key) ||
                    ObjectUtils.isEmpty(iv)) {
                throw new BaseException("加密传参不能为空");
            }
            byte[] cipherTextBytes = Base64.decode(cipherText);
            byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
            byte[] ivBytes = iv.getBytes(StandardCharsets.UTF_8);
            byte[] decrypt = SM4EncryptionUtil.decrypt(keyBytes, ivBytes, cipherTextBytes);
            return new String(decrypt, StandardCharsets.UTF_8);
        }
    },
    ;

    private String code;

    private String desc;

    EncryptEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String encrypt(String plainText, EncryptConfig encryptConfig) {
        Map<String, String> algorithmProperty = encryptConfig.getAlgorithmProperty(this.code);
        return encrypt(plainText, algorithmProperty.get("key"), algorithmProperty.get("iv"));
    }

    public String decrypt(String cipherText, EncryptConfig encryptConfig) {
        Map<String, String> algorithmProperty = encryptConfig.getAlgorithmProperty(this.code);
        return decrypt(cipherText, algorithmProperty.get("key"), algorithmProperty.get("iv"));
    }

    /**
     * 加密方法
     * @param plainText 明文串
     * @param key
     * @param iv
     * @return
     */
    abstract String encrypt(String plainText, String key, String iv);

    abstract String decrypt(String cipherText, String key, String iv);
}
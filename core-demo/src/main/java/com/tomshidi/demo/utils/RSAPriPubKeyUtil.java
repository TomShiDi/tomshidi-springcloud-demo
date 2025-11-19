package com.tomshidi.demo.utils;

import com.tomshidi.demo.dto.RSAEncryptKeyPair;
import org.apache.commons.io.IOUtils;
import org.bouncycastle.util.encoders.Base64;

import javax.crypto.Cipher;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author tomshidi
 * @since 2024/4/10 16:06
 */
public class RSAPriPubKeyUtil {

    /**
     * 获取秘钥对
     *
     * @return 秘钥对
     * @throws Exception 异常
     */
    public static RSAEncryptKeyPair getPairKey() throws Exception {
        KeyFactory kf = KeyFactory.getInstance("RSA");
        InputStream priKeyInputStream = RSAPriPubKeyUtil.class.getClassLoader()
                .getResourceAsStream("key" + File.separator + "priKey.key");
        InputStream pubKeyInputStream = RSAPriPubKeyUtil.class.getClassLoader()
                .getResourceAsStream("key" + File.separator + "pubKey.key");
        if (priKeyInputStream == null || pubKeyInputStream == null) {
            throw new IOException("秘钥文件不全");
        }
        //从文件中获取公钥以及私钥
        return new RSAEncryptKeyPair(
                (RSAPrivateKey) kf.generatePrivate(new PKCS8EncodedKeySpec(IOUtils.toByteArray(priKeyInputStream))),
                (RSAPublicKey) kf.generatePublic(new X509EncodedKeySpec(IOUtils.toByteArray(pubKeyInputStream))));
    }

    public static String decryptByPrivateKey(byte[] data, RSAPrivateKey privateKey) throws Exception {
        //数据解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(data));
    }

    public static String encryptByPublicKey(String data, RSAPublicKey publicKey) throws Exception {
        Cipher ci = Cipher.getInstance("RSA");
        ci.init(Cipher.ENCRYPT_MODE, publicKey);
        return Base64.toBase64String(ci.doFinal(data.getBytes(StandardCharsets.UTF_8)));
    }

    public static void main(String[] args) throws Exception {
        RSAEncryptKeyPair pairKey = getPairKey();
//        System.out.println(Base64.toBase64String(pairKey.getPrivateKey().getEncoded()));
        String testStr = "hT6PZgiwABm2ki9SdVX7DhYiASCL6i1ejQ/+AP6NX8QUguJXPEoRmRpiGLmDoaSHq0MnSw9k/4uZWKnSjD3xgZUgVoEQRbXq2dQ58maq5Rlsqtx6K1B4YRdcMR4hgvojqd/nSZTCTZmSxvPUe9Gu52lDNy92EgjonzGzMikXw4U=";
        testStr = "et2Ei8BgFtGwoBkWCJ9U1eAnSnFbi0h2oMvZnPPGZlpXRbOls3PTEt/Mf7zeYXTXxAUEphlJNGRKHGEFWYdz1PUmZ27VbfTLqBYeVw205OGfU3aouLKXD5eVXZqvDEvsXw6EwCGu0h+mZPV2ADQBaJ3yAs1dObu66sZiqDl67po=";

        String decodeStr = decryptByPrivateKey(Base64.decode(testStr), pairKey.getPrivateKey());
        System.out.println(decodeStr);

        String encodeStr = encryptByPublicKey("JQBMm@2022", pairKey.getPublicKey());
        System.out.println(encodeStr);
    }
}

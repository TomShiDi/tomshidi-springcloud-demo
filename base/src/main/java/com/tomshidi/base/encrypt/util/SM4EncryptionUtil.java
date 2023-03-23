package com.tomshidi.base.encrypt.util;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.engines.SM4Engine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.nio.charset.StandardCharsets;
import java.security.Security;
import java.util.Base64;

public class SM4EncryptionUtil {

    public static void main(String[] args) {
        Security.addProvider(new BouncyCastleProvider());

        String plainText = "Hello World";
        byte[] key = "0123456789abcdef".getBytes(StandardCharsets.UTF_8);
        byte[] iv = "0123456789abcdef".getBytes(StandardCharsets.UTF_8);

        byte[] cipherText = encrypt(key, iv, plainText.getBytes(StandardCharsets.UTF_8));
        String base64CipherText = Base64.getEncoder().encodeToString(cipherText);
        System.out.println("Cipher Text: " + base64CipherText);

        byte[] decryptedText = decrypt(key, iv, Base64.getDecoder().decode(base64CipherText));
        System.out.println("Decrypted Text: " + new String(decryptedText, StandardCharsets.UTF_8));
    }

    /**
     * 加密
     * @param key 秘钥
     * @param iv 长度为16的随机字符串
     * @param plainText 待加密串
     * @return byte[]
     */
    public static byte[] encrypt(byte[] key, byte[] iv, byte[] plainText) {
        BlockCipher engine = new SM4Engine();
        PaddedBufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(engine));
        cipher.init(true, new ParametersWithIV(new KeyParameter(key), iv));

        byte[] output = new byte[cipher.getOutputSize(plainText.length)];
        int bytesProcessed = cipher.processBytes(plainText, 0, plainText.length, output, 0);
        try {
            cipher.doFinal(output, bytesProcessed);
        } catch (Exception e) {
            throw new RuntimeException("Unable to perform encryption", e);
        }
        return output;
    }

    /**
     * 解密
     * @param key 秘钥
     * @param iv 长度为16的随机字符串
     * @param cipherText 密文串
     * @return byte[]
     */
    public static byte[] decrypt(byte[] key, byte[] iv, byte[] cipherText) {
        BlockCipher engine = new SM4Engine();
        PaddedBufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(engine));
        cipher.init(false, new ParametersWithIV(new KeyParameter(key), iv));

        byte[] output = new byte[cipher.getOutputSize(cipherText.length)];
        int bytesProcessed = cipher.processBytes(cipherText, 0, cipherText.length, output, 0);
        try {
            cipher.doFinal(output, bytesProcessed);
        } catch (Exception e) {
            throw new RuntimeException("Unable to perform decryption", e);
        }
        byte[] result = new byte[0];
        for (int i = output.length - 1; i >= 0; i--) {
            if (output[i] != 0) {
                result = new byte[i + 1];
                System.arraycopy(output, 0, result, 0, i + 1);
                break;
            }
        }
        return result;
    }
}


package org.winterframework.crypto.utils;

import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * @author sven
 * Created on 2023/3/11 1:42 PM
 */
public class AesUtils {
    private AesUtils() {}

    public static String aesDecrypt(String encryptStr, String algorithm, String secretKey) throws Exception {
        byte[] decodeBytes = Base64.getDecoder().decode(encryptStr);
        KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);

        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(secretKey.getBytes());
        keyGenerator.init(128, random);
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(keyGenerator.generateKey().getEncoded(), algorithm));
        byte[] decryptBytes = cipher.doFinal(decodeBytes);
        return new String(decryptBytes);
    }

    public static String aesEncrypt(String originStr, String algorithm, String secretKey) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(secretKey.getBytes());
        keyGenerator.init(128, random);
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(keyGenerator.generateKey().getEncoded(), algorithm));
        byte[] encodeBytes = cipher.doFinal(originStr.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encodeBytes);
    }
}

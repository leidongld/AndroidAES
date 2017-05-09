package com.example.leidong.androidaes;

import android.util.Base64;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by leidong on 2017/5/9.
 */

public class AESUtils {
    /**
     * 密钥算法
     */
    public static final String KEY_ALGORITHM = "AES";
    /**
     * 加密/解密算法  /工作模式  /填充方式
     */
    public static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    /**
     * 转换密钥
     * @param key 待密钥
     * @return 转换后的密钥
     * @throws Exception 抛出异常
     */
    private static Key toKey(byte[] key) throws Exception{
        //实例化AES密钥材料
        SecretKey secretKey = new SecretKeySpec(key, KEY_ALGORITHM);
        return secretKey;
    }

    /**
     * AES解密
     * @param data 待解密数据
     * @param key 密钥
     * @return 明文
     * @throws Exception 抛出异常
     */
    private static byte[] decrypy(byte[] data, byte[] key) throws Exception{
        //还原密钥
        Key k = toKey(key);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        //初始化，设置解密模式
        cipher.init(Cipher.DECRYPT_MODE, k);
        return cipher.doFinal(data);
    }

    /**
     * AES解密
     * @param data 密文
     * @param aesKey AES密钥
     * @return 明文
     * @throws Exception 抛出异常
     */
    public static String decrypt(String data, String aesKey) throws Exception{
        byte[] data_byets = Base64.decode(data, Base64.DEFAULT);
        byte[] aesKey_bytes = Base64.decode(aesKey, Base64.DEFAULT);
        byte[] result = decrypy(data_byets, aesKey_bytes);
        return new String(result);
    }

    /**
     * AES加密
     * @param data 待加密数据
     * @param key 密钥
     * @return 密文
     * @throws Exception 抛出异常
     */
    private static byte[] encrypy(byte[] data, byte[] key) throws Exception{
        Key k = toKey(key);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        //初始化，设置解密模式
        cipher.init(Cipher.ENCRYPT_MODE, k);
        return cipher.doFinal(data);
    }

    /**
     * AES加密
     * @param data 待加密数据
     * @param aesKey AES密钥
     * @return 密文
     * @throws Exception 抛出异常
     */
    public static String encrypt(String data, String aesKey) throws Exception{
        byte[] data_bytes = data.getBytes();
        byte[] aesKey_bytes = Base64.decode(aesKey, Base64.DEFAULT);
        byte[] result = encrypy(data_bytes, aesKey_bytes);
        return Base64.encodeToString(result, Base64.DEFAULT);
    }

    /**
     * 生成AES密钥
     * @return AES密钥
     * @throws Exception 抛出异常
     */
    public static String initKey() throws Exception{
        //实例化
        KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM);
        //设置密钥长度
        keyGenerator.init(256);
        //生成密钥
        SecretKey secretKey = keyGenerator.generateKey();
        //获得密钥的二进制编码形式
        byte[] aesKey_bytes = secretKey.getEncoded();
        String aesKey = Base64.encodeToString(aesKey_bytes, Base64.DEFAULT);
        return aesKey;
    }
}

package com.antplatform.admin.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author: maoyan
 * @date: 2020/7/10 11:02:04
 * @description:
 */
@Slf4j
public class AesUtil {
    /**
     * 安全密码(UUID生成)，作为盐值用于用户密码的加密
     */
    private static final String SECURITY_KEY = "929123f8f17944e8b0a531045453e1f1";
    private static final String KEY_ALGORITHM = "AES";
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    /**
     * AES 加密
     * @param password
     *         未加密的密码
     * @param salt
     *         盐值，默认使用用户名就可
     * @return
     * @throws Exception
     */
    public static String encrypt(String password, String salt) throws Exception {
        // 加密的密钥
        String secretKey = MD5(salt,SECURITY_KEY);

        return encryptPwd(secretKey, password);
    }

    /**
     * AES 解密
     * @param encryptPassword
     *         加密后的密码
     * @param salt
     *         盐值，默认使用用户名就可
     * @return
     * @throws Exception
     */
    public static String decrypt(String encryptPassword, String salt) throws Exception {
        // 加密的密钥
        String secretKey = MD5(salt,SECURITY_KEY);

        return decryptPwd(secretKey, encryptPassword);
    }

    /**
     * 通过盐值对字符串进行MD5加密
     *
     * @param param
     *         需要加密的字符串
     * @param salt
     *         盐值
     * @return
     */
    private static String MD5(String param, String salt) {
        return MD5(param + salt);
    }

    /**
     * 加密字符串
     *
     * @param s
     *         字符串
     * @return
     */
    private static String MD5(String s) {
//        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
//        try {
//            byte[] btInput = s.getBytes();
//            MessageDigest mdInst = MessageDigest.getInstance("MD5");
//            mdInst.update(btInput);
//            byte[] md = mdInst.digest();
//            int j = md.length;
//            char[] str = new char[j * 2];
//            int k = 0;
//            for (byte byte0 : md) {
//                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
//                str[k++] = hexDigits[byte0 & 0xf];
//            }
//            return new String(str);
//        } catch (Exception e) {
//            log.error("MD5生成失败", e);
//            return null;
//        }

        try {
            final int length = 32;
            final char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
            //获取MD5实例
            MessageDigest md = MessageDigest.getInstance("MD5");
            //此处传入要加密的byte类型值
            md.update(s.getBytes());
            //此处得到的是md5加密后的byte类型值
            byte[] digest = md.digest();

            char[] chars = new char[length];
            for (int i = 0; i < chars.length; i = i + 2) {
                byte b = digest[i / 2];
                chars[i] = hexDigits[(b >>> 0x4) & 0xf];
                chars[i + 1] = hexDigits[b & 0xf];
            }
            return new String(chars);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            log.error("MD5生成失败", e);
            return null;
        }
    }

    /**
     * AES加密
     *
     * @param secretKey
     *         加密的密钥
     * @param content
     *         需要加密的字符串
     * @return 返回Base64转码后的加密数据
     * @throws Exception
     */
    private static String encryptPwd(String secretKey, String content) throws Exception {
        // 创建密码器
        Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);

        byte[] byteContent = content.getBytes("utf-8");

        // 初始化为加密模式的密码器
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(secretKey));

        // 加密
        byte[] result = cipher.doFinal(byteContent);

        //通过Base64转码返回
        return Base64.encodeBase64String(result);
    }

    /**
     * AES解密
     *
     * @param secretKey
     *         加密的密钥
     * @param encrypted
     *         已加密的密文
     * @return 返回解密后的数据
     * @throws Exception
     */
    private static String decryptPwd(String secretKey, String encrypted) throws Exception {
        //实例化
        Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);

        //使用密钥初始化，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, getSecretKey(secretKey));

        //执行操作
        byte[] result = cipher.doFinal(Base64.decodeBase64(encrypted));

        return new String(result, "utf-8");
    }

    /**
     * 生成加密秘钥
     *
     * @return
     */
    private static SecretKeySpec getSecretKey(final String key) throws NoSuchAlgorithmException {
        //返回生成指定算法密钥生成器的 KeyGenerator 对象
        KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
        // javax.crypto.BadPaddingException: Given final block not properly padded解决方案
        // https://www.cnblogs.com/zempty/p/4318902.html - 用此法解决的
        // https://www.cnblogs.com/digdeep/p/5580244.html - 留作参考吧
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(key.getBytes());
        //AES 要求密钥长度为 128
        kg.init(128, random);

        //生成一个密钥
        SecretKey secretKey = kg.generateKey();
        // 转换为AES专用密钥
        return new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);
    }

    public static void main(String[] args) {
        try {
            System.out.println(encrypt("1234561","admin"));
            System.out.println(encrypt("123456","root"));

            System.out.println(decrypt("y7KOBXU8Aie/0dcP0CBWwA==","admin"));
            System.out.println(decrypt("3x+lWa4XAr4WmmNySexRrw==","root"));


            System.out.println(AesUtil.MD5("123456admin"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

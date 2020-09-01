package com.xiaozu.core.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Author:80906
 * @Des:
 * @Date:2019/3/13
 */
public class SecretUtil {

    public static String Sha256(String str) {
        return encode(str, "SHA-256");
    }

    public static String MD5(String str) {
        return encode(str, "MD5");
    }

    private static String encode(String str, String digestStr) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(digestStr);
            messageDigest.update(str.getBytes("utf-8"));
            return byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 　　* 将byte转为16进制
     * 　　* @param bytes
     * 　　* @return
     */
    private static String byte2Hex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
//1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

}

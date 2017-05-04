package com.gmy.bus.charet;

import java.io.UnsupportedEncodingException;

/**
 * Created by Gmy on 2017/5/4.
 */
public class CharsetUtil {


    /**
     * 获取输入字符串的字符集
     *
     * @param str 输入字符串
     * @throws UnsupportedEncodingException 不支持的字符集
     */
    public void getStringCharset(String str) throws UnsupportedEncodingException {
        System.out.println("1:" + new String(str.getBytes("GBK"), "ISO8859_1"));
        System.out.println("2:" + new String(str.getBytes("GBK"), "utf-8"));
        System.out.println("3:" + new String(str.getBytes("GBK"), "GB2312"));
        System.out.println("4:" + new String(str.getBytes("GBK"), "GBK"));
        System.out.println("5:" + new String(str.getBytes("ISO8859_1"), "GBK"));
        System.out.println("6:" + new String(str.getBytes("ISO8859_1"), "ISO8859_1"));
        System.out.println("7:" + new String(str.getBytes("ISO8859_1"), "GB2312"));
        System.out.println("8:" + new String(str.getBytes("ISO8859_1"), "utf-8"));
        System.out.println("9:" + new String(str.getBytes("utf-8"), "GBK"));
        System.out.println("10:" + new String(str.getBytes("utf-8"), "utf-8"));
        System.out.println("11:" + new String(str.getBytes("utf-8"), "GB2312"));
        System.out.println("12:" + new String(str.getBytes("utf-8"), "ISO8859_1"));
        System.out.println("13:" + new String(str.getBytes("GB2312"), "GB2312"));
        System.out.println("14:" + new String(str.getBytes("GB2312"), "ISO8859_1"));
        System.out.println("15:" + new String(str.getBytes("GB2312"), "utf-8"));
        System.out.println("16:" + new String(str.getBytes("GB2312"), "GBK"));
    }

    /**
     * 获取输入数组的字符集
     * @param bytes 输入字符串
     * @throws UnsupportedEncodingException 不支持的字符集
     */
    public void getBytesCharset(byte[] bytes) throws UnsupportedEncodingException {
        System.out.println("1:" + new String(bytes, "ISO8859_1"));
        System.out.println("2:" + new String(bytes, "utf-8"));
        System.out.println("3:" + new String(bytes, "GB2312"));
        System.out.println("4:" + new String(bytes, "GBK"));
        System.out.println("5:" + new String(bytes, "GBK"));
        System.out.println("6:" + new String(bytes, "ISO8859_1"));
        System.out.println("7:" + new String(bytes, "GB2312"));
        System.out.println("8:" + new String(bytes, "utf-8"));
        System.out.println("9:" + new String(bytes, "GBK"));
        System.out.println("10:" + new String(bytes, "utf-8"));
        System.out.println("11:" + new String(bytes, "GB2312"));
        System.out.println("12:" + new String(bytes, "ISO8859_1"));
        System.out.println("13:" + new String(bytes, "GB2312"));
        System.out.println("14:" + new String(bytes, "ISO8859_1"));
        System.out.println("15:" + new String(bytes, "utf-8"));
        System.out.println("16:" + new String(bytes, "GBK"));
    }

}

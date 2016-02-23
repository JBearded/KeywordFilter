package com.keyword.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 汉语拼音的处理
 *
 * @author 谢俊权
 * @create 2016/2/23 14:55
 */
public class PinYinUtil {

    private static final String UNICODE_TO_HANYU_PINYIN_TXT = "unicode_to_hanyu_pinyin.txt";

    private static final Properties hanyuPinyinMap = new Properties();
    static {
        String path = PinYinUtil.class.getClassLoader().getResource(UNICODE_TO_HANYU_PINYIN_TXT).getPath();
        loadUnicodeToHanyuPinyinTxt(path);
    }

    public static String getPinyin(String hanyu){

        StringBuilder pinyinBuilder = new StringBuilder();
        if(!EmptyUtil.isEmpty(hanyu)){
            int length = hanyu.length();
            for(int i = 0; i < length; i++){
                int codePoint = hanyu.codePointAt(i);
                String code = Integer.toHexString(codePoint).toUpperCase();
                String pinyinProperty = hanyuPinyinMap.getProperty(code);
                if(!EmptyUtil.isEmpty(pinyinProperty)) {
                    String pinyin = getPinyinFromProperty(pinyinProperty);
                    pinyinBuilder.append(pinyin).append(" ");
                }
            }
        }
        return pinyinBuilder.toString();
    }

    private static String getPinyinFromProperty(String pinyins){

        int begin = pinyins.lastIndexOf(Field.LEFT_BRACKET) + 1;
        int end =  pinyins.indexOf(Field.RIGHT_BRACKET);
        String temp = pinyins.substring(begin, end);
        String[] pinyinArray = temp.split(Field.COMMA);
        List<String> pinyinList = getListFromPinyinArray(pinyinArray);
        if(EmptyUtil.isEmpty(pinyinList))
            return null;
        return pinyinList.get(0);
    }

    private static List<String> getListFromPinyinArray(String[] array){
        List<String> list = new ArrayList<String>();
        int length = array.length;
        for(int i = 0; i < length; i++){
            String pinyinWithNumber = array[i];
            String pinyin = pinyinWithNumber.replaceAll("[1-5]", "");
            if(!list.contains(pinyin)){
                list.add(pinyin);
            }
        }
        return list;
    }

    public static void loadUnicodeToHanyuPinyinTxt(String unicodeToHanyuPinyinTxt){
        InputStream is = null;
        try {
            if(!hanyuPinyinMap.isEmpty()){
                hanyuPinyinMap.clear();
            }
            is = new FileInputStream(unicodeToHanyuPinyinTxt);
            hanyuPinyinMap.load(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private class Field{
        static final String LEFT_BRACKET = "(";
        static final String RIGHT_BRACKET = ")";
        static final String COMMA = ",";
    }

}
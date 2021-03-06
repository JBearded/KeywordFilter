package com.keyword.rule;

import com.keyword.pinyin.DefaultPinyinHandler;
import com.keyword.pinyin.PinyinGenerator;

import java.util.HashSet;
import java.util.Set;

/**
 * 拼音规则, 取给出文字的拼音作为关键字, 同时包含默认规则
 *
 * @author 谢俊权
 * @create 2016/2/25 10:34
 */
public class PinyinRule implements KeywordRule{

    public Set<String> getKeywords(String word) {
        PinyinGenerator generator = PinyinGenerator.getInstance();
        String pinyin = generator.getPinyin(new DefaultPinyinHandler(), word);
        Set<String> keywordSet = new HashSet<String>();
        keywordSet.add(pinyin);
        return keywordSet;
    }
}

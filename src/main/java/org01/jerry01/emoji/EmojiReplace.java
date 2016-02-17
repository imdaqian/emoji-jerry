package org01.jerry01.emoji;

import org01.jerry01.special.emoji.EmojiBeanParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * core algo about wechat emoji replace
 * <p/>
 * * @author Jerry Deng(zhuyu.deng@yeepay.com)
 */
public class EmojiReplace {

    private final static Logger LOGGER = LoggerFactory.getLogger(EmojiReplace.class);
    private EmojiAlgoDS trie;
    private Map<String, String> emojis;

    public EmojiReplace(Map<String, String> emojis) {
        // 为了保证算法特性,不做特别的构造函数
        this.trie = new EmojiAlgoDS();
        this.emojis = emojis;
        for (String x : emojis.keySet()) {
            trie.insert(x);
        }
    }

    public String emojiHappy(String input) {

        if (input == null || input.length() == 0) {
            throw new RuntimeException("sorry, sb, i can't help u :) for ur empty input. ");
        }
        int start = input.indexOf("/");
        if (start == -1) {
            input = EmojiBeanParser.parseToAliases(input, EmojiBeanParser.FitzpatrickAction.REMOVE);
            return input;
        }

        char[] letters = input.toCharArray();
        System.out.println("l : " + letters.length);
        String result = input;

        int i = start + 1;
        while (i <= letters.length) {
            String x = input.substring(start, i);
            LOGGER.debug("x : " + start + " " + x + " " + i);
            if (trie.has(input.substring(start, i))) {
                LOGGER.debug("----------------------------------" + start + " " + i + " " + input.substring(start, i));
                result = result.replace(input.substring(start, i), emojis.get(input.substring(start, i)));
                LOGGER.debug("r : " + result);
                start = input.indexOf("/", i);
                LOGGER.debug("end : " + start);
                if (start == -1) {
                    break;
                } else {
                    i = start;
                }
            }

            if (i < letters.length && letters[i] == '/') {
                LOGGER.debug("la : " + letters[i]);
                start = i;
            }

            ++i;

        }

        result = EmojiBeanParser.parseToAliases(result, EmojiBeanParser.FitzpatrickAction.REMOVE);
        return result;
    }

}

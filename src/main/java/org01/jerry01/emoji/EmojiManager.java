package org01.jerry01.emoji;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Holds the loaded emojis
 *
 * @author Jerry Deng(zhuyu.deng@yeepay.com)
 */
public class EmojiManager {
    private static final String EMOJI_PATH = "/x";
    private static final Map<String, String> EMOJIS;

    static {
        try {
            InputStream estream = EmojiLoader.class.getResourceAsStream(EMOJI_PATH);
            EMOJIS = EmojiLoader.loadEmojis(estream);
            estream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static EmojiReplace emojiReplace;
    public static EmojiReplace getEmojiReplaceInstance() {
        return emojiReplace == null ? emojiReplace = new EmojiReplace(EMOJIS) : emojiReplace;
    }


    /**
     * No need for a constructor, all the methods are static.
     */
    private EmojiManager() {
    }

}
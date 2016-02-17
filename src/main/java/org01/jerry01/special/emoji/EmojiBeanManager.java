package org01.jerry01.special.emoji;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Holds the loaded emojis and provides search functions.
 *
 */
public class EmojiBeanManager {
    private static final String PATH = "/emojis.json";
    private static final Map<String, EmojiBean> EMOJIS_BY_ALIAS = new HashMap<String, EmojiBean>();
    private static final Map<String, Set<EmojiBean>> EMOJIS_BY_TAG = new HashMap<String, Set<EmojiBean>>();

    static {
        try {
            InputStream stream = EmojiBeanLoader.class.getResourceAsStream(PATH);
            List<EmojiBean> emojis = EmojiBeanLoader.loadEmojis(stream);
            for (EmojiBean emoji : emojis) {
                for (String tag : emoji.getTags()) {
                    if (EMOJIS_BY_TAG.get(tag) == null) {
                        EMOJIS_BY_TAG.put(tag, new HashSet<EmojiBean>());
                    }
                    EMOJIS_BY_TAG.get(tag).add(emoji);
                }
                for (String alias : emoji.getAliases()) {
                    EMOJIS_BY_ALIAS.put(alias, emoji);
                }
            }
            stream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * No need for a constructor, all the methods are static.
     */
    private EmojiBeanManager() {}

    /**
     * Returns all the {@link EmojiBean}s for a given tag.
     *
     * @param tag the tag
     *
     * @return the associated {@link EmojiBean}s, null if the tag is unknown
     */
    public static Set<EmojiBean> getForTag(String tag) {
        if (tag == null) {
            return null;
        }
        return EMOJIS_BY_TAG.get(tag);
    }

    /**
     * Returns the {@link EmojiBean} for a given alias.
     *
     * @param alias the alias
     *
     * @return the associated {@link EmojiBean}, null if the alias is unknown
     */
    public static EmojiBean getForAlias(String alias) {
        if (alias == null) {
            return null;
        }
        return EMOJIS_BY_ALIAS.get(trimAlias(alias));
    }

    private static String trimAlias(String alias) {
        String result = alias;
        if (result.startsWith(":")) {
            result = result.substring(1, result.length());
        }
        if (result.endsWith(":")) {
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }

    /**
     * Returns all the {@link EmojiBean}s
     *
     * @return all the {@link EmojiBean}s
     */
    public static Collection<EmojiBean> getAll() {
        return EMOJIS_BY_ALIAS.values();
    }

    /**
     * Tests if a given String is an emoji.
     *
     * @param string the string to test
     *
     * @return true if the string is an emoji's unicode, false else
     */
    public static boolean isEmoji(String string) {
        if (string != null) {
            for (EmojiBean emoji : getAll()) {
                if (emoji.getUnicode().equals(string)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns all the tags in the database
     *
     * @return the tags
     */
    public static Collection<String> getAllTags() {
        return EMOJIS_BY_TAG.keySet();
    }
}
package org01.jerry01.special.emoji;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Loads the emojis from a JSON database.
 *
 */
public class EmojiBeanLoader {
    /**
     * No need for a constructor, all the methods are static.
     */
    private EmojiBeanLoader() {}

    /**
     * Loads a JSONArray of emojis from an InputStream, parses it and returns the associated list of {@link EmojiBean}s
     *
     * @param stream the stream of the JSONArray
     *
     * @return the list of {@link EmojiBean}s
     * @throws IOException if an error occurs while reading the stream or parsing the JSONArray
     */
    public static List<EmojiBean> loadEmojis(InputStream stream) throws IOException {
        JSONArray emojisJSON = new JSONArray(inputStreamToString(stream));
        List<EmojiBean> emojis = new ArrayList<EmojiBean>(emojisJSON.length());
        for (int i = 0; i < emojisJSON.length(); i++) {
            EmojiBean emoji = buildEmojiFromJSON(emojisJSON.getJSONObject(i));
            if (emoji != null) {
                emojis.add(emoji);
            }
        }
        return emojis;
    }

    private static String inputStreamToString(InputStream stream) {
        Scanner s = new Scanner(stream, "UTF-8").useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    protected static EmojiBean buildEmojiFromJSON(JSONObject json) throws UnsupportedEncodingException {
        if (!json.has("emoji")) {
            return null;
        }

        byte[] bytes = json.getString("emoji").getBytes("UTF-8");
        String description = null;
        if (json.has("description")) {
            description = json.getString("description");
        }
        boolean supportsFitzpatrick = false;
        if (json.has("supports_fitzpatrick")) {
            supportsFitzpatrick = json.getBoolean("supports_fitzpatrick");
        }
        List<String> aliases = jsonArrayToStringList(json.getJSONArray("aliases"));
        List<String> tags = jsonArrayToStringList(json.getJSONArray("tags"));
        return new EmojiBean(description, supportsFitzpatrick, aliases, tags, bytes);
    }

    private static List<String> jsonArrayToStringList(JSONArray array) {
        List<String> strings = new ArrayList<String>(array.length());
        for (int i = 0; i < array.length(); i++) {
            strings.add(array.getString(i));
        }
        return strings;
    }
}

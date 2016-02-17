package org01.jerry01.emoji;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Loads the emojis from file-database.
 *
 * @author Jerry Deng(zhuyu.deng@yeepay.com)
 */
public class EmojiLoader {

    /**
     * No need for a constructor, all the methods are static.
     */
    private EmojiLoader() {}

    /**
     * Loads emojis from an InputStream
     *
     * @param stream the stream of the file-database
     *
     * @return emojis
     * @throws IOException if an error occurs while reading the stream or parsing the file
     */
    public static Map<String, String> loadEmojis(InputStream stream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
        Map<String, String> emojis = new HashMap<String, String>();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            String[] arr = line.split("\t");
            emojis.put(arr[0], "<image src='" + arr[2] + "'/>");
        }
        return emojis;
    }

}

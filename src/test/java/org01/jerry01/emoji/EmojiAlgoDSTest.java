package org01.jerry01.emoji;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by yp-tc-m-2505 on 1/16/16.
 */
public class EmojiAlgoDSTest {

    EmojiAlgoDS algoDS;
    Map<String, String> EMOJIS;

    @Before
    public void setUp() throws Exception {
        String EMOJI_PATH = "/x";
        InputStream estream = EmojiLoader.class.getResourceAsStream(EMOJI_PATH);
        EMOJIS = EmojiLoader.loadEmojis(estream);
        estream.close();

        algoDS = new EmojiAlgoDS();
        for (String x : EMOJIS.keySet()) {
            algoDS.insert(x);
        }
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testHas_for_the_algo_is_right() throws Exception {
        for (String x : EMOJIS.keySet()) {
            assertEquals(true, algoDS.has(x));
        }

    }
}
package org01.jerry01.emoji;

import org.junit.Test;

import java.io.InputStream;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by yp-tc-m-2505 on 1/16/16.
 */
public class EmojiLoaderTest {

    @Test
    public void testLoadEmojis_for_init_num() throws Exception {

        InputStream estream = EmojiLoader.class.getResourceAsStream("/x");
        Map EMOJIS = EmojiLoader.loadEmojis(estream);

        assertEquals(105, EMOJIS.size());

    }
}
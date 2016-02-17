package org01.jerry01.emoji;

import org01.jerry01.special.emoji.EmojiBeanParser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * Created by yp-tc-m-2505 on 1/16/16.
 */
public class EmojiReplaceTest {

    private EmojiReplace emojiReplace;
    Map<String, String> EMOJIS;

    @Before
    public void setUp() throws Exception {
        emojiReplace = EmojiManager.getEmojiReplaceInstance();
        InputStream estream = EmojiLoader.class.getResourceAsStream("/x");
        EMOJIS = EmojiLoader.loadEmojis(estream);
    }

    @After
    public void tearDown() throws Exception {
        emojiReplace = null;
    }

    @Test
    public void testEmojiHappy_two_onebyone() throws Exception {

        String tt = "dafdafdafdsafdsafdsafa你好啊/:strong/:strongdsafdsafa";
        assertEquals("dafdafdafdsafdsafdsafa你好啊<image src='http://cache.soso.com/img/img/e179.gif'/><image src='http://cache.soso.com/img/img/e179.gif'/>dsafdsafa", emojiReplace.emojiHappy(tt));
    }

    @Test
    public void testAlone() {
        String tt = "/:love";
        assertEquals(EMOJIS.get(tt), emojiReplace.emojiHappy(tt));
    }

    @Test
    public void testTwoOnebyOne() {
//        String unit = "/:love";
        for (String x : EMOJIS.keySet()) {
            String tt = x + x;
            assertEquals(EMOJIS.get(x) + EMOJIS.get(x), emojiReplace.emojiHappy(tt));
        }
    }

    Random random = new Random();

    @Test
    public void testRandowCombine() {
        List<String> es = new LinkedList<String>();
        for (String x : EMOJIS.keySet()) {
            es.add(x);
        }

        for (int i = 0; i < random.nextInt(es.size()); ++i) {
            int x = random.nextInt(es.size());
            int y = random.nextInt(es.size());
//            System.out.println(EMOJIS.get(es.get(x)));
//            System.out.println(EMOJIS.get(es.get(y)));
//            System.out.println(es.get(x));
//            System.out.println(es.get(y));
            assertEquals(EMOJIS.get(es.get(x)) + EMOJIS.get(es.get(y)), emojiReplace.emojiHappy(es.get(x) + es.get(y)));
        }
    }

    @Test
    public void testAheadEmoji() {
        List<String> es = new LinkedList<String>();
        for (String x : EMOJIS.keySet()) {
            es.add(x);
        }
        String suffix = "/wofdsafdaa/dfadsfa/fda";

        for (int i = 0; i < random.nextInt(es.size()); ++i) {
            int x = random.nextInt(es.size());
            String waitForTesting = es.get(x) + suffix;
            String a = EMOJIS.get(es.get(x));
            System.out.println(a);
            assertEquals(a + suffix, emojiReplace.emojiHappy(waitForTesting));
        }
    }

    @Test
    public void testEndEmoji() {
        List<String> es = new LinkedList<String>();
        for (String x : EMOJIS.keySet()) {
            es.add(x);
        }
//        String preffix = "/wofdsafdaa/dfadsfa/fda/faf/::dfad";
        String preffix = "/wofdsafdaa/dfadsfa/fd";

        for (int i = 0; i < random.nextInt(es.size()); ++i) {
            int x = random.nextInt(es.size());
            String waitForTesting = preffix + es.get(x);
            assertEquals(preffix + EMOJIS.get(es.get(x)), emojiReplace.emojiHappy(waitForTesting));
        }

        char[] arr = "abcdefg".toCharArray();
        System.out.println("arr : " + arr.length);
    }

    @Test
    public void testMiddleEmoji() {
        List<String> es = new LinkedList<String>();
        for (String x : EMOJIS.keySet()) {
            es.add(x);
        }
        String midffix = "/wofdsafdaa/dfadsfa/fdafad";
//        String midffix = "你好啊";

        for (int i = 0; i < random.nextInt(es.size()); ++i) {
            int x = random.nextInt(es.size());
            int y = random.nextInt(es.size());
            String waitForTesting = es.get(x) + midffix + es.get(y);
            System.out.println("ww : " + waitForTesting);
            System.out.println("xx : " + EMOJIS.get(x));
            assertEquals(EMOJIS.get(es.get(x)) + midffix + EMOJIS.get(es.get(y)), emojiReplace.emojiHappy(waitForTesting));
        }
    }

    @Test
    public void testNoEmoji() {
//        String midffix = "/wofdsafdaa/dfadsfa/fda/faf/::dfad";
//        String midffix = "我是好孩子";
        String midffix = "好么啊咯好\uE312\uE34B\uE40E\uE403\uE409\uE415\uE40A\uE410";
        System.out.println(emojiReplace.emojiHappy(midffix));
        assertEquals(midffix, emojiReplace.emojiHappy(midffix));

        String resultDecimal = EmojiBeanParser.parseToHtmlDecimal(midffix, EmojiBeanParser.FitzpatrickAction.IGNORE);

        System.out.println(resultDecimal);

        String str = "Here is a boy: \uD83D\uDC66\uD83C\uDFFF\uE312\uE34B\uE40E\uE403\uE409\uE415\uE40A\uE410!";
        System.out.println(EmojiBeanParser.parseToAliases(str));
        System.out.println(EmojiBeanParser.parseToAliases(str, EmojiBeanParser.FitzpatrickAction.PARSE));
// Prints twice: "Here is a boy: :boy|type_6:!"
        System.out.println(EmojiBeanParser.parseToAliases(str, EmojiBeanParser.FitzpatrickAction.REMOVE));
// Prints: "Here is a boy: :boy:!"
        System.out.println(EmojiBeanParser.parseToAliases(str, EmojiBeanParser.FitzpatrickAction.IGNORE));
// Prints: "Here is a boy: :boy:🏿!"


    }


}
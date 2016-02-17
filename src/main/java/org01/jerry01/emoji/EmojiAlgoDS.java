package org01.jerry01.emoji;

public class EmojiAlgoDS {

    private int SIZE = 1000;
    private TrieNode root;

    public EmojiAlgoDS() {
        root = new TrieNode();
    }

    /**
     * inner class representing emoji-trie
     */
    private class TrieNode {
        //有多少单词通过这个节点,即节点字符出现的次数
        private int num;
        private TrieNode[] son;
        private boolean isEnd;
        private char val;

        TrieNode() {
            num = 1;
            son = new TrieNode[SIZE];
            isEnd = false;
        }
    }

    // build emoji by insert a string
    public void insert(String str) {
        if (str == null || str.length() == 0) {
            return;
        }
        TrieNode node = root;
        char[] letters = str.toCharArray();
        for (int i = 0, len = str.length(); i < len; i++) {
            int pos = letters[i];
            if (node.son[pos] == null) {
                node.son[pos] = new TrieNode();
                node.son[pos].val = letters[i];
            } else {
                node.son[pos].num++;
            }
            node = node.son[pos];
        }
        node.isEnd = true;
    }

    // count num of preifx-word
    public int countPrefix(String prefix) {
        if (prefix == null || prefix.length() == 0) {
            return -1;
        }
        TrieNode node = root;
        char[] letters = prefix.toCharArray();
        for (int i = 0, len = prefix.length(); i < len; i++) {
            int pos = letters[i];
            if (node.son[pos] == null) {
                return 0;
            } else {
                node = node.son[pos];
            }
        }
        return node.num;
    }

    // whether match str in trie complete
    public boolean has(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        TrieNode node = root;
        char[] letters = str.toCharArray();
        for (int i = 0, len = str.length(); i < len; i++) {
            int pos = letters[i];
            if (node.son[pos] != null) {
                node = node.son[pos];
            } else {
                return false;
            }
        }
        return node.isEnd;
    }

    public void preTraverse(TrieNode node) {
        if (node != null) {
            for (TrieNode child : node.son) {
                preTraverse(child);
            }
        }
    }

    public TrieNode getRoot() {
        return this.root;
    }

}
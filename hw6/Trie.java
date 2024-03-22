import java.util.HashMap;
import java.util.Map;

public class Trie {
    private class TrieNode {
        Map<Character, TrieNode> next;
        boolean isLeaf = false;
        TrieNode() {
            next = new HashMap<>();

        }
    }
    TrieNode root;
    Trie() {
        root = new TrieNode();
    }
    public void add(String s) {
        TrieNode curNode = root;
        for (char c : s.toCharArray()) {
            curNode.next.putIfAbsent(c, new TrieNode());
            curNode = curNode.next.get(c);
        }
        curNode.isLeaf = true;
    }

    public boolean contain(String s) {
        if (s == null) {
            return false;
        }

        TrieNode curNode = root;
        for (char c : s.toCharArray()) {
            curNode = curNode.next.get(c);
            if (curNode == null) {
                return false;
            }
        }
        return true;
    }

    public boolean containWord(String s) {
        if (s == null) {
            return false;
        }
        TrieNode curNode = root;
        for (char c : s.toCharArray()) {
            curNode = curNode.next.get(c);
            if (curNode == null) {
                return false;
            }
        }
        return curNode.isLeaf;
    }

}
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * @Auther: XuZhiyu
 * @Date: 2024/3/22 下午8:00
 */
public class BinaryTrie implements Serializable {
    private class Node implements Comparable<Node>, Serializable {
        char ch;
        int frequency;
        Node left;
        Node right;
        Node(char c, int freq, Node l, Node r) {
            ch = c;
            frequency = freq;
            left = l;
            right = r;
        }
        Node(char c, int freq) {
            ch = c;
            frequency = freq;
            left = null;
            right = null;
        }
        Node(int freq, Node l, Node r) {
            frequency = freq;
            left = l;
            right = r;
        }
        boolean isLeaf() {
            return (left == null && right == null);
        }
        @Override
        public int compareTo(Node n) {
            return this.frequency - n.frequency;
        }

    }
    private final Map<BitSequence, Character> searchTable;
    public BinaryTrie(Map<Character, Integer> frequencyTable) {
        Node trieRoot = buildTrie(frequencyTable);
        searchTable = new HashMap<>();
        dfs(trieRoot, new BitSequence(), searchTable);
    }

    private Node buildTrie(Map<Character, Integer> frequencyTable) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        for (Map.Entry<Character, Integer> entry : frequencyTable.entrySet()) {
            pq.add(new Node(entry.getKey(), entry.getValue()));
        }
        while (pq.size() > 1) {
            Node left0 = pq.remove();
            Node right1 = pq.remove();
            Node parent = new Node(left0.frequency + right1.frequency, left0, right1);
            pq.add(parent);
        }
        return pq.remove();
    }

    public Match longestPrefixMatch(BitSequence querySequence) {
        BitSequence b = new BitSequence();
        for (int i = 0; i < querySequence.length(); ++i) {
            b = b.appended(querySequence.bitAt(i));
            if (searchTable.containsKey(b)) {
                return new Match(b, searchTable.get(b));
            }
        }
        return null;
    }
    public Map<Character, BitSequence> buildLookupTable() {
        HashMap<Character, BitSequence> ans = new HashMap<>();
        for (Map.Entry<BitSequence, Character> e : searchTable.entrySet()) {
            ans.put(e.getValue(), e.getKey());
        }
        return ans;
    }

    private void dfs(Node node, BitSequence bits, Map<BitSequence, Character> map) {
        if (node.isLeaf()) {
            map.put(bits, node.ch);
            return;
        }
        dfs(node.left, bits.appended(0), map);
        dfs(node.right, bits.appended(1), map);
    }
}

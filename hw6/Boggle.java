import java.util.LinkedList;
import java.util.List;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.HashSet;

public class Boggle {
    
    // File path of dictionary file
    static String dictPath = "words.txt";
    private static final int[][] NEXT = {{0, 1}, {1, 0}, {0, -1}, {-1, 0},
                                         {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
    private static final Comparator<String> BOGGLE_COMPARATOR = (str1, str2) -> {
        int lengthComparison = Integer.compare(str1.length(), str2.length());
        if (lengthComparison == 0) {
            return str1.compareTo(str2);
        }
        return -lengthComparison;
    };
    private static char[][] board;
    private static Trie mytrie;
    private static boolean[][] mark;
    private static PriorityQueue<String> ans;
    private static int M, N;
    private static HashSet<String> ansSet;
    /**
     * Solves a Boggle puzzle.
     * 
     * @param k The maximum number of words to return.
     * @param boardFilePath The file path to Boggle board file.
     * @return a list of words found in given Boggle board.
     *         The Strings are sorted in descending order of length.
     *         If multiple words have the same length,
     *         have them in ascending alphabetical order.
     */
    public static List<String> solve(int k, String boardFilePath) {
        if (k <= 0) {
            throw new IllegalArgumentException();
        }
        In in = new In(dictPath);
        In boardIn = new In(boardFilePath);
        //read Files
        ans = new PriorityQueue<>(BOGGLE_COMPARATOR);
        ansSet = new HashSet<>();
        mytrie = new Trie();
        String[] strings = boardIn.readAllStrings();
        M = strings.length;
        N = strings[0].length();
        board = new char[M][];
        mark = new boolean[M][N];
        for (int i = 0; i < M; ++i) {
            if (strings[i].length() != N) {
                throw new IllegalArgumentException();
            }
            board[i] = strings[i].toCharArray();
        }
        //initialize variable
        for (String s : in.readAllStrings()) {
            if (s.length() > 2) {
                mytrie.add(s);
            }
        }
        //build Trie
        for (int i = 0; i < M; ++i) {
            for (int j = 0; j < N; ++j) {
                dfs(i, j, String.valueOf(board[i][j]));
            }
        }
        //dfs at each starting point
        LinkedList<String> ret = new LinkedList<>();
        while (!ans.isEmpty() && ret.size() < k) {
            ret.add(ans.remove());
        }
        //transform answer to list
        return ret;
    }
    private static void dfs(int x, int y, String s) {
        if (!ansSet.contains(s) && mytrie.containWord(s)) {
            ans.add(s);
            ansSet.add(s);
        }
        mark[x][y] = true;
        for (int i = 0; i < 8; ++i) {
            int tx = x + NEXT[i][0];
            int ty = y + NEXT[i][1];
            if (tx >= 0 && ty >= 0 && tx < M && ty < N && !mark[tx][ty]) {
                String nextString = s + board[tx][ty];
                if (mytrie.contain(nextString)) {
                    dfs(tx, ty, nextString);
                }
            }
        }
        mark[x][y] = false;
    }
}
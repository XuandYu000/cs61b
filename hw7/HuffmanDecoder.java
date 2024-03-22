/**
 * @Auther: XuZhiyu
 * @Date: 2024/3/22 下午8:10
 */
public class HuffmanDecoder {
    public static void main(String[] args) {
        ObjectReader or = new ObjectReader(args[0]);
        BitSequence allBits = (BitSequence) or.readObject();
        BinaryTrie binaryTrie = (BinaryTrie) or.readObject();

        int idx = 0;
        StringBuilder stringBuilder = new StringBuilder();
        while (allBits.length() > idx) {
            Match match = binaryTrie.longestPrefixMatch(allBits.allButFirstNBits(idx));
            stringBuilder.append(match.getSymbol());
            idx += match.getSequence().length();
        }

        FileUtils.writeCharArray(args[1], stringBuilder.toString().toCharArray());
    }
}

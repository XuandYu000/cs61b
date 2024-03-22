import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @Auther: XuZhiyu
 * @Date: 2024/3/22 下午8:07
 */
public class HuffmanEncoder {
    public static Map<Character, Integer> buildFrequencyTable(char[] inputSymbols) {
        Map<Character, Integer> table = new HashMap<>();
        for (char ch : inputSymbols) {
            table.merge(ch, 1, Integer::sum);
        }
        return table;
    }
    public static void main(String[] args) {
        char[] chars = FileUtils.readFile(args[0]);
        BinaryTrie binaryTrie = new BinaryTrie(buildFrequencyTable(chars));
        Map<Character, BitSequence> charToBits = binaryTrie.buildLookupTable();
        List<BitSequence> bitSequenceList = new LinkedList<>();
        for (char ch : chars) {
            bitSequenceList.add(charToBits.get(ch));
        }
        ObjectWriter ow = new ObjectWriter(args[0] + ".huf");
        BitSequence fianlBits = BitSequence.assemble(bitSequenceList);

        ow.writeObject(fianlBits);
        ow.writeObject(binaryTrie);
    }
}

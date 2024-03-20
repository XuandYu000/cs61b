/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {
    private static int MaxL = 0;
    private static String[] sorted;
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        for (String item : asciis) {
            MaxL = MaxL > item.length() ? MaxL : item.length();
        }
        sorted = asciis.clone();
        sortHelperLSD(sorted, 0);
        return sorted;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index) {
        // Optional LSD helper method for required LSD radix sort
        if (index == MaxL) {
            return;
        }

        int[] counts = new int[256];
        int[] indexes = new int[256];

        for (String item : asciis) {
            int bucket = CharAt(item, index);
            counts[bucket] ++;
        }

        int pos = 0;
        for (int i = 0; i < counts.length; i ++) {
            indexes[i] = pos;
            pos += counts[i];
        }

        String[] sorted1 = new String[asciis.length];
        for (int i = 0; i < asciis.length; i++) {
            String item = asciis[i];
            int place = indexes[CharAt(item, index)];
            sorted1[place] = item;
            indexes[CharAt(item, index)] += 1;
        }
        sorted = sorted1;
        sortHelperLSD(sorted1, index + 1);
    }

    private static int CharAt(String item, int index) {
        int sub = MaxL - 1 - index;
        if (item.length() - 1 >= sub) {
            return item.charAt(sub);
        } else {
            return 0;
        }
    }

    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        return;
    }

    public static void main(String[] args) {
        String[] a = {"astronomy", "aster", "as", "astrolabe"};
        String[] sorted = RadixSort.sort(a);
    }
}

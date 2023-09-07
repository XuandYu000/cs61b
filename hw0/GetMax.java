public class GetMax {
    /** Returns the maximum value from m. */
    public static int max(int[] m) {
        int max = -1;
        for(int i = 0; i < m.length; i++){
            if(max < m[i]){
                max = m[i];
            }
        }
        return max;
    }
    public static void main(String[] args) {
       int[] numbers = new int[]{9, 2, 15, 2, 22, 10, 6};
       System.out.println(max(numbers));      
    }
}
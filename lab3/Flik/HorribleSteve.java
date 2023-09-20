public class HorribleSteve {
    public static void main(String [] args) {
        int i = 0;
        for (int j = 0; i < 500; ++i, ++j) {
              // 常量池-128~127中Integer进行值比较, 除此之外进行地址比较
//            if (!Flik.isSameNumber(i, j)) {
//                break; // break exits the for loop!
//            }
              if(!((Integer)i == (Integer) j)) break;
        }
        System.out.println("i is " + i);
    }
}

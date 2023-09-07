public class Triangle{
    public static void main(String[] args){
        int x = 1;
        while(x <= 5){
            for(int i = 0; i < x; i++){
                System.out.print('*');
            }
            System.out.println("");
            x = x + 1;
        }
    }
}
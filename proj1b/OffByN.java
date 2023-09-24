public class OffByN implements CharacterComparator{
    private int offSet;

    public OffByN(int N){
        offSet = N;
    }

    @Override
    public boolean equalChars(char x, char y){
        if(x - y == offSet || y - x == offSet){
            return true;
        }
        return false;
    }
}

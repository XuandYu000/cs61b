public class OffByN implements CharacterComparator{
    private int offSet;

    public OffByN(int N){
        this.offSet = N;
    }

    @Override
    public boolean equalChars(char x, char y){
        return Math.abs(x - y) == n;
    }
}

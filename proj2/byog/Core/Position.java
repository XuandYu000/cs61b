package byog.Core;


/**
 * @author overlookingview
 * @Description Point's position
 * @create 2023-12-07 下午8:22
 */
public class Position {
    private int x;
    private int y;

    Position(int xx, int yy) {
        this.x = xx;
        this.y = yy;
    }
    int getX() {
        return this.x;
    }

   int getY() {
        return this.y;
    }
}

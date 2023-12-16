package byog.Core;
import edu.princeton.cs.introcs.StdDraw;
import org.omg.CORBA.DynAnyPackage.Invalid;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

/**
 * @Auther: XuZhiyu
 * @Date: 2023/12/16 下午6:28
 */
public class MainMeau {
    private int width;
    private int height;
    private static final Character[] AvailableChoice = {'n', 'l', 'q'};
    private static final Font BIG = new Font("Monaco", Font.BOLD, 30);
    private static final Font SMALL = new Font("Monaco", Font.PLAIN, 20);

    public static void main(String[] args) {
        MainMeau meau = new MainMeau(40, 40);
        String option = meau.StartGame();
        System.out.println(option);
    }
    public MainMeau(int width, int height) {
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
    }

    public String StartGame() {
        String title = "CS61B: THE GAME";
        String[] options = {"New Game (N)", "Load Game (L)", "Quit (Q)"};
        drawFrame(title, options);
        char choice = 'q';
        if(StdDraw.hasNextKeyTyped()) {
            choice = StdDraw.nextKeyTyped();
        }
        if(Available(Character.toLowerCase(choice))) {
            return Character.toString(choice);
        } else {
            drawFrame("Invalid Input");
            System.exit(0);
            return null;
        }
    }

    private void drawFrame(String Title, String[] Options) {
        StdDraw.clear(Color.BLACK);

        // draw title
        StdDraw.setFont(BIG);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(0.5 * width, 0.8 * height, Title);

        // draw options
        StdDraw.setFont(SMALL);
        double textWidth = 0.5;
        double textHeight = 0.5;
        for(int i =  0, len = Options.length; i < len; i ++) {
            StdDraw.text(textWidth * width, textHeight * height, Options[i]);
            textHeight -= 0.05;
        }
        StdDraw.show();
        StdDraw.pause(5000);
    }

    private void drawFrame(String Title) {
        StdDraw.clear(Color.BLACK);

        // draw title
        StdDraw.setFont(BIG);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(0.5 * width, 0.8 * height, Title);
        StdDraw.show();
        StdDraw.pause(2500);
    }

    private boolean Available(Character option) {
        for(Character choice : AvailableChoice) {
            if(choice == option) {
                return true;
            }
        }
        return false;
    }
}

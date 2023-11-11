/**
 * @author Xu Zhiyu
 * @date 2023/11/11 下午1:41
 * @desciption: Allowing the user (you!) to interactively play sounds using the 
 * synthesizer package’s GuitarString class.
 */
import edu.princeton.cs.algs4.StdAudio;
import synthesizer.GuitarString;
public class GuitarHero {
    private static final double CONCETY = 440.0;
    private static final String KEYBOARD = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";

    private static double compute(int i) { 
        return CONCETY * Math.pow(2, ((double) i - 24.0) / 12.0);
    }

    private static double sumSample(GuitarString[] string) {
        double ans = 0;
        for (int i = 0, siz = string.length; i < siz; i++) {
            ans += string[i].sample();
        }
        return ans;
    }

    public static void main(String[] args) {
        GuitarString[] string = new GuitarString[37];
        for (int i = 0, siz = KEYBOARD.length(); i < siz; i++) {
            string[i] = new GuitarString(compute(i));
        }
        while (true) {
//            check if the user has typed a key; if so, process it and
//            the program can't clash if there is invalid input
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                int index = KEYBOARD.indexOf(key);
                if (index != -1) {
                    string[index].pluck();
                }
            }

            // compute the superposition of samples
            double sample = sumSample(string);

            // play the sample on standard audio
            StdAudio.play(sample);

            // advance the simulation of each guitar by one step
            for (int i = 0,  siz = string.length; i < siz; i++) {
                string[i].tic();
            }
        }
    }
}

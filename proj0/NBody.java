public class NBody {
    public static double readRadius(String fn) {
        In in = new In(fn);
        in.readInt();
        return in.readDouble();
    }

    public static Planet[] readPlanets(String fn) {
        In inp = new In(fn);
        int num = inp.readInt();
        inp.readDouble();
        Planet[] res = new Planet[num];
        // 在初始化Planet数组后不能直接复制,因为此时在Planet.java中并没有定义Planet()构造方法,所以初始化Planet数组后得到的是空指针而不是空对象
        for (int i = 0; i < num; i++) {
            double xxp = inp.readDouble();
            double yyp = inp.readDouble();
            double xxv = inp.readDouble();
            double yyv = inp.readDouble();
            double mass = inp.readDouble();
            String img = inp.readString();
            res[i] = new Planet(xxp, yyp, xxv, yyv, mass, img);
        }

        return res;
    }

    private static void drawPlanets(Planet[] plts) {
        for (Planet plt : plts) {
            Planet tmp = new Planet(plt);
            tmp.imgFileName = "images/" + tmp.imgFileName;
            tmp.draw();
        }
    }

    private static void drawBackground(double radius, String backGround) {
        StdDraw.setScale(-radius, radius);
        StdDraw.clear();
        StdDraw.picture(0, 0, backGround);
    }


    private static final int waitTime = 10;
    private static final double eps = 1e-6;

    public static void main(String[] args) {
        StdDraw.enableDoubleBuffering();
        // Collecting All Needed Input
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double radius = readRadius(filename);
        Planet[] plts = readPlanets(filename);
        String backGround = "images/starfield.jpg";

        // Drawing the Background
        drawBackground(radius, backGround);

        // Drawing All of the Planets
        drawPlanets(plts);

        // Create an Animation
        double tamp = 0;
        while (T - tamp >= eps) {
            int len = plts.length;
            double[] xForces = new double[len];
            double[] yForces = new double[len];
            for (int j = 0; j < len; j++) {
                xForces[j] = plts[j].calcNetForceExertedByX(plts);
                yForces[j] = plts[j].calcNetForceExertedByY(plts);
                plts[j].update(dt, xForces[j], yForces[j]);
            }
            StdDraw.clear();
            drawBackground(radius, backGround);
            drawPlanets(plts);
            StdDraw.show();
            StdDraw.pause(waitTime);
            tamp += dt;
        }

        // Printing the Universe
        StdOut.printf("%d\n", plts.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < plts.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    plts[i].xxPos, plts[i].yyPos, plts[i].xxVel,
                    plts[i].yyVel, plts[i].mass, plts[i].imgFileName);
        }
    }
}

public class Planet {
    // declare the constant
    private static final double G = 6.67e-11;


    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

    // Constructor
    public Planet(double xP, double yP, double xV,
                  double yV, double m, String img) {
        xxPos = xP; yyPos = yP; xxVel = xV;
        yyVel = yV; mass = m; imgFileName = img;
    }

    // Constructor
    public Planet(Planet p) {
        // xxPos = p.xxPos; yyPos = p.yyPos; xxVel = p.xxVel;
        // yyVel = p.yyVel; mass = p.mass; imgFileName = p.imgFileName;
        this(p.xxPos, p.yyPos, p.xxVel, p.yyVel, p.mass, p.imgFileName);
    }

    // distance between two planets
    public double calcDistance(Planet planet){
        double dx = planet.xxPos - this.xxPos;
        double dy = planet.yyPos - this.yyPos;
        return Math.sqrt(dx * dx + dy * dy);
    }

    // the square the distance
    private double squaredDistance(Planet planet){
        return calcDistance(planet) * calcDistance(planet);
    }
    
    // gravitational force 
    public double calcForceExertedBy(Planet planet){
        return G * planet.mass * this.mass / squaredDistance(planet);
    }

    // pairwise force of x
    public double calcForceExertedByX(Planet planet){
        return calcForceExertedBy(planet) * (planet.xxPos - this.xxPos) / calcDistance(planet);
    }

    // pairwise force of y
    public double calcForceExertedByY(Planet planet){
        return calcForceExertedBy(planet) * (planet.yyPos - this.yyPos) / calcDistance(planet);
    }

    // Net Force of all the planets exerted by x
    public double calcNetForceExertedByX(Planet[] plts){
        double NetForceX = 0;
        for(Planet plt : plts){
            if(!this.equals(plt)){
                NetForceX += calcForceExertedByX(plt);
            }
        }
        return NetForceX;
    }

    // Net Force of all the planets exerted by y
    public double calcNetForceExertedByY(Planet[]  plts){
        double NetForceY = 0;
        for(Planet plt : plts){
            if(!this.equals(plt)){
                NetForceY += calcForceExertedByY(plt);
            }
        }
        return NetForceY;
    }


    // update the position with the fxN exerted by X and the fyN exerted by Y continuing dt
    public void update(double dt, double fX, double fY){
            double ax = fX / this.mass;
            double ay = fY / this.mass;
            xxVel += dt * ax;
            yyVel += dt * ay;
            xxPos += dt * xxVel;
            yyPos += dt * yyVel;
    }

    // Drawing One Planet
    public void draw(){
        StdDraw.picture(xxPos, yyPos, imgFileName);
    }
}


public class Planet {
    static final double G = 6.67e-11;
    public double xxPos, yyPos;
    public double xxVel, yyVel;
    public double mass;
    public String imgFileName;

    public Planet(double xP, double yP, double xV, double yV, double m, String img) {
        this.xxPos = xP;
        this.yyPos = yP;
        this.xxVel = xV;
        this.yyVel = yV;
        this.mass = m;
        this.imgFileName = img;
    }

    public Planet(Planet b) {
        this(b.xxPos, b.yyPos, b.xxVel, b.yyVel, b.mass, b.imgFileName);
    }

    public Planet() {
    }

    public double calcDistance(Planet other) {
        double dx = other.xxPos - xxPos, dy = other.yyPos - yyPos;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public double calcForceExertedBy(Planet other) {
        double r = calcDistance(other);
        if (r == 0.0)
            return 0;
        return G * mass * other.mass / (r * r);
    }

    public double calcForceExertedByX(Planet other) {
        double dx = other.xxPos - xxPos;
        double r = calcDistance(other);
        if (r == 0.0)
            return 0;
        return calcForceExertedBy(other) * dx / r;
    }

    public double calcForceExertedByY(Planet other) {
        double dy = other.yyPos - yyPos;
        double r = calcDistance(other);
        if (r == 0.0)
            return 0;
        return calcForceExertedBy(other) * dy / r;
    }

    public void update(double t, double fx, double fy) {
        double ax = fx / mass, ay = fy / mass;
        xxVel += t * ax;
        yyVel += t * ay;
        xxPos = xxPos + t * xxVel;
        yyPos = yyPos + t * yyVel;
    }

    public void draw() {
        StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
    }

    public double calcNetForceExertedByX(Planet[] planets) {
        double res = 0;
        for (Planet p : planets) {
            if (this == p)
                continue;
            res += calcForceExertedByX(p);
        }
        return res;
    }

    public double calcNetForceExertedByY(Planet[] planets) {
        double res = 0;
        for (Planet p : planets) {
            if (this == p)
                continue;
            res += calcForceExertedByY(p);
        }
        return res;
    }

}
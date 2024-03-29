import javax.swing.text.PlainDocument;
import javax.swing.text.PlainView;

public class NBody {
    public static double readRadius(String s) {
        In in = new In(s);
        double n = in.readInt();
        double radius = in.readDouble();
        return radius;
    }

    public static Planet[] readPlanets(String s) {
        In in = new In(s);
        int n = in.readInt();
        double r = in.readDouble();
        Planet[] res = new Planet[n];
        for (int i = 0; i < n; i++) {
            double xxPos = in.readDouble();
            double yyPos = in.readDouble();
            double xxVel = in.readDouble();
            double yyVel = in.readDouble();
            double mass = in.readDouble();
            String imgFileName = in.readString();
            res[i] = new Planet(xxPos, yyPos, xxVel, yyVel, mass, imgFileName);
        }

        return res;
    }

    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("needs three args: t, dt, filename");
        }
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];

        double r = readRadius(filename);
        Planet[] planets = readPlanets(filename);
        int n = planets.length;
        String imageBackground = "./images/starfield.jpg";
        StdDraw.setScale(-r, r);
        StdDraw.clear();
        StdDraw.picture(0, 0, imageBackground);
        for (Planet p : planets) {
            p.draw();
        }

        StdDraw.enableDoubleBuffering();
        for (double t = 0; T - t > 1e-6; t = t + dt) {
            double[][] f = new double[n][2];
            for (int i = 0; i < n; i++) {
                f[i][0] = planets[i].calcNetForceExertedByX(planets);
                f[i][1] = planets[i].calcNetForceExertedByY(planets);
            }
            for (int i = 0; i < n; i++) {
                planets[i].update(dt, f[i][0], f[i][1]);
            }

            StdDraw.clear();
            StdDraw.picture(0, 0, imageBackground);
            for (Planet p : planets) {
                p.draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
        }

        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", r);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                    planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
        }
    }
}

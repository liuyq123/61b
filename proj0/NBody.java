public class NBody {
	public static double readRadius(String FileName) {
		In in = new In(FileName);
		in.readInt();
		Double radius = in.readDouble();
		return radius;
	}

	public static Planet[] readPlanets(String FileName) {
		In in = new In(FileName);
		int number = in.readInt();
		in.readDouble();
		Planet[] planets = new Planet[number];
		int i = 0;
		while (i < number) {
			double xP = in.readDouble();
			double yP = in.readDouble();
			double xV = in.readDouble();
			double yV = in.readDouble();
			double m = in.readDouble();
			String img = in.readString();
		    Planet p = new Planet(xP, yP, xV, yV, m, img);
			planets[i] = p;
			i += 1;
		}
		return planets;
	}

	public static void main (String[] args) {
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];
		double radius = readRadius(filename);
		Planet[] planets = readPlanets(filename);
		StdDraw.setScale(-radius, radius);
		StdDraw.clear();

		StdDraw.picture(0.0, 0.0, "images/starfield.jpg");
		for (Planet p : planets) {
			p.draw();
		}

		StdDraw.enableDoubleBuffering();
		double time = 0.0;
		double[] xForces = new double[planets.length];
		double[] yForces = new double[planets.length];
		while (time < T) {
			for (int i = 0; i < planets.length; i++) {
				xForces[i] = planets[i].calcNetForceExertedByX(planets);
				yForces[i] = planets[i].calcNetForceExertedByY(planets);
			}
			for (int i = 0; i < planets.length; i++) {
				planets[i].update(dt, xForces[i], yForces[i]);
			}
			StdDraw.picture(0.0, 0.0, "images/starfield.jpg");
			for (Planet p : planets) {
				p.draw();
			}
			StdDraw.show();
			StdDraw.pause(10);
			time += dt;
		}

		StdOut.printf("%d\n", planets.length);
		StdOut.printf("%.2e\n", radius);
		for (int i = 0; i < planets.length; i++) {
   	 	StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
            planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
            planets[i].yyVel, planets[i].mass, planets[i].imgFileName);   
		}
	}
}
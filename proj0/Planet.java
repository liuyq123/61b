public class Planet {
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;
	public static final double G = 6.67e-11;

	public Planet(double xP, double yP, double xV, 
		double yV, double m, String img) {
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}

	public Planet(Planet p) {
		xxPos = p.xxPos;
		yyPos = p.yyPos;
		xxVel = p.xxVel;
		yyVel = p.yyVel;
		mass = p.mass;
		imgFileName = p.imgFileName;
	}

	public double calcDistance(Planet p) {
		double distance;
		double dx = p.xxPos - this.xxPos;
		double dy = p.yyPos - this.yyPos;
		distance = Math.sqrt(dx * dx + dy * dy);
		return distance;
	}

	public double calcForceExertedBy(Planet p) {
		double force = (G * this.mass * p.mass) / (calcDistance(p) * calcDistance(p));
		return force;
	}

	public double calcNetForceExertedByX(Planet[] ps) {
		double x_force = 0;
		for (int i = 0; i < ps.length; i++) {
			if (ps[i].equals(this)) {
				continue;
			}
			double total_force = calcForceExertedByX(ps[i]);
			double dx = ps[i].xxPos - this.xxPos;
			double distance = calcDistance(ps[i]);
			x_force += total_force / distance * dx;
		}
		return x_force;
	}

	public double calcNetForceExertedByY(Planet[] ps) {
		double y_force = 0;
		for (int i = 0; i < ps.length; i++) {
			if (ps[i].equals(this)) {
				continue;
			}
			double total_force = calcForceExertedByY(ps[i]);
			double dy = ps[i].yyPos - this.yyPos;
			double distance = calcDistance(ps[i]);
			y_force += total_force / distance * dy;
		}
		return y_force;
	}

	public void update(double dt, double fX, double fY) {
		double aX = fX / this.mass;
		double vX = aX * dt;
		this.xxVel += vX;
		double pX = this.xxVel * dt;
		this.xxPos += pX;

		double aY = fY / this.mass;
		double vY = aY * dt;
		this.yyVel += vY;
		double pY = this.yyVel * dt;
		this.yyPos += pY;
	}

	public void draw() {
		StdDraw.picture(xxPos, yyPos, imgFileName);
	}
}
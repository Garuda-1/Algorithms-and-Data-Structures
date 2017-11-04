import java.util.*;
import java.io.*;

public class Bruteforce_Geometry {
	public static void main(String[] args) throws IOException {
		Scanner in = new Scanner(new File("input.txt"));
		PrintWriter out = new PrintWriter(new File("output.txt"));

		Point a = new Point(in.nextDouble(), in.nextDouble());
		Point b = new Point(in.nextDouble(), in.nextDouble());
		Point c = new Point(in.nextDouble(), in.nextDouble());
		Point d = new Point(in.nextDouble(), in.nextDouble());

		out.println(dist(a, c));
		out.println(dPointSegment(c, d, a));
		out.println(dPointRay(c, d, a));
		out.println(dPointLine(c, d, a));
		out.println(dPointSegment(a, b, c));
		out.println(dSegmentSegment(a, b, c, d));
		out.println(dSegmentRay(a, b, c, d));
		out.println(dSegmentLine(a, b, c, d));
		out.println(dPointRay(a, b, c));
		out.println(dSegmentRay(c, d, a, b));
		out.println(dRayRay(a, b, c, d));
		out.println(dRayLine(a, b, c, d));
		out.println(dPointLine(a, b, c));
		out.println(dSegmentLine(c, d, a, b));
		out.println(dRayLine(c, d, a, b));
		out.println(dLineLine(a, b, c, d));

		out.close();
	}

	static class Point {
		double x;
		double y;

		public Point(double x, double y) {
			super();
			this.x = x;
			this.y = y;
		}
	}

	static class Line {
		double a, b, c;

		public Line(double x1, double y1, double x2, double y2) {
			super();
			this.a = y2 - y1;
			this.b = x1 - x2;
			double div = Math.sqrt(this.a * this.a + this.b * this.b);
			this.a /= div;
			this.b /= div;
			this.c = -this.a * x1 - this.b * y1;
		}
	}

	public static double vProduct(Point a, Point b) {
		return (a.x * b.x + b.y * a.y);
	}

	public static boolean inRange(Point a, Point b, Point c) {
		return ((Math.min(a.x, b.x) <= c.x) && (c.x <= Math.max(a.x, b.x)) && (Math.min(a.y, b.y) <= c.y)
				&& (c.y <= Math.max(a.y, b.y)));
	}

	public static boolean inRangeRay(Point a, Point b, Point c) {
		boolean condx = false;
		boolean condy = false;
		if (a.x > b.x) {
			condx = (c.x <= a.x);
		} else {
			condx = (c.x >= a.x);
		}
		if (a.y > b.y) {
			condy = (c.y <= a.y);
		} else {
			condy = (c.y >= a.y);
		}
		return condx && condy;
	}

	public static Point toZeroV(Point a, Point b) {
		return new Point((b.x - a.x), (b.y - a.y));
	}

	public static Point intersect(Line n, Line m) {
		double d = m.a * n.b - m.b * n.a;
		double d1 = -m.c * n.b + m.b * n.c;
		double d2 = -n.c * m.a + n.a * m.c;
		if (d == 0)
			return null;
		return new Point(d1 / d, d2 / d);
	}

	public static double dist(Point a, Point b) {
		return Math.sqrt((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y));
	}

	public static double dPointSegment(Point a, Point b, Point c) {
		if (vProduct(toZeroV(a, c), toZeroV(a, b)) * vProduct(toZeroV(b, c), toZeroV(b, a)) > 0) {
			double al = b.y - a.y;
			double bl = a.x - b.x;
			double div = Math.sqrt(al * al + bl * bl);
			al /= div;
			bl /= div;
			double cl = -b.x * al - b.y * bl;
			return Math.abs(al * c.x + bl * c.y + cl) / Math.sqrt(al * al + bl * bl);
		} else {
			return Math.min(dist(c, a), dist(c, b));
		}
	}

	public static double dPointRay(Point a, Point b, Point c) {
		if (vProduct(toZeroV(a, c), toZeroV(a, b)) > 0) {
			double al = b.y - a.y;
			double bl = a.x - b.x;
			double cl = -a.x * al - a.y * bl;
			return Math.abs(al * c.x + bl * c.y + cl) / Math.sqrt(al * al + bl * bl);
		} else {
			return Math.min(dist(c, a), dist(c, b));
		}
	}

	public static double dPointLine(Point a, Point b, Point c) {
		double al = b.y - a.y;
		double bl = a.x - b.x;
		double cl = -a.x * al - a.y * bl;
		return Math.abs(al * c.x + bl * c.y + cl) / Math.sqrt(al * al + bl * bl);
	}

	public static double dSegmentSegment(Point a, Point b, Point c, Point d) {
		Line m = new Line(a.x, a.y, b.x, b.y);
		Line n = new Line(c.x, c.y, d.x, d.y);

		Point x = intersect(n, m);

		double res = Double.MAX_VALUE;

		if (x != null) {
			if (inRange(a, b, x) && (inRange(c, d, x))) {
				res = 0;
			}
		}

		if (inRange(c, d, intersect(n, new Line(a.x, a.y, a.x + n.a, a.y + n.b)))) {
			res = Math.min(dPointLine(c, d, a), res);
		}
		if (inRange(c, d, intersect(n, new Line(b.x, b.y, b.x + n.a, b.y + n.b)))) {
			res = Math.min(dPointLine(c, d, b), res);
		}
		if (inRange(a, b, intersect(m, new Line(c.x, c.y, c.x + m.a, c.y + m.b)))) {
			res = Math.min(dPointLine(a, b, c), res);
		}
		if (inRange(a, b, intersect(m, new Line(d.x, d.y, d.x + m.a, d.y + m.b)))) {
			res = Math.min(dPointLine(a, b, d), res);
		}

		res = Math.min(res, dist(a, c));
		res = Math.min(res, dist(a, d));
		res = Math.min(res, dist(b, c));
		res = Math.min(res, dist(b, d));

		return res;
	}

	public static double dSegmentRay(Point a, Point b, Point c, Point d) {
		Line m = new Line(a.x, a.y, b.x, b.y);
		Line n = new Line(c.x, c.y, d.x, d.y);

		Point x = intersect(n, m);

		double res = Double.MAX_VALUE;

		if (x != null) {
			if (inRange(a, b, x) && (inRangeRay(c, d, x))) {
				res = 0;
			}
		}

		if (inRangeRay(c, d, intersect(n, new Line(a.x, a.y, a.x + n.a, a.y + n.b)))) {
			res = Math.min(dPointLine(c, d, a), res);
		}
		if (inRangeRay(c, d, intersect(n, new Line(b.x, b.y, b.x + n.a, b.y + n.b)))) {
			res = Math.min(dPointLine(c, d, b), res);
		}
		if (inRange(a, b, intersect(m, new Line(c.x, c.y, c.x + m.a, c.y + m.b)))) {
			res = Math.min(dPointLine(a, b, c), res);
		}

		res = Math.min(res, dist(a, c));
		res = Math.min(res, dist(b, c));

		return res;
	}

	public static double dSegmentLine(Point a, Point b, Point c, Point d) {
		Line m = new Line(a.x, a.y, b.x, b.y);
		Line n = new Line(c.x, c.y, d.x, d.y);

		Point x = intersect(n, m);

		double res = Double.MAX_VALUE;

		if (x != null) {
			if (inRange(a, b, x)) {
				res = 0;
			}
		}

		res = Math.min(dPointLine(c, d, a), res);
		res = Math.min(dPointLine(c, d, b), res);

		return res;
	}

	public static double dRayRay(Point a, Point b, Point c, Point d) {
		Line m = new Line(a.x, a.y, b.x, b.y);
		Line n = new Line(c.x, c.y, d.x, d.y);

		Point x = intersect(n, m);

		double res = Double.MAX_VALUE;

		if (x != null) {
			if (inRangeRay(a, b, x) && (inRangeRay(c, d, x))) {
				res = 0;
			}
		}

		if (inRangeRay(c, d, intersect(n, new Line(a.x, a.y, a.x + n.a, a.y + n.b)))) {
			res = Math.min(dPointLine(c, d, a), res);
		}
		if (inRangeRay(c, d, intersect(n, new Line(b.x, b.y, b.x + n.a, b.y + n.b)))) {
			res = Math.min(dPointLine(c, d, b), res);
		}
		if (inRangeRay(a, b, intersect(m, new Line(c.x, c.y, c.x + m.a, c.y + m.b)))) {
			res = Math.min(dPointLine(a, b, c), res);
		}
		if (inRangeRay(a, b, intersect(m, new Line(d.x, d.y, d.x + m.a, d.y + m.b)))) {
			res = Math.min(dPointLine(a, b, d), res);
		}

		res = Math.min(res, dist(a, c));

		return res;
	}

	public static double dRayLine(Point a, Point b, Point c, Point d) {
		Line m = new Line(a.x, a.y, b.x, b.y);
		Line n = new Line(c.x, c.y, d.x, d.y);

		Point x = intersect(n, m);

		double res = Double.MAX_VALUE;

		if (x != null) {
			if (inRangeRay(a, b, x)) {
				res = 0;
			}
		}

		res = Math.min(dPointLine(c, d, a), res);

		return res;
	}

	public static double dLineLine(Point a, Point b, Point c, Point d) {
		Line m = new Line(a.x, a.y, b.x, b.y);
		Line n = new Line(c.x, c.y, d.x, d.y);

		Point x = intersect(n, m);

		double res = Double.MAX_VALUE;

		if ((m.a == n.a) && (m.b == n.b)) {
			res = Math.abs(m.c - n.c) / Math.sqrt(m.a * m.a + m.b * m.b);
		} else {
			res = 0;
		}

		return res;
	}
}
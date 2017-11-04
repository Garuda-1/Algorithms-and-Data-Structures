
// Convex hull searching (Graham algorithm)
//
// Input format:
// n - number of points
// 1..n: x y - point coordinates
//
// Output format:
// m - number of points included in minimal convex hull
// 1..m: x y - coordinates of chosen points

import java.util.*;
import java.io.*;

public class Convex_Hull {
	FastScanner in;
	PrintWriter out;

	static long sx, sy;

	public void solve() {
		int n = in.nextInt();
		ArrayList<Point> a = new ArrayList<Point>();
		sx = Integer.MAX_VALUE;
		sy = Integer.MAX_VALUE;
		for (int i = 0; i < n; i++) {
			int x = in.nextInt();
			int y = in.nextInt();
			if (x < sx || (x == sx && y < sy)) {
				sx = x;
				sy = y;
			}
			a.add(new Point(x, y));
		}
		for (int i = 0; i < n; i++) {
			a.get(i).update();
		}

		Collections.sort(a);

		Deque<Point> res = new LinkedList<Point>();
		res.addLast(a.get(0));
		res.addLast(a.get(1));

		for (int i = 2; i < n; i++) {
			while (res.size() > 1) {
				Point p = res.pollLast();
				Point pp = res.pollLast();
				Point next = a.get(i);
				long xvp = p.x - pp.x;
				long yvp = p.y - pp.y;
				long xvn = next.x - p.x;
				long yvn = next.y - p.y;
				res.addLast(pp);
				if (vProd(xvp, yvp, xvn, yvn) > 0) {
					res.addLast(p);
					break;
				}
			}
			res.addLast(a.get(i));
		}
		
		out.println(res.size());
		
		while (!res.isEmpty()) {
			Point z = res.pollLast();
			out.println(z.x + " " + z.y);
		}
	}

	public long vProd(long x1, long y1, long x2, long y2) {
		return (x1 * y2) - (x2 * y1);
	}

	public long dist(long x1, long y1, long x2, long y2) {
		return (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2);
	}

	class Point implements Comparable<Point> {
		long x, y, d;
		double pol;

		public Point(int x, int y) {
			super();
			this.x = x;
			this.y = y;
			this.d = -1;
			this.pol = -1;
		}

		public void update() {
			this.d = dist(this.x, this.y, sx, sy);
			this.pol = Math.atan2(this.y - sy, this.x - sx);
		}

		public int compareTo(Point z) {
			if (x == sx && y == sy)
				return -1;
			if (z.x == sx && z.y == sy)
				return 1;
			if (pol < z.pol)
				return -1;
			if (pol > z.pol)
				return 1;
			if (d < z.d)
				return -1;
			if (d > z.d)
				return 1;
			return 0;
		}
	}

	public void run() {
		in = new FastScanner();
		out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		solve();
		out.close();
	}

	class FastScanner {
		StringTokenizer st;
		BufferedReader br;

		FastScanner() {
			br = new BufferedReader(new InputStreamReader(System.in));
		}

		String next() {
			while (st == null || !st.hasMoreTokens()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return st.nextToken();
		}

		int nextInt() {
			return Integer.parseInt(next());
		}

		long nextLong() {
			return Long.parseLong(next());
		}

		double nextDouble() {
			return Double.parseDouble(next());
		}
	}

	public static void main(String[] args) {
		new Convex_Hull().run();
	}
}

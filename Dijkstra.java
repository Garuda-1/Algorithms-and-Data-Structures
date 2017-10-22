
// Dijkstra's algorithm 
// 
// Input format:
// n - number of vertexes
// m - number of edges
// 1..m : f t w - info about edge (from, to, weight)
// q - number of queries
//
// Query protocol
// Input:
// f t - request shortest path from vertex f to vertex t
// Output:
// l - length of the shortest path or code of non-existing path (-1)
// a..b - vertex sequence representing path 

import java.util.*;
import java.io.*;

public class Dijkstra {
	FastScanner in;
	PrintWriter out;

	static final int inf = Integer.MAX_VALUE;
	static ArrayList<Edge> a[];
	static int n, m;

	public void solve() {
		n = in.nextInt();
		a = new ArrayList[n];
		for (int i = 0; i < n; i++) {
			a[i] = new ArrayList<Edge>();
		}

		m = in.nextInt();
		for (int i = 0; i < m; i++) {
			int f = in.nextInt() - 1;
			int t = in.nextInt() - 1;
			int w = in.nextInt();
			a[f].add(new Edge(t, w));
		}

		int q = in.nextInt();
		for (int i = 0; i < q; i++) {
			int f = in.nextInt() - 1;
			int t = in.nextInt() - 1;

			Dat x = override(f);

			if (x.d[t] == inf) {
				out.println(-1);
				break;
			}

			ArrayList<Integer> path = new ArrayList<Integer>();
			int cur = t;
			while (cur != f) {
				path.add(cur);
				cur = x.p[cur];
			}
			path.add(f);
			Collections.reverse(path);

			out.println(x.d[t]);
			for (int z : path) {
				out.print(z + 1 + " ");
			}
		}
	}

	public Dat override(int f) {
		PriorityQueue<Vertex> s = new PriorityQueue<Vertex>();
		boolean vis[] = new boolean[n];
		Dat res = new Dat(n);

		Arrays.fill(res.d, inf);
		Arrays.fill(res.p, -1);
		res.d[f] = 0;

		s.add(new Vertex(f, res.d[f]));

		while (!s.isEmpty()) {
			Vertex cur = s.poll();
			vis[cur.num] = true;

			if (cur.val == inf) {
				break;
			}

			for (Edge e : a[cur.num]) {
				if (!vis[e.to] && res.d[e.to] > res.d[cur.num] + e.w) {
					res.d[e.to] = res.d[cur.num] + e.w;
					res.p[e.to] = cur.num;
					s.add(new Vertex(e.to, res.d[e.to]));
				}
			}
		}

		return res;
	}

	class Dat {
		int d[], p[];

		public Dat(int n) {
			this.d = new int[n];
			this.p = new int[n];
		}
	}

	class Edge {
		int to, w;

		public Edge(int to, int w) {
			this.to = to;
			this.w = w;
		}
	}

	class Vertex implements Comparable<Vertex> {
		int num, val;

		public Vertex(int num, int val) {
			this.num = num;
			this.val = val;
		}

		public int compareTo(Vertex x) {
			if (val < x.val)
				return -1;
			if (val > x.val)
				return 1;
			if (num < x.num)
				return -1;
			if (num > x.num)
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
		new Dijkstra().run();
	}
}


// Ford-Bellman algorithm
//
// Input format:
// n - number of vertexes
// m - number of edges
// 1..m: f t w - edge info (from, to, weight)
// q - number of queries
//
// Query protocol
// Input:
// cmd (st)
//
// cmd = 0 - find distances from vertex st to others
// Value 2147483647 means that vertex is unreachable
// cmd = 1 - search for any negative cycle
//
// Output:
// cmd = 0
// d[] - distances from vertex st to others
// cmd = 1
// Search report

import java.util.*;
import java.io.*;

public class Ford_Bellman {
	FastScanner in;
	PrintWriter out;

	static final int inf = Integer.MAX_VALUE;
	static ArrayList<Edge> a[];
	static ArrayList<Integer> negaCycle;
	static int n, m;
	static boolean vis[];

	public void solve() {
		n = in.nextInt();
		m = in.nextInt();
		a = new ArrayList[n];
		for (int i = 0; i < n; i++) {
			a[i] = new ArrayList<Edge>();
		}

		for (int i = 0; i < m; i++) {
			int f = in.nextInt() - 1;
			int t = in.nextInt() - 1;
			int w = in.nextInt();
			a[f].add(new Edge(t, w));
		}

		int q = in.nextInt();
		for (int u = 0; u < q; u++) {
			int cmd = in.nextInt();
			if (cmd == 0) {
				int f = in.nextInt() - 1;
				vis = new boolean[n];
				int d[] = dist(f);
				if (negaCycle == null) {
					out.println("No negative cycle detected.");
					for (int i = 0; i < n; i++) {
						out.print(d[i] + " ");
					}
					out.println();
				} else {
					out.println("Negative cycle detected.");
					out.println("Length: " + negaCycle.size());
					for (int x : negaCycle) {
						out.print((x + 1) + " ");
					}
					out.println();
				}
			} else {
				boolean f = false;
				vis = new boolean[n];
				for (int i = 0; i < n; i++) {
					if (!vis[i] && !f) {
						dist(i);
						if (negaCycle != null) {
							out.println("Negative cycle detected.");
							out.println("Length: " + negaCycle.size());
							for (int x : negaCycle) {
								out.print((x + 1) + " ");
							}
							out.println();
							f = true;
						}
					}
				}
				if (!f) {
					out.println("No negatve cycle detected.");
				}
			}
		}
	}

	public int[] dist(int st) {
		int d[] = new int[n];
		int p[] = new int[n];
		Arrays.fill(d, inf);
		Arrays.fill(p, -1);
		d[st] = 0;
		vis[st] = true;

		int x = -1;
		for (int i = 0; i < n; i++) {
			x = -1;
			for (int j = 0; j < n; j++) {
				for (Edge e : a[j]) {
					if (d[j] != inf && d[j] + e.w < d[e.t]) {
						d[e.t] = d[j] + e.w;
						p[e.t] = j;
						x = e.t;
						vis[e.t] = true;
					}
				}
			}
		}

		if (x != -1) {
			negaCycle = new ArrayList<Integer>();
			for (int i = 0; i < n; i++) {
				x = p[x];
			}
			for (int y = x; negaCycle.size() == 0 || y != x; y = p[y]) {
				negaCycle.add(y);
			}
			Collections.reverse(negaCycle);
		} else {
			negaCycle = null;
		}

		return d;
	}

	class Edge {
		int t, w;

		public Edge(int t, int w) {
			super();
			this.t = t;
			this.w = w;
		}
	}

	public void run() {
		in = new FastScanner();
		out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		solve();
		out.close();
	}

	class FastScanner {
		BufferedReader br;
		StringTokenizer st;

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
		new Ford_Bellman().run();
	}
}

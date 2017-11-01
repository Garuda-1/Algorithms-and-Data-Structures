
// Floyd-Warshall algorithm
//
// Input format:
// n - number of vertexes
// m - number of edges
// 1..m: f t w - edge info (from, to, weight)
// q - number of queries
//
// Query protocol
// Input:
// f t - request info about shortest path between vertexes f and t
//
// Output:
// Path report

import java.util.*;
import java.io.*;

public class Floyd_Warshall {
	FastScanner in;
	PrintWriter out;

	final static int inf = Integer.MAX_VALUE;
	static int n, m, d[][], p[][];

	public void solve() {
		n = in.nextInt();
		m = in.nextInt();
		d = new int[n][n];
		p = new int[n][n];
		for (int i = 0; i < n; i++) {
			Arrays.fill(d[i], inf);
			Arrays.fill(p[i], i);
		}

		for (int i = 0; i < m; i++) {
			int f = in.nextInt() - 1;
			int t = in.nextInt() - 1;
			int w = in.nextInt();
			d[f][t] = w;
		}

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				for (int k = 0; k < n; k++) {
					if (d[i][k] != inf && d[k][j] != inf && d[i][j] > d[i][k] + d[k][j]) {
						d[i][j] = d[i][k] + d[k][j];
						p[i][j] = k;
					}
				}
			}
		}

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				for (int k = 0; k < n; k++) {
					if (d[i][k] != inf && d[k][j] != inf && d[k][k] < 0) {
						d[i][j] = -inf;
					}
				}
			}
		}

		int q = in.nextInt();
		for (int u = 0; u < q; u++) {
			int f = in.nextInt() - 1;
			int t = in.nextInt() - 1;
			if (d[f][t] == -inf) {
				out.println("No shortest path available.");
				out.println();
				continue;
			}
			ArrayList<Integer> path = new ArrayList<Integer>();
			for (int cur = t; cur != f; cur = p[f][cur]) {
				path.add(cur);
			}
			path.add(f);
			Collections.reverse(path);
			out.println("Shortest path length: " + d[f][t]);
			out.print("Path: ");
			for (int x : path) {
				out.print((x + 1) + " ");
			}
			out.println();
			out.println();
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
		new Floyd_Warshall().run();
	}
}

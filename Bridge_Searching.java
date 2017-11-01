
// Bridge searching
//
// Input:
// n - number of vertexes
// m - number of edges
// 1..m: f t - pair of connected by edge vertexes
// 
// Output:
// q - amount of bridges
// a[] - numbers of edges in input list which are bridges

import java.util.*;
import java.io.*;

public class Bridge_Searching {
	FastScanner in;
	PrintWriter out;

	static ArrayList<Edge> a[];
	static ArrayList<Integer> br;
	static int n, m, tin[], fup[], timer;
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
			a[f].add(new Edge(t, i));
			a[t].add(new Edge(f, i));
		}

		init();
		for (int i = 0; i < n; i++) {
			if (!vis[i])
				DFS(i, -1);
		}

		Collections.sort(br);
		out.println(br.size());
		for (int x : br) {
			out.print((x + 1) + " ");
		}
	}

	public void init() {
		tin = new int[n];
		fup = new int[n];
		vis = new boolean[n];
		br = new ArrayList<Integer>();
	}

	public void DFS(int v, int p) {
		vis[v] = true;
		tin[v] = fup[v] = timer++;
		for (Edge e : a[v]) {
			if (!vis[e.to]) {
				DFS(e.to, e.id);
				fup[v] = Math.min(fup[v], fup[e.to]);
				if (fup[e.to] > tin[v]) {
					br.add(e.id);
				}
			} else {
				if (e.id != p) {
					fup[v] = Math.min(fup[v], tin[e.to]);
				}
			}
		}
	}

	class Edge {
		int to, id;

		public Edge(int to, int id) {
			super();
			this.to = to;
			this.id = id;
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
		new Bridge_Searching().run();
	}
}

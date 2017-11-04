
// Cutpoint searching
//
// Input:
// n - number of vertexes
// m - number of edges
// 1..m: f t - pair of connected by edge vertexes
// 
// Output:
// q - amount of cutpoints
// a[] - numbers of vertexes which are cutpoints

import java.util.*;
import java.io.*;

public class Cutpoint_Searching {
	FastScanner in;
	PrintWriter out;

	static ArrayList<Edge> a[];
	static HashSet<Integer> cp;
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

		ArrayList<Integer> tmp = new ArrayList<Integer>();
		tmp.addAll(cp);
		Collections.sort(tmp);
		out.println(tmp.size());
		for (int x : tmp) {
			out.print((x + 1) + " ");
		}
	}

	public void init() {
		tin = new int[n];
		fup = new int[n];
		vis = new boolean[n];
		cp = new HashSet<Integer>();
	}

	public static void DFS(int v, int p) {
		vis[v] = true;
		fup[v] = tin[v] = timer++;
		int cnt = 0;
		for (Edge z : a[v]) {
			if (!vis[z.to]) {
				DFS(z.to, z.id);
				fup[v] = Math.min(fup[v], fup[z.to]);
				if ((tin[v] <= fup[z.to]) && (p != -1)) {
					cp.add(v);
				}
				cnt++;
			} else {
				if (z.id != p) {
					fup[v] = Math.min(fup[v], tin[z.to]);
				}
			}
		}
		if ((p == -1) && (cnt > 1))
			cp.add(v);
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
		new Cutpoint_Searching().run();
	}
}


// Max Flow (Ford-Fulkerson algorithm)
//
// Input format: 
// n - number of vertexes
// m - number of edges
// src - source vertex
// sink - sink vertex
// 1..m: f t c - add edge from 'f' to 't' with capacity 'c'
//
// Output format:
// flow - max flow value
// 1..2*m: f t lf/c - edge from 'f' to 't' with capacity 'c' is filled with flow of 'lf'

import java.util.*;
import java.io.*;

public class Max_Flow {
	FastScanner in;
	PrintWriter out;

	static final int inf = Integer.MAX_VALUE;
	static int n, m, src, sink;
	static ArrayList<Integer>[] network;
	static ArrayList<Edge> edges;
	static HashSet<Integer> wired[];
	static VerSet vis;

	public void solve() {
		n = in.nextInt();
		m = in.nextInt();

		src = in.nextInt();
		sink = in.nextInt();

		init();

		for (int i = 0; i < m; i++) {
			int f = in.nextInt() - 1;
			int t = in.nextInt() - 1;
			int c = in.nextInt();
			addEdge(f, t, c);
		}

		long res = 0;

		for (int i = 30; i >= 0; i--) {
			vis.clear();
			while (true) {
				long add = DFS(src, 1 << i);
				if (add == 0)
					break;
				res += add;
				vis.clear();
			}
		}

		vis.clear();
		out.println(res);
		// print();
	}

	public long DFS(int x, long flow) {
		vis.mark(x);
		if (x == sink)
			return flow;

		for (int to : network[x]) {
			Edge front = edges.get(to);
			Edge back = edges.get(to ^ 1);

			if (vis.get(front.to) || front.flow == front.cap) {
				continue;
			}

			long t_flow = DFS(front.to, Math.min(flow, front.cap - front.flow));
			if (t_flow != 0) {
				front.flow += t_flow;
				back.flow -= t_flow;
				return t_flow;
			}
		}

		return 0;
	}

	public void init() {
		vis = new VerSet(n);
		network = new ArrayList[n];
		wired = new HashSet[n];
		for (int i = 0; i < n; i++) {
			network[i] = new ArrayList<Integer>();
			wired[i] = new HashSet<Integer>();
		}
		edges = new ArrayList<Edge>();
	}

	public void addEdge(int f, int t, int c) {
		if (wired[f].contains(t)) {
			return;
		}
		network[f].add(edges.size());
		edges.add(new Edge(f, t, c));
		network[t].add(edges.size());
		edges.add(new Edge(t, f, 0));
		wired[f].add(t);
		wired[t].add(f);
	}

	public void print() {
		for (Edge e : edges) {
			out.println(e.from + " " + e.to + " " + e.flow + "/" + e.cap);
		}
	}

	class VerSet {
		int v[];
		int ver;

		public VerSet(int n) {
			v = new int[n];
			ver = 1;
		}

		public void mark(int i) {
			v[i] = ver;
		}

		public boolean get(int i) {
			return v[i] == ver;
		}

		public void clear() {
			ver++;
		}
	}

	class Edge {
		int to, from;
		long flow, cap;

		public Edge(int from, int to, long cap) {
			super();
			this.to = to;
			this.from = from;
			this.flow = 0;
			this.cap = cap;
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
		new Max_Flow().run();
	}
}

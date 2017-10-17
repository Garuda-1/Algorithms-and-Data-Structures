// Segment Tree
//
// Input format:
// n - array size
// a[] - array
// q - number of requests
// Request types:
// '+' l r d - add d to segment [l; r]
// '?' l r - request max at segment [l; r]

import java.util.*;
import java.io.*;

public class ST {
	FastScanner in;
	PrintWriter out;

	static int a[], n;
	static Node root;

	public void solve() {
		n = in.nextInt();
		a = new int[n];

		for (int i = 0; i < n; i++) {
			a[i] = in.nextInt();
		}

		root = new Node(0, n - 1);
		build(root);

		int q = in.nextInt();
		for (int i = 0; i < q; i++) {
			if (in.next().compareTo("+") == 0) {
				int l = in.nextInt() - 1;
				int r = in.nextInt() - 1;
				int d = in.nextInt();
				update(root, l, r, d);
			} else {
				int l = in.nextInt() - 1;
				int r = in.nextInt() - 1;
				out.println(get(root, l, r));
			}
		}
	}

	public int get(Node V, int l, int r) {
		if (l > r) {
			return Integer.MIN_VALUE;
		}
		if (V.tl == l && V.tr == r) {
			return V.cur + V.d;
		}
		return Math.max(get(V.L, l, Math.min(V.L.tr, r)), get(V.R, Math.max(V.R.tl, l), r)) + V.d;
	}

	public void update(Node V, int l, int r, int d) {
		if (l > r) {
			return;
		}
		if (V.tl == l && V.tr == r) {
			V.d += d;
			return;
		}
		V.L.d += V.d;
		V.R.d += V.d;
		V.d = 0;
		update(V.L, l, Math.min(V.L.tr, r), d);
		update(V.R, Math.max(V.R.tl, l), r, d);
		V.cur = Math.max(V.L.cur + V.L.d, V.R.cur + V.R.d);
	}

	public void build(Node V) {
		if (V.tl == V.tr) {
			V.cur = a[V.tl];
		} else {
			int tm = (V.tl + V.tr) / 2;
			V.L = new Node(V.tl, tm);
			V.R = new Node(tm + 1, V.tr);
			build(V.L);
			build(V.R);
			V.cur = Math.max(V.L.cur, V.R.cur);
		}
	}

	class Node {
		int cur, tl, tr, d;
		Node L, R;

		public Node(int tl, int tr) {
			super();
			this.cur = this.d = 0;
			this.tl = tl;
			this.tr = tr;
			this.L = this.R = null;
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
		new ST().run();
	}
}

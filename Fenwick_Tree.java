
// Fenwick Tree (Segment sum)
//
// Input format:
// n - array size
// a[] - array
// Query types:
// '!' i d - add d to a[i]
// '?' l r - get sum on segment [l; r]

import java.util.*;
import java.io.*;

public class Fenwick_Tree {
	FastScanner in;
	PrintWriter out;

	public void solve() {
		int n = in.nextInt();
		int a[] = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = in.nextInt();
		}

		FT tree = new FT(a);

		int q = in.nextInt();
		for (int i = 0; i < q; i++) {
			if (in.next().compareTo("!") == 0) {
				tree.add(in.nextInt() - 1, in.nextInt());
			} else {
				out.println(tree.get(in.nextInt() - 1, in.nextInt() - 1));
			}
		}
	}

	class FT {
		int t[];

		public FT(int a[]) {
			this.t = new int[a.length];
			for (int i = 0; i < a.length; i++) {
				add(i, a[i]);
			}
		}

		public void add(int ind, int d) {
			for (int i = ind; i < this.t.length; i = (i | (i + 1))) {
				this.t[i] += d;
			}
		}

		public int getPref(int ind) {
			int res = 0;
			for (int i = ind; i >= 0; i = (i & (i + 1)) - 1) {
				res += this.t[i];
			}
			return res;
		}

		public int get(int l, int r) {
			return getPref(r) - getPref(l - 1);
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
		new Fenwick_Tree().run();
	}
}


// Sparse Table (Sum)
//
// Input format:
// n - array size
// a[] - array
// Query types:
// l r - get sum on segment [l; r]

import java.util.*;
import java.io.*;

public class Sparse_Table {
	FastScanner in;
	PrintWriter out;

	static int n, a[], t[][];

	public void solve() {
		n = in.nextInt();
		a = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = in.nextInt();
		}
		
		init();

		int m = in.nextInt();
		for (int i = 0; i < m; i++) {
			int l = in.nextInt() - 1;
			int r = in.nextInt() - 1;
			out.println(get(l, r));
		}
	}

	public int get(int l, int r) {
		int len = r - l + 1;
		int x = log(len);
		return Math.max(t[x][l], t[x][r - pow(x) + 1]);
	}

	public void init() {
		t = new int[log(n) + 1][n];
		for (int i = 0; i < n; i++) {
			t[0][i] = a[i];
		}
		for (int i = 1; i < log(n) + 1; i++) {
			for (int j = 0; j < n - pow(i) + 1; j++) {
				t[i][j] = Math.max(t[i - 1][j], t[i - 1][j + pow(i - 1)]);
			}
		}
	}

	public int pow(int x) {
		return (1 << x);
	}

	public int log(int x) {
		int a = 1;
		int res = 0;
		while (a <= x) {
			a <<= 1;
			res++;
		}
		return res - 1;
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
		new Sparse_Table().run();
	}
}

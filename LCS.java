
// Longest common subsequence
//
// Input format:
// n - length of 1st sequence
// a[] - 1st sequence
// m - length of 2nd sequence
// b[] - 2nd sequence
//
// Output format:
// k - length of LCS
// res[] - LCS

import java.util.*;
import java.io.*;

public class LCS {
	FastScanner in;
	PrintWriter out;

	public void solve() {
		int n = in.nextInt();
		int a[] = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = in.nextInt();
		}

		int m = in.nextInt();
		int b[] = new int[m];
		for (int i = 0; i < m; i++) {
			b[i] = in.nextInt();
		}

		int d[][] = new int[n + 1][m + 1];
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= m; j++) {
				if (a[i - 1] == b[j - 1]) {
					d[i][j] = d[i - 1][j - 1] + 1;
				} else {
					d[i][j] = Math.max(d[i - 1][j], d[i][j - 1]);
				}
			}
		}

		out.println(d[n][m]);

		int res[] = new int[d[n][m]];
		int k = d[n][m] - 1;
		int i = n;
		int j = m;
		while (k >= 0) {
			if (a[i - 1] == b[j - 1]) {
				res[k] = a[i - 1];
				k--;
				i--;
				j--;
			} else {
				if (d[i - 1][j] == d[i][j]) {
					i--;
				} else {
					j--;
				}
			}
		}

		for (int u = 0; u < res.length; u++) {
			out.print(res[u] + " ");
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
		new LCS().run();
	}
}

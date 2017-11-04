
// Longest increasing subsequence
//
// Input format:
// n - length of sequence
// a[] - sequence
//
// Output format:
// k - length of LIS
// b[] - LIS (elements)

import java.util.*;
import java.io.*;

public class LIS {
	FastScanner in;
	PrintWriter out;

	static final int inf = Integer.MAX_VALUE;

	public void solve() {
		int n = in.nextInt();
		int a[] = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = in.nextInt();
		}

		int d[] = new int[n + 1];
		int di[] = new int[n + 1];
		int p[] = new int[n];
		Arrays.fill(d, inf);
		d[0] = -inf;

		int res = 0;

		for (int i = 0; i < n; i++) {
			int l = 0;
			int r = n;
			while (r - l > 1) {
				int m = (l + r) / 2;
				if (d[m] > a[i]) {
					r = m;
				} else {
					l = m;
				}
			}
			if (d[l] < a[i] && a[i] < d[r]) {
				d[r] = a[i];
				res = Math.max(res, r);
				p[i] = di[r - 1];
				di[r] = i;
			}
		}

		out.println(res);

		int lis[] = new int[res];
		int k = res;
		int x = di[k];
		while (k != 0) {
			lis[--k] = x;
			x = p[x];
		}

		for (int i = 0; i < res; i++) {
			out.print(a[lis[i]] + " ");
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
		new LIS().run();
	}
}

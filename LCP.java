
// Suffix array
//
// Input format:
// s - string to override
//
// Output format: 
// q - number of queries
// 1..q: i j - length of LCP of substrings starting at i and j

import java.util.*;
import java.io.*;

public class LCP {
	FastScanner in;
	PrintWriter out;

	static final int maxlen = 200000;
	static final int alphabet = 256;
	static int p[], c[][], log_n_d, log_n_u;

	public void solve() {
		String s = in.next();

		p = getSuffArr(s);

		int q = in.nextInt();
		for (int i = 0; i < q; i++) {
			System.out.println(lcp(in.nextInt() - 1, in.nextInt() - 1));
		}
	}

	public int lcp(int i, int j) {
		int ans = 0;
		for (int u = log_n_d; u >= 0; u--) {
			if (c[u][i] == c[u][j]) {
				ans += 1 << u;
				i += 1 << u;
				j += 1 << u;
			}
		}
		return ans;
	}

	public int[] getSuffArr(String s) {
		s += '$';
		int n = s.length();
		int tmp = 1;
		log_n_u = 0;
		while (tmp < n) {
			tmp <<= 1;
			log_n_u++;
		}
		log_n_d = log_n_u - 1;
		if ((1 << log_n_u) == n)
			log_n_d++;

		int p[] = new int[maxlen];
		c = new int[log_n_u + 1][maxlen];
		int cnt[] = new int[maxlen];

		for (int i = 0; i < n; i++) {
			cnt[s.charAt(i)]++;
		}
		for (int i = 1; i < alphabet; i++) {
			cnt[i] += cnt[i - 1];
		}
		for (int i = 0; i < n; i++) {
			p[--cnt[s.charAt(i)]] = i;
		}
		c[0][p[0]] = 0;
		int classes = 1;
		for (int i = 1; i < n; i++) {
			if (s.charAt(p[i]) != s.charAt(p[i - 1]))
				classes++;
			c[0][p[i]] = classes - 1;
		}

		int pn[] = new int[maxlen];
		int cn[] = new int[maxlen];
		for (int h = 0; (1 << h) < n; ++h) {
			for (int i = 0; i < n; i++) {
				pn[i] = p[i] - (1 << h);
				if (pn[i] < 0)
					pn[i] += n;
			}
			cnt = new int[maxlen];
			for (int i = 0; i < n; i++) {
				cnt[c[h][pn[i]]]++;
			}
			for (int i = 1; i < classes; i++) {
				cnt[i] += cnt[i - 1];
			}
			for (int i = n - 1; i >= 0; i--) {
				p[--cnt[c[h][pn[i]]]] = pn[i];
			}
			cn[p[0]] = 0;
			classes = 1;
			for (int i = 1; i < n; i++) {
				int mid1 = (p[i] + (1 << h)) % n;
				int mid2 = (p[i - 1] + (1 << h)) % n;
				if (c[h][p[i]] != c[h][p[i - 1]] || c[h][mid1] != c[h][mid2])
					classes++;
				cn[p[i]] = classes - 1;
			}
			for (int i = 0; i < maxlen; i++) {
				c[h + 1][i] = cn[i];
			}
		}

		return p;
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
		new LCP().run();
	}
}


// Suffix array
//
// Input format:
// s - string to override
//
// Output format: 
// p[] - suffix array

import java.util.*;
import java.io.*;

public class Suff_Arr {
	FastScanner in;
	PrintWriter out;

	static final int maxlen = 200000;
	static final int alphabet = 256;

	public void solve() {
		String s = in.next();

		int p[] = getSuffArr(s);
		for (int i = 0; i < s.length(); i++) {
			out.print(p[i] + " ");
		}
	}

	public int[] getSuffArr(String s) {
		int n = s.length();
		s += '$';

		int p[] = new int[maxlen];
		int c[] = new int[maxlen];
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
		c[p[0]] = 0;
		int classes = 1;
		for (int i = 1; i < n; i++) {
			if (s.charAt(p[i]) != s.charAt(p[i - 1]))
				classes++;
			c[p[i]] = classes - 1;
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
				cnt[c[pn[i]]]++;
			}
			for (int i = 1; i < classes; i++) {
				cnt[i] += cnt[i - 1];
			}
			for (int i = n - 1; i >= 0; i--) {
				p[--cnt[c[pn[i]]]] = pn[i];
			}
			cn[p[0]] = 0;
			classes = 1;
			for (int i = 1; i < n; i++) {
				int mid1 = (p[i] + (1 << h)) % n;
				int mid2 = (p[i - 1] + (1 << h)) % n;
				if (c[p[i]] != c[p[i - 1]] || c[mid1] != c[mid2])
					classes++;
				cn[p[i]] = classes - 1;
			}
			for (int i = 0; i < maxlen; i++) {
				c[i] = cn[i];
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
		new Suff_Arr().run();
	}
}

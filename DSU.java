
// Disjoint set union
//
// Input format:
// n - number of items
// q - number of queries
//
// Query protocol
// Input:
// cmd a b 
//
// cmd = '+' - unite sets, including items a and b
// cmd = '?' - check whether a and b are included in the same set
//
// Output:
// Yes/No - result for query type '?'

import java.util.*;
import java.io.*;

public class DSU {
	FastScanner in;
	PrintWriter out;

	static int rank[], par[];

	public void solve() {
		int n = in.nextInt();
		par = new int[n];
		rank = new int[n];

		for (int i = 0; i < n; i++) {
			initSet(i);
		}

		int q = in.nextInt();
		for (int u = 0; u < q; u++) {
			String cmd = in.next();
			int a = in.nextInt() - 1;
			int b = in.nextInt() - 1;
			if (cmd.compareTo("+") == 0) {
				uniteSet(a, b);
			} else {
				if (areUnited(a, b)) {
					out.println("Yes");
				} else {
					out.println("No");
				}
			}
		}
	}

	public boolean areUnited(int a, int b) {
		return (findSet(a) == findSet(b));
	}

	public void initSet(int x) {
		par[x] = x;
		rank[x] = 1;
	}

	public int findSet(int x) {
		if (par[x] == x)
			return x;
		return par[x] = findSet(par[x]);
	}

	public void uniteSet(int a, int b) {
		a = findSet(a);
		b = findSet(b);
		if (a == b)
			return;
		if (rank[a] < rank[b]) {
			int x = a;
			a = b;
			b = x;
		}
		par[b] = a;
		if (rank[a] == rank[b])
			rank[a]++;
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
		new DSU().run();
	}
}

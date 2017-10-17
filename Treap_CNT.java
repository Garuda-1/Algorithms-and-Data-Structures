
// Treap by count
//
// Input format:
// n - number of requests
// Request types:
// '+' x i - put x at the position i of array
// '<<' l r - move to front segment [l; r] 
// '><' l r - reverse segment [l; r]
// '!' - print array

import java.util.*;
import java.io.*;

public class Treap_CNT {
	FastScanner in;
	PrintWriter out;

	static Node root;

	public void solve() {
		int n = in.nextInt();
		root = null;

		for (int i = 0; i < n; i++) {
			String cmd = in.next();
			if (cmd.compareTo("+") == 0) {
				int x = in.nextInt();
				int pos = in.nextInt() - 1;
				root = insert(root, x, pos);
			}
			if (cmd.compareTo("<<") == 0) {
				int l = in.nextInt() - 1;
				int r = in.nextInt() - 1;
				root = moveFront(root, l, r);
			}
			if (cmd.compareTo("><") == 0) {
				int l = in.nextInt() - 1;
				int r = in.nextInt() - 1;
				root = reverse1(root, l, r);
			}
			if (cmd.compareTo("!") == 0) {
				printTree(root);
				out.println();
			}
		}
	}

	public void printTree(Node T) {
		if (T == null)
			return;
		printTree(T.L);
		out.print(T.x + " ");
		printTree(T.R);
	}

	public Node reverse1(Node T, int l, int r) {
		Pair tmp1 = split(T, r + 1);
		Pair tmp2 = split(T, l);
		tmp2.S = reverse2(tmp2.S);
		return merge(merge(tmp2.F, tmp2.S), tmp1.S);
	}

	public Node reverse2(Node T) {
		if (T == null)
			return null;
		reverse2(T.L);
		reverse2(T.R);
		Node x = T.L;
		T.L = T.R;
		T.R = x;
		return T;
	}

	public Node moveFront(Node T, int l, int r) {
		Pair tmp1 = split(T, r + 1);
		Pair tmp2 = split(tmp1.F, l);
		T = merge(merge(tmp2.S, tmp2.F), tmp1.S);
		return T;
	}

	public Node insert(Node T, int val, int pos) {
		Pair tmp = split(T, pos);
		T = merge(merge(tmp.F, new Node(val)), tmp.S);
		return T;
	}

	public Pair split(Node T, int k) {
		if (T == null)
			return new Pair(null, null);
		int lim = getCnt(T.L);
		if (k > lim) {
			Pair tmp = split(T.R, k - lim - 1);
			T.R = tmp.F;
			tmp.F = T;
			update(tmp.S);
			update(tmp.F);
			return tmp;
		} else {
			Pair tmp = split(T.L, k);
			T.L = tmp.S;
			tmp.S = T;
			update(tmp.S);
			update(tmp.F);
			return tmp;
		}
	}

	public Node merge(Node T1, Node T2) {
		if (T1 == null)
			return T2;
		if (T2 == null)
			return T1;
		if (T1.y > T2.y) {
			T1.R = merge(T1.R, T2);
			update(T1);
			return T1;
		} else {
			T2.L = merge(T1, T2.L);
			update(T2);
			return T2;
		}
	}

	class Pair {
		Node F, S;

		public Pair(Node f, Node s) {
			super();
			F = f;
			S = s;
		}
	}

	class Node {
		int x, y, cnt;
		Node L, R;

		public Node(int x) {
			super();
			this.x = x;
			this.cnt = 1;
			this.y = (int) (Math.random() * Integer.MAX_VALUE);
			this.L = this.R = null;
		}
	}

	public int getCnt(Node T) {
		if (T == null)
			return 0;
		else
			return T.cnt;
	}

	public void update(Node T) {
		if (T != null)
			T.cnt = getCnt(T.L) + getCnt(T.R) + 1;
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
		new Treap_CNT().run();
	}
}

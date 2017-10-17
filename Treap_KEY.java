
// Treap by key
//
// Input format:
// n - number of requests
// Request types:
// '+' x - add x to set
// '?' x - learn whether set contains x
// '<>' l r - get amount of number in range [l; r]

import java.util.*;
import java.io.*;

public class Treap_KEY {
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
				root = insert(root, x);
			}
			if (cmd.compareTo("?") == 0) {
				int x = in.nextInt();
				if (contains(root, x)) {
					out.println("YES");
				} else {
					out.println("NO");
				}
			}
			if (cmd.compareTo("<>") == 0) {
				int l = in.nextInt();
				int r = in.nextInt();
				out.println(amountInRange(root, l, r));
			}
		}
	}

	public int amountInRange(Node T, int l, int r) {
		Pair tmp1 = split(T, l);
		Pair tmp2 = split(tmp1.S, r + 1);
		int res = getCnt(tmp2.F);
		T = merge(merge(tmp1.F, tmp2.F), tmp2.S);
		return res;
	}

	public boolean contains(Node T, int val) {
		Pair tmp1 = split(T, val);
		Pair tmp2 = split(tmp1.S, val + 1);
		boolean res = tmp2.F != null;
		T = merge(merge(tmp1.F, tmp2.F), tmp2.S);
		return res;
	}

	public Node insert(Node T, int val) {
		Pair tmp = split(T, val);
		T = merge(merge(tmp.F, new Node(val)), tmp.S);
		return T;
	}

	public Pair split(Node T, int k) {
		if (T == null)
			return new Pair(null, null);
		if (T.x < k) {
			Pair tmp = split(T.R, k);
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
		new Treap_KEY().run();
	}
}

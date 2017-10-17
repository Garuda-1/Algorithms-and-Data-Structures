
// Bohr
//
// Input format:
// n - number of requests
// Request types
// '+' s - add string s to dictionary
// '?' s - check if dictionary contains s
// WARNING: use only uppercase latin characters in a query string

import java.util.*;
import java.io.*;

public class Bohr {
	FastScanner in;
	PrintWriter out;

	static int k = 26;
	static Node root;

	public void solve() {
		int n = in.nextInt();
		root = new Node();
		for (int i = 0; i < n; i++) {
			if (in.next().compareTo("+") == 0) {
				add(in.next());
			} else {
				if (contains(in.next())) {
					out.println("YES");
				} else {
					out.println("NO");
				}
			}
		}
	}

	public boolean contains(String s) {
		Node cur = root;
		for (int i = 0; i < s.length(); i++) {
			if (cur.next[f(s.charAt(i))] == null) {
				return false;
			}
			cur = cur.next[f(s.charAt(i))];
		}
		return cur.t;
	}

	public void add(String s) {
		Node cur = root;
		for (int i = 0; i < s.length(); i++) {
			if (cur.next[f(s.charAt(i))] == null) {
				cur.next[f(s.charAt(i))] = new Node();
			}
			cur = cur.next[f(s.charAt(i))];
		}
		cur.t = true;
	}

	public int f(char x) {
		return x - 'A';
	}

	class Node {
		boolean t;
		Node next[];

		public Node() {
			super();
			this.t = false;
			this.next = new Node[k];
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
		new Bohr().run();
	}
}

import java.util.*;
import java.io.*;

public class FS {
	FastScanner in;
	PrintWriter out;
	
	public void solve() {
		
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
		
		FastScanner () {
			br = new BufferedReader(new InputStreamReader(System.in));
		}
		
		String next() {
			while (st == null || !st.hasMoreTokens()) {
				try {
					st = new StringTokenizer (br.readLine());
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
	
	public static void main (String[] args) {
		new FS().run();
	}
}

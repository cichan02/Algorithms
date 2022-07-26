package task0.task1;

import task0.*;

import java.io.*;

public class Solution {
	public static void main(String[] args) throws IOException {
		FastScanner scanner = new FastScanner("input.txt");
		int n = scanner.nextInt();
		int q = scanner.nextInt();
		
		DSU dsu = new DSU(n);
		
		PrintWriter writer = new PrintWriter("output.txt");
		
		for(int i = 0; i < q; i++) {
			int u = scanner.nextInt();
			int v = scanner.nextInt();
			dsu.union(u, v);
			writer.println(dsu.getRootNumber());
		}
		
		writer.close();
	}
}

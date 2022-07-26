package task0.task2;

import task0.*;

import java.util.*;
import java.io.*;

public class Solution {
	public static void main(String[] args) throws IOException {
		FastScanner scanner = new FastScanner("input.txt");
		int n = scanner.nextInt();
		int m = scanner.nextInt();
		int q = scanner.nextInt();
		
		int[][] roads = new int[m][2];
		for(int i = 0; i < m; i++) {
			for(int j = 0; j < 2; j++) {
				roads[i][j] = scanner.nextInt();
			}
		}
		
		Stack<Integer> quake = new Stack<>();
		for(int i = 0; i < q; i++) {
			quake.add(scanner.nextInt() - 1);
		}
		
		DSU dsu = new DSU(n);
		
		Set<Integer> quickQuake = new HashSet<>(quake);
		for(int i = 0; i < m; i++) {
			if(!quickQuake.contains(i)) {
				dsu.union(roads[i][0], roads[i][1]);
			}
		}
		quickQuake = null;
		
		byte[] answer = new byte[q];
		for(int i = 0; i < q; i++) {
			answer[q-i-1] = dsu.getRootNumber() == 1 ? (byte)1 : 0;
			int k = quake.pop();
			dsu.union(roads[k][0], roads[k][1]);
		}
		
		PrintWriter writer = new PrintWriter("output.txt");
		for(int i = 0; i < q; i++) {
			writer.print(answer[i]);
		}
		writer.close();
	}
}

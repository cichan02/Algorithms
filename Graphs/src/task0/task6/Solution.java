package task0.task6;

import task0.*;

import java.util.*;
import java.io.*;

public class Solution {
	public static void main(String[] args) throws IOException {
		FastScanner scanner = new FastScanner("input.txt");
		int n = scanner.nextInt();
		int m = scanner.nextInt();
		
		List<List<Integer>> adjacList = new ArrayList<>(n);
		
		for(int i = 0; i < n; i++) {
			adjacList.add(new LinkedList<>());
		}
		
		for(int i = 0; i < m; i++) {
			int v = scanner.nextInt();
			int u = scanner.nextInt();
			
			adjacList.get(v-1).add(u);
			adjacList.get(u-1).add(v);
		}
		
		PrintWriter writer = new PrintWriter("output.txt");
		for(int i = 0; i < n; i++) {
			List<Integer> adjacSet = adjacList.get(i);
			writer.print(adjacSet.size());
			adjacSet.stream().forEach(num -> writer.print(" " + num));
			writer.println();
		}	
		writer.close();
	}
}

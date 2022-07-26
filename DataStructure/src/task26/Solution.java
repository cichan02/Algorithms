package task26;

import java.util.*;
import java.io.*;

public class Solution {
	public static void main(String[] args) throws IOException {
		FastScanner scanner = new FastScanner("input.txt");
		int m = scanner.nextInt();
		int n = scanner.nextInt();
		
		int[] a = new int[m];
		for(int i = 0; i < m; i++) {
			int element = scanner.nextInt();
			a[i] =  element;
		}
	
		int[] u = new int[n];
		for(int i = 0; i < n; i++) {
			int element = scanner.nextInt();
			u[i] = element;
		}
		
		Queue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
		Queue<Integer> minHeap = new PriorityQueue<>();
		
		PrintWriter writer = new PrintWriter("output.txt");
		
		int j = 0;
		int i = 0;
		while(j < n) {
			if(maxHeap.size() + minHeap.size() == u[j]) {
				if(j == 0)
					writer.print(maxHeap.peek());
				else
					writer.print(" " + maxHeap.peek());
				j++;
				if(!minHeap.isEmpty())
					maxHeap.add(minHeap.remove());
			}
			else {		
				if(maxHeap.size() > j) {
					if(a[i] < maxHeap.peek()) {
						minHeap.add(maxHeap.remove());
						maxHeap.add(a[i]);
					}
					else {
						minHeap.add(a[i]);
					}
				}
				else {
					maxHeap.add(a[i]);
				}
				i++;
			}
		}
		
		writer.close();
	}
}

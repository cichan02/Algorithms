package task19;

import static java.lang.Math.abs;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import task0.FastScanner;

public class Solution {
	private static int n;
	private static int[] x;
	private static int[] y;
	
	private static void init() {
		FastScanner scanner = new FastScanner("input.txt");
		n = scanner.nextInt();
		
		x = new int[n];
		y = new int[n];
		
		for(int i = 0; i < n; i++) {
			x[i] = scanner.nextInt();
			y[i] = scanner.nextInt();
		}
	}
	
	private static long distance(int firstCity, int secondCity) {
		return abs(x[firstCity] - x[secondCity]) + abs(y[firstCity] - y[secondCity]);
	}
	
	public static void setMaxValue(long[] mass) {
		for(int i = 0; i < mass.length; i++) {
			mass[i] = Long.MAX_VALUE;
		}
	}
	
	private static long sumDistance(int[] answer) {
		long sum = 0;
		for(int i = 1; i < answer.length; i++) {
			sum += distance(answer[i-1], answer[i]);
		}
		return sum;
	}
	
	private static void printAnswer(long distance,int[] mass) {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter("output.txt");
			for(int i = 0; i < mass.length; i++) {
				writer.print(mass[i]+1 + " ");
			}
			writer.println();
			writer.println(distance);
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			writer.close();
		}
	}
	
	public static void main(String[] args) {
		init();
		
		boolean[] visited = new boolean[n];
		long[] minDist = new long[n];
		setMaxValue(minDist);
		
		int[] answer = new int[n+1];
		for(int i = 0; i < n; i++) {
			int u = -1;
			for(int j = 0; j < n; j++) {
				if(!visited[j] && (u == -1 || minDist[j] < minDist[u])) {
					u = j;
				}
			}
			
			visited[u] = true;
			answer[i] = u;
			
			for(int j = 0; j < n; j++) {
				if(u == j) continue;
				long dist = distance(u, j);
				if(dist < minDist[j]) minDist[j] = dist;
			}
		}
		answer[n] = answer[0];
		
		long distance = sumDistance(answer);
		printAnswer(distance, answer);
	}
}

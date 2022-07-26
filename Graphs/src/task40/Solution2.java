package task40;

import java.io.*;

import java.util.stream.IntStream;

import task0.FastScanner;

public class Solution2 {
	private static short N;
	private static int[][] a;

	private static void initA(int[][] a) {
		IntStream.range(1, a.length)
				 .forEach(i -> IntStream.range(1, a[i].length)
						 				.forEach(j -> a[i][j] = Short.MAX_VALUE));
	}
	
	public static void init() {
		FastScanner scanner = new FastScanner("input.txt");
		N = scanner.nextShort();
		
		a = new int[N+1][N+1];
		initA(a);
		
		for(short i = 1; i <= N; i++) {
			short k = scanner.nextShort();
			
			for(short l = 0; l < k; l++) {
				short j = scanner.nextShort();
				short risk = scanner.nextShort();
				
				a[i][j] = risk;
			}
		}
	}
	
	private static void setInf(int[] massiv) {
		IntStream.range(1, massiv.length)
				 .forEach(i -> massiv[i] = Short.MAX_VALUE);
	}
	
	private static int[] hungarin() {
		int[] u = new int[N+1];
		int[] v = new int[N+1];
		int[] p = new int[N+1];
		int[] way = new int[N+1];
		
		for (int i=1; i<=N; ++i) {
			p[0] = i;
			int j0 = 0;
			int[] minv = new int[N+1];
			setInf(minv);
			boolean[] used  = new boolean[N+1];
			do {
				used[j0] = true;
				int i0 = p[j0], delta = Short.MAX_VALUE,  j1 = 0;
				for (int j = 1; j <= N; j++) {
					if (!used[j]) {
						int cur = a[i0][j]-u[i0]-v[j];
						if (cur < minv[j]) {
							minv[j] = cur;
							way[j] = j0;
						}
						if (minv[j] < delta) {
							delta = minv[j];  
							j1 = j;
						}
					}
				}
				for (int j = 0; j <= N; j++) {
					if (used[j]) {
						u[p[j]] += delta;
						v[j] -= delta;
					}
					else {
						minv[j] -= delta;
					}
				}
				j0 = j1;
			} while (p[j0] != 0);
			do {
				int j1 = way[j0];
				p[j0] = p[j1];
				j0 = j1;
			} while (j0 != 0);
		}
		
		return p;
	}
	
	private static void printAnswer(int[] p) {
		int[] ans = new int[N+1];
		for (int j = 1; j <= N; j++) {
			ans[p[j]] = j;
		}
		
		try {
			PrintWriter writer = new PrintWriter("output.txt");
			int sum = 0;
			for(int i = 1; i <= N; i++) {
				if(a[i][ans[i]] == Short.MAX_VALUE) {
					writer.println(-1);
					writer.close();
					return;
				}
				sum += a[i][ans[i]];
			}
			writer.println(sum);
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}	
	}
	
	public static void main(String[] args) {
		init();
		int[] p = hungarin();
		printAnswer(p);	
	}
}

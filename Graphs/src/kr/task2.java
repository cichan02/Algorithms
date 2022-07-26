package kr;

import java.io.*;
import java.util.*;

import task0.FastScanner;

public class task2 {
	public static void main(String[] args) throws IOException {
		FastScanner sc = new FastScanner("input.txt");
		byte n = sc.nextByte();
		
		DSU dsu = new DSU(n);
		
		byte[][] m = new byte[n][n];
		for(byte i = 0; i < n; i++) {
			for(byte j = 0; j < n; j++) {
				m[i][j] = sc.nextByte();
			}
		}
		
		List<List<Byte>> edges = new ArrayList<>();
		
		for(byte i = 1; i < n; i++) {
			for(byte j = 0; j < i; j++) {
				byte edge = m[i][j];
				if(edge == 0) continue;
				
				int u = i + 1;
				int v = j + 1;
				
				if(dsu.findSet(u) == dsu.findSet(v)) continue;
				
				dsu.union(u, v);
				
				edges.add(Arrays.asList((byte)u, (byte)v));
			}
		}
		
		PrintWriter writer = new PrintWriter("output.txt");
		if(dsu.getRootNumber() == 1) {
			int size = edges.size();
			writer.println(size);
			for(int i = 0; i < size; i++) {
				for(byte j = 0; j < 2; j++) {
					writer.print(edges.get(i).get(j) + " ");
				}
				writer.println();
			}
		}
		else {
			writer.print(-1);
		}
		writer.close();
	}
}

package task0.task8;

import task0.*;

import java.io.*;
import java.util.*;

public class Solution {
	public static byte bfs(byte start, byte index, byte n,
							boolean[] visited, byte[] answer, byte[][] graphMatrix) {
		Deque<Byte> deq = new ArrayDeque<>();
		
		visited[start] = true;
		answer[start] = index++;
		deq.addLast(start);
		
		while(!deq.isEmpty()) {
			byte v = deq.removeFirst();
			
			for (byte i = 0; i < n; i++) {
				if(graphMatrix[v][i] == 1 && !visited[i]) {
					visited[i] = true;
					answer[i] = index++;
					deq.addLast(i);
				}
			}
		}
		
		return index;
	}
	
	public static void main(String[] args) throws IOException {
		FastScanner scanner = new FastScanner("input.txt");
		
		byte n = scanner.nextByte();
		
		byte[][] graphMatrix = new byte[n][n];
		for(byte i = 0; i < n; i++) {
			for(byte j = 0; j < n; j++) {
				graphMatrix[i][j] = scanner.nextByte();
			}
		}
		
		boolean[] visited = new boolean[n];
		byte[] answer = new byte[n];
		
		byte index = 1;
		for(byte i = 0; i < n; i++) {
			if(!visited[i]) {
				index = bfs(i, index, n, visited, answer, graphMatrix);
			}
		}
		
		PrintWriter writer = new PrintWriter("output.txt");
		for(byte i = 0; i < n; i++) {
			writer.print(answer[i] + " ");
		}
		writer.close();
	}
}

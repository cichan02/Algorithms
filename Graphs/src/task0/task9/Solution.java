package task0.task9;

import task0.*;

import java.io.*;

public class Solution {
	private static byte dfs(byte v, byte index, byte n, boolean[] visited, byte[] answer, byte[][] graphMatrix) {
		visited[v] = true;
		answer[v] = index++;
		
		for(byte i = 0; i < n; i++) {
			if(graphMatrix[v][i] == 1 && !visited[i]) {
				index = dfs(i, index, n, visited, answer, graphMatrix);
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
		for(byte i = 0; i < n ; i++) {
			if(!visited[i]) {
				index = dfs(i, index, n, visited, answer, graphMatrix);
			}
		}
		
		PrintWriter writer = new PrintWriter("output.txt");
		for(byte i = 0; i < n; i++) {
			writer.print(answer[i] + " ");
		}
		writer.close();
	}
}

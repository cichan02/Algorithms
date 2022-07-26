package task0.task11;

import java.util.*;
import java.io.*;

import task0.FastScanner;

public class Solution {
	private static int n;
	private static int m;
	private static List<List<Integer>> incidList = new ArrayList<>();
	private static List<Edge> edges = new ArrayList<>();
	
	public static void init() {
		FastScanner scanner = new FastScanner("input.txt");
		n = scanner.nextInt();
		m = scanner.nextInt();
		
		for(int i = 0; i < n; i++) {
			incidList.add(new LinkedList<>());
		}
		
		for(int i = 0; i < m; i++) {
			int u = scanner.nextInt() - 1;
			int v = scanner.nextInt() - 1;
			int c = scanner.nextInt();
			
			edges.add(new Edge(u, v, c, 0)); 
			edges.add(new Edge(v, u, 0, 0));
			
			incidList.get(u).add(2*i);
			incidList.get(v).add(2*i + 1);
		}
	}
	
	public static void printAnswer() {
		PrintWriter writer = new PrintWriter(System.out);
		long answer = 0;
		for(int edgeNumber: incidList.get(0)) {
			answer += edges.get(edgeNumber).getFlow();
		}
		writer.print(answer);
		writer.close();
	}
	
	private static void dfs(int u, int edgeFrom, boolean[] visited, int[] prev) {
		visited[u] = true;
		prev[u] = edgeFrom;
	
		for(int edgeNumber: incidList.get(u)) {
			Edge edge = edges.get(edgeNumber);
			int v = edge.getTo();
			if(edge.available() > 0 && !visited[v]) {
				dfs(v, edgeNumber, visited, prev);
			}
		}
	}
	
	private static void bfs(int start, int edgeFrom, boolean[] visited, int[] prev) {
		Deque<Integer> deq = new ArrayDeque<>();
		visited[start] = true;
		prev[start] = edgeFrom;
		deq.addLast(start);
		
		while(!deq.isEmpty()) {
			int u = deq.removeFirst();
			
			for(int edgeNumber: incidList.get(u)) {
				Edge edge = edges.get(edgeNumber);
				int v = edge.getTo();
				if(edge.available() > 0 && !visited[v]) {
					visited[v] = true;
					prev[v] = edgeNumber;
					deq.addLast(v);
				}
			}
		}
	}
	
	private static int[] augmentPath1(int u) {
		boolean[] visited = new boolean[n];
		int[] prev = new int[n];
		for(int i = 0; i < n; i++) {
			prev[i] = -1;
		}
		
		dfs(u, -1, visited, prev);
		
		return prev;
	}
	
	private static int[] augmentPath2(int u) {
		boolean[] visited = new boolean[n];
		int[] prev = new int[n];
		for(int i = 0; i < n; i++) {
			prev[i] = -1;
		}
		
		bfs(u, -1, visited, prev);
		
		return prev;
	}
	
	private static List<Integer> restorePath(int v, int[] prev) {
		List<Integer> augmentPath = new ArrayList<>(n-1);
		while(prev[v] != -1) {
			augmentPath.add(prev[v]);
			v = edges.get(prev[v]).getFrom();
		}
		return augmentPath;
	}

	private static int residualCap(List<Integer> augmentPath) {
		int min = Integer.MAX_VALUE;
		for(int edgeNumber: augmentPath) {
			int c = edges.get(edgeNumber).available();
			min = Math.min(min, c);
		}
		return min;
	}
	
	private static void push(int edgeNumber, int residualCap) {
		Edge edge = edges.get(edgeNumber);
		edge.increaseFlow(residualCap);
		
		Edge reverseEdge = edges.get(edgeNumber^1);
		reverseEdge.decreaseFlow(residualCap);
	}
	
	private static void pushPath(List<Integer> augmentPath, int residualCap) {
		for(int edgeNumber: augmentPath) {
			push(edgeNumber, residualCap);
		}
	}
	
	public static void fordFulkerson() {
		while(true) {
			int[] prev = augmentPath1(0);
			if(prev[n-1] == -1) break;
			
			List<Integer> augmentPath = restorePath(n-1, prev);
			int residualCap = residualCap(augmentPath);
			pushPath(augmentPath, residualCap);	
		}
	}

	public static void edmondsKarp() {
		while(true) {
			int[] prev = augmentPath2(0);
			if(prev[n-1] == -1) break;
			
			List<Integer> augmentPath = restorePath(n-1, prev);
			int residualCap = residualCap(augmentPath);
			pushPath(augmentPath, residualCap);	
		}
	}
	
	public static void main(String[] args) {
		init();
		fordFulkerson();
		//or edmondsKarp();
		printAnswer();
	}
}
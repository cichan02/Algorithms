package task40;

import java.io.*;
import java.util.*;
import java.time.*;

import java.util.stream.IntStream;

import task0.FastScanner;
import task0.task11.Edge;

public class Solution {
	private static short N;
	private static List<List<Integer>> incidList = new ArrayList<>();
	private static List<Edge2> edges = new ArrayList<>();
	
	public static void init() {
		FastScanner scanner = new FastScanner("input.txt");
		N = scanner.nextShort();
		
		for(int i = 0; i < 2*N; i++) {
			incidList.add(new LinkedList<>());
		}
		
		int edgeNumber = 0;
		
		for(short i = 0; i < N; i++) {
			short k = scanner.nextShort();
			for(short j = 0; j < k; j++) {
				short day = (short)(scanner.nextShort() - 1 + N);
				short risk = scanner.nextShort();
				
				edges.add(new Edge2(i, day, 1, 0, risk));
				edges.add(new Edge2(day, i, 0, 0, risk));
				
				incidList.get(i).add(edgeNumber*2);
				incidList.get(day).add(edgeNumber*2+1);
				
				edgeNumber++;
			}
		}
	}
	
	private static void addSource() {
		int edgeNumber = edges.size();
		
		incidList.add(new LinkedList<>());
		
		for(short i = 0; i < N; i++) {
			edges.add(new Edge2(2*N, i, 1, 0, 0));
			edges.add(new Edge2(i, 2*N, 0, 0, 0));
			
			incidList.get(2*N).add(edgeNumber++);
			incidList.get(i).add(edgeNumber++);
			
		}
	}
	
	private static void addSink() {
		int edgeNumber = edges.size();
		
		incidList.add(new LinkedList<>());
		
		for(short i = 0; i < N; i++) {
			edges.add(new Edge2(i+N, 2*N+1, 1, 0, 0));
			edges.add(new Edge2(2*N+1, i+N, 0, 0, 0));
			
			incidList.get(i+N).add(edgeNumber++);
			incidList.get(2*N+1).add(edgeNumber++);
		}
	}
	
	private static void buildFlowNetwork() {
		addSource();
		addSink();
	}
	
	private static void setNullPrevious(int[] prev) {
		IntStream.range(0, 2*N+2)
				 .forEach(i -> prev[i] = -1);
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
	
	private static int[] augmentPath(int u) {
		boolean[] visited = new boolean[2*N+2];
		int[] prev = new int[2*N+2];
		setNullPrevious(prev);
		
		dfs(u, -1, visited, prev);
		
		return prev;
	}
	
	private static List<Integer> restorePath(int v, int[] prev) {
		List<Integer> augmentPath = new ArrayList<>(3);
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
			int[] prev = augmentPath(2*N);
			if(prev[2*N+1] == -1) break;
			
			List<Integer> augmentPath = restorePath(2*N+1, prev);
			int residualCap = residualCap(augmentPath);
			pushPath(augmentPath, residualCap);
		}
	}

	private static boolean isPerfect() {
		int sum = incidList.get(2*N).stream()
									.mapToInt(edgeNumber -> edges.get(edgeNumber).getFlow())
									.sum();
		return sum == N;
	}
	
	private static void destroyFlowNetwork() {
		for(int i = 0; i < 2*N; i++) {
			int last = incidList.get(i).size() - 1;
			incidList.get(i).remove(last);
		}
		incidList.remove(2*N+1);
		incidList.remove(2*N);
		
		for(int i = 0; i < 4*N; i++) {
			int last = edges.size() - 1;
			edges.remove(last);
		}
	}
	
	private static void setEdgeCost() {
		for(int i = 0; i < edges.size(); i+=2) {
			Edge2 edge = edges.get(i);
			if(edge.available() == 0) {
				edge.changeCostSign();
			}
		}
	}
	
	private static void setDist(int[] dist) {
		IntStream.range(0, dist.length / 2)
				 .forEach(i -> dist[i] = 0);
		IntStream.range(dist.length / 2, dist.length)
				 .forEach(i -> dist[i] = 0);
	}
	
	private static void setPreviousNull(int[] massiv) {
		IntStream.range(0, massiv.length)
				 .forEach(i -> massiv[i] = -1);
	}
	
	private static boolean edgeRound(boolean flagValue, int[] prev, int[] dist) {
		boolean flag = false;
		
		for(int j = 0; j < edges.size(); j+=2) {
			Edge2 edge = edges.get(j);
			int from = edge.getFrom();
			int to = edge.getTo();
			int c = edge.getCost();
			
			if(edge.available() > 0) {
//				swap
				int temp = from;
				from = to;
				to = temp;
			}
			
			if(dist[to] > dist[from] + c && dist[from] != Integer.MAX_VALUE) {
				flag = flagValue;
				if(flag) {
					prev[2*N] = j;
//					return flag;
				}
				dist[to] = dist[from] + c;
				prev[to] = j;
			}
		}
		
		return flag;
	}
	
	private static Pair fordBellman() {
		int[] dist = new int[2*N];
		int[] prev = new int[2*N+1];
		setDist(dist);
		setPreviousNull(prev);
		prev[2*N] = -2;
		
		boolean flag;
		
		for(int i = 0; i < 2*N-1; i++) {
			flag = edgeRound(false, prev, dist);
		}
//		n-th fordBellman, where n = 2*N
		flag = edgeRound(true, prev, dist);
		
		return new Pair(flag, prev);
	}
	
	private static int startVertex(int u, int[] prev) {
		Set<Integer> set = new HashSet<>();
		set.add(u);
		Edge2 edge = edges.get(u);
		int v = edge.available() > 0 ? edge.getTo() : edge.getFrom();
		while(!set.contains(prev[v])) {
			set.add(prev[v]);
			edge = edges.get(prev[v]);
			v = edge.available() > 0 ? edge.getTo() : edge.getFrom();
		}
		return prev[v];
	}
	
	private static List<Integer> restoreCicle(int start, int[] prev) {
		List<Integer> cicle = new ArrayList<>(2*N);		
		cicle.add(start);
		Edge2 edge = edges.get(start);
		int v = edge.available() > 0 ? edge.getTo() : edge.getFrom();
		while(prev[v] != start) {
			cicle.add(prev[v]);
			edge = edges.get(prev[v]);
			v = edge.available() > 0 ? edge.getTo() : edge.getFrom();
		}
		return cicle;
	}	
	
	private static void swithEdges(List<Integer> cicle) {
		for(int edgeNumber: cicle) {
			Edge2 edge = edges.get(edgeNumber);
			if(edge.available() > 0) {
				push(edgeNumber, 1);
			}
			else {
				push(edgeNumber^1, 1);
			}
			
			edge.changeCostSign();		
		}
	}
	
	private static void minimizingCost() {	
		setEdgeCost();
		
		while(true) {
			Pair pair = fordBellman();
			if(!pair.first()) break;
			int[] prev = pair.second();
			int start = startVertex(prev[2*N], prev);
			List<Integer> cicle = restoreCicle(start, prev);
			swithEdges(cicle);
		}
	}

	private static int countSum() {
		int sum = 0;
		for(int i = 0; i < N; i++) {
			for(int edgeNumber: incidList.get(i)) {
				int c = edges.get(edgeNumber).getCost();
				if(c <= 0) {
					sum -= c;
				}
			}
		}
		return sum;
	}

	public static void printAnswer(boolean flag) {
		try {
			PrintWriter writer = new PrintWriter("output.txt");	
			int answer = !flag ? -1 : countSum();
			writer.print(answer);
			writer.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		LocalTime beginTime = LocalTime.now();
		init();	
		System.out.println("init: " + Duration.between(beginTime, LocalTime.now())); 
		
		beginTime = LocalTime.now();
		buildFlowNetwork();
		System.out.println("buildFlowNetwork: " + Duration.between(beginTime, LocalTime.now()));
		
		beginTime = LocalTime.now();
		fordFulkerson();
		System.out.println("fordFulkerson: " + Duration.between(beginTime, LocalTime.now()));
		
		beginTime = LocalTime.now();
		if(!isPerfect()) {
			printAnswer(false);
			return;
		}
		System.out.println("isPerfect: " + Duration.between(beginTime, LocalTime.now()));
		
		beginTime = LocalTime.now();
		destroyFlowNetwork();
		System.out.println("destroyFlowNetwork: " + Duration.between(beginTime, LocalTime.now()));
		
		beginTime = LocalTime.now();
		minimizingCost();
		System.out.println("minimizingCost: " + Duration.between(beginTime, LocalTime.now()));
		
		printAnswer(true);
	}
}

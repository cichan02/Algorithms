package task49;

import java.util.*;
import java.io.*;

import task0.FastScanner;

public class Solution {
	private static List<Road> roads = new ArrayList<>();
	private static List<Integer> uselessRoads = new ArrayList<>();
	private static int n;
	private static int m;
	
	public static void init() {
		FastScanner scanner = new FastScanner("input.txt");
		n = scanner.nextInt();
		m = scanner.nextInt();
		
		for(int i = 0; i < m; i++) {
			int u = scanner.nextInt() - 1;
			int v = scanner.nextInt() - 1;
			int roadType = scanner.nextInt();
			
			roads.add(new Road(u, v, RoadType.getRoad(roadType)));
		}
	}

 	public static void printAnswer(boolean flag) {
		try {
			PrintWriter writer = new PrintWriter("output.txt");
			
			if(!flag) {
				writer.print(-1);
			}
			else {
				writer.println(uselessRoads.size());
				for(int i = 0; i < uselessRoads.size(); i++) {
					writer.print(uselessRoads.get(i)+1 + " ");
				}
			}
			
			writer.close();
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
 	private static void unionCities(DSU componets, RoadType roadType) {
		for(int i = 0; i < roads.size(); i++) {
			if(roads.get(i).getRoadType() != roadType) continue;
			
			int u = roads.get(i).getFrom();
			int v = roads.get(i).getTo();
			
			if(componets.findSet(u) == componets.findSet(v)) {
				uselessRoads.add(i);
				continue;
			}
			
			componets.union(u, v);
		}
	}	
 	
	public static void main(String[] args) throws CloneNotSupportedException {
		init();

		DSU neutralRoads = new DSU(n);
		unionCities(neutralRoads, RoadType.NEUTRAL);
		
		DSU royalRoads = neutralRoads.clone();
		unionCities(royalRoads, RoadType.ROYAL);
		if(royalRoads.getRootNumber() != 1) {
			printAnswer(false);
			return;
		}
		
		DSU robberRoads = neutralRoads.clone();
		unionCities(robberRoads, RoadType.ROBBER);
		if(robberRoads.getRootNumber() != 1) {
			printAnswer(false);
			return;
		}
		
		printAnswer(true);
	}
}

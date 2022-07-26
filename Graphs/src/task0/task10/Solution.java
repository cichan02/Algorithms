package task0.task10;

import static java.util.Map.Entry.*;
import static java.lang.Math.*;

import task0.*;

import java.io.*;
import java.util.*;

public class Solution implements Runnable {
	public static void main(String[] args) throws IOException {
		new Thread(null, new Solution(), "", 64 * 1024 * 1024).start();
	}
	
	public void run() {
		try {
			FastScanner scanner = new FastScanner("input.txt");
			int n = scanner.nextInt();
			int m = scanner.nextInt();
			
			List<Map<Integer, Short>> adjacList = new ArrayList<>(n);
			
			for(int i = 0; i < n; i++) {
				adjacList.add(new HashMap<Integer, Short>());
			}
			
			for(int i = 0; i < m; i++) {
				int v = scanner.nextInt();
				int u = scanner.nextInt();
				short w = scanner.nextShort();	
				w = (short)min(w, adjacList.get(v-1).getOrDefault(u, w));
				
				adjacList.get(v-1).put(u, w);
				adjacList.get(u-1).put(v, w);
			}
			
			boolean[] processed = new boolean[n];
			
			long[] dist = new long[n];
			for(int i = 0; i < n; i++) {
				dist[i] = Long.MAX_VALUE;
			}
			dist[0] = 0;
			
			Queue<Map.Entry<Integer, Long>> vertexes = new PriorityQueue<>(comparingByValue());	
			vertexes.add(Map.entry(1, dist[0]));
			
			while(!vertexes.isEmpty()) {
				int v = vertexes.peek().getKey() - 1;
				long dv = vertexes.remove().getValue();
				if(processed[v]) {
					continue;
				}
				
				processed[v] = true;
				dist[v] = dv;		
				
				for(var vertex : adjacList.get(v).entrySet()) {
					int u = vertex.getKey() - 1;
					long du = dv + vertex.getValue();
					if(!processed[u] && du < dist[u]) {
						vertexes.add(Map.entry(u+1, du));
					}
				}
			}
			
			PrintWriter writer = new PrintWriter("output.txt");
			writer.print(dist[n-1]);
			writer.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}
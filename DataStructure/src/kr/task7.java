package kr;

import java.util.*;
import java.io.*;

public class task7 {
	
	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("in.txt"));
		int n = scanner.nextInt();
		
		Deque<Integer> deq = new ArrayDeque<>();
		for(int i = 0; i < n; i++) {
			deq.add(i);
		}
		
		List<Integer> answer = new ArrayList<>();
		for(int i = 0; i < n; i++) {
			answer.add(0);
		}
		
		int flag = 0;
		while(!deq.isEmpty()) {
			int place1 = deq.removeFirst();
			answer.set(place1, flag);
			flag = flag == 0 ? 1 : 0;
			if(!deq.isEmpty()) {
				int place2 = deq.removeFirst();
				deq.addLast(place2);
			}
		}	
		scanner.close();
		
		PrintWriter writer = new PrintWriter("out.txt");
		for(int i = 0; i < answer.size(); i++) {
			writer.print(answer.get(i) + " ");
		}
		writer.close();
	}
}

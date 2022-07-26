package task1;

import static java.lang.Math.*;

import java.io.*;
import java.util.*;

import task0.FastScanner;

public class Solution {
	private static byte n;
	private static byte m;
	private static byte[] field;
	private static int record = Integer.MAX_VALUE;
	
	private static int init() {
		FastScanner scanner = new FastScanner("input.txt");
		m = scanner.nextByte();
		n = scanner.nextByte();
		field = new byte[n*m];
		int k = scanner.nextInt();
		
		for(short i = 0; i < k; i++) {
			byte x = (byte)(scanner.nextByte() - 1);
			byte y = (byte)(scanner.nextByte() - 1);
			field[y*m + x] = 2;
		}
		
		return k;
	}
	
	private static boolean isBlack(int i, byte[] field) {
		return field[i] == 2;
	}
	
	private static boolean isCovered(int i, byte[] field) {
		return field[i] == 3;
	}

	private static boolean isKnight(int i, byte[] field) {
		return field[i] == 1;
	}
	
	private static void setCovered(int i, byte[] field) {
		field[i] = 3;
	}
	
	private static List<Integer> neighbours(int i) {
		List<Integer> neighbours = new LinkedList<>();
		int x = i % m;
		int y = i / m;
		if(y-1 >= 0) {
			if(x-2 >= 0) neighbours.add((y-1)*m + x-2);
			if(x+2 < m) neighbours.add((y-1)*m + x+2);
			if(y-2 >= 0) {
				if(x-1 >= 0) neighbours.add((y-2)*m + x-1);
				if(x+1 < m) neighbours.add((y-2)*m + x+1);
			}
		}
		if(y+1 < n) {
			if(x-2 >= 0) neighbours.add((y+1)*m + x-2);
			if(x+2 < m) neighbours.add((y+1)*m + x+2);
			if(y+2 < n) {
				if(x-1 >= 0) neighbours.add((y+2)*m + x-1);
				if(x+1 < m) neighbours.add((y+2)*m + x+1);
			}
		} 
		return neighbours;
	}
	
	private static int coverNeighbours(int place, byte[] field) {
		List<Integer> neighbours = neighbours(place);
		int sum = 0;
		for(int i: neighbours) {
			if(!isBlack(i, field) && !isCovered(i, field) && !isKnight(i, field)) {
				setCovered(i, field);
				sum++;
			}
		}
		return sum;
	}
	
	private static void noNameFunction(byte[] field, int number, int where, int checkSum, Set<byte[]> answer) {
		if(!isCovered(where, field)) checkSum++;
		field[where] = 1;
		number++;
		if(number > record) return;
		int neighbNum= coverNeighbours(where, field);
		checkSum += neighbNum;
		if(checkSum == n*m) {
			if(number < record) {
				answer.removeAll(answer);
				record = number;
			}
			answer.add(field);
			return;
		}
		for(int i = where+1; i < n*m; i++) {
			if(!isBlack(i, field)) {
				noNameFunction(field.clone(), number, i, checkSum, answer);
			}
		}
	}
	
	private static void printAnswer(Set<byte[]> answer) {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter("output.txt");
			writer.println(answer.size());
			writer.println();
			for(byte[] field: answer) {
				for(byte i = 0; i < n; i++) {
					for(byte j = 0; j < m; j++) {
						if(field[i*m + j] == 3) {
							writer.print(0);
						}
						else {
							writer.print(field[i*m + j]);
						}
					}
					writer.println();
				}
				writer.println();
			}
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			writer.close();
		}
	}	
	
	public static void main(String[] args) {
		int k = init();
		Set<byte[]> answer = new HashSet<>();
		int upperBound = min(2*m+2, n*m);
		for(int i = 0; i < upperBound; i++) {
			if(!isBlack(i, field)) {
				noNameFunction(field.clone(), 0, i, k, answer);
			}
		}
		printAnswer(answer);
	}
}

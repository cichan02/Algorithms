package task65;

import static java.lang.Math.*;

import java.util.*;

public class Main {
	private static int powOf2(int i) {
		return 1<<i;
	}
	
	public static int signumBinSearch(int bound) {
		return bound > -1 ? bound : abs(bound + 1);
	}
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		long m = scanner.nextLong();
		int n = scanner.nextInt();
		int k = scanner.nextInt();
		
		long[] a = new long[n];
		for(int i = 0; i < n - 1; i++) {
			a[i] = scanner.nextLong();
		}
		a[n-1] = m + 1;
		
		int[] log2 = new int[n+1];
		int powOf2 = 0;
		for(int i = 1; i < n + 1; i++) {
			log2[i] = (i>>(powOf2+1) > 0) ? ++powOf2 : powOf2;
		}
		
		//form sparse table for minutes
		long[][] minutes = new long [log2[n]+1][n];
		minutes[0][0] = a[0] - 1;
		for(int j = 1; j < n; j++) {
			minutes[0][j] = a[j] - a[j-1];
		}
		for(int i = 1; i < log2[n] + 1; i++) {
			for(int j = 0; j < n - powOf2(i) + 1; j++) {
				minutes[i][j] = max(minutes[i-1][j], minutes[i-1][j+powOf2(i-1)]);
			}
		}	
		
		for(int i = 0; i < k; i++) {
			long l = scanner.nextLong();
			long r = scanner.nextLong();
			int leftPlace = signumBinSearch(Arrays.binarySearch(a, l));
			int rightPlace = signumBinSearch(Arrays.binarySearch(a, r));
			rightPlace = r == a[rightPlace] ? ++rightPlace : rightPlace;
			if(leftPlace == rightPlace)
				System.out.println(r - l + 1);
			else {
				long leftMax = a[leftPlace] - l;
				long rightMax = r - a[rightPlace-1] + 1;
				long boundMax = max(rightMax, leftMax);
				if (rightPlace - leftPlace == 1)
					System.out.println(boundMax);
				else {
					int p = log2[rightPlace - leftPlace - 1];
					long middleMax = max(minutes[p][leftPlace+1], minutes[p][rightPlace-powOf2(p)]);
					System.out.println(max(middleMax, boundMax));
				}
			}
		}
		
		scanner.close();
	}
}

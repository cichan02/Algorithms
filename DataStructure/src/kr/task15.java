package kr;

import static java.lang.Math.*;

import java.util.*;
import java.io.*;

class FastScanner {
    BufferedReader reader;
    StringTokenizer tokenizer;

    public FastScanner(String fileName) throws IOException {
        reader = new BufferedReader(new FileReader(fileName));
    }

    public String next() throws IOException {
        while (tokenizer == null || !tokenizer.hasMoreTokens()) {
            String line = reader.readLine();
            if (line == null) {
                throw new EOFException();
            }
            tokenizer = new StringTokenizer(line);
        }
        return tokenizer.nextToken();
    }

    public int nextInt() throws IOException {
        return Integer.parseInt(next());
    }
    
    public short nextShort() throws IOException {
        return Short.parseShort(next());
    }
    
    public long nextLong() throws IOException {
        return Long.parseLong(next());
    }
}

class Top implements Comparable<Top>{
	private long path;
	private final short i, j;
	private boolean processed = false;
	
	public Top(long path, short i, short j) {
		super();
		this.path = path;
		this.i = i;
		this.j = j;
	}
	
	public long getPath() {
		return path;
	}
	public void setPath(long path) {
		this.path = path;
	}
	public short getI() {
		return i;
	}
	public short getJ() {
		return j;
	}
	public boolean isProcessed() {
		return processed;
	}
	public void setProcessed() {
		processed = true;
	}
	
	public boolean isWall() {
		return path < 0 ? true : false;
	}

	@Override
	public int compareTo(Top other) {
		long temp = path - other.path;
		if(temp == 0)
			return 0;
		return temp < 0 ? -1 : 1;
	}
}

public class task15 {
	public static void main(String[] args) throws EOFException, IOException {
		FastScanner fastSc = new FastScanner("in.txt");
		short n = fastSc.nextShort();
		short m = fastSc.nextShort();
		
		int[][] height = new int[n][m];
		for(short i = 0; i < n; i++) {
			for(short j = 0; j < m; j++) {
				height[i][j] = fastSc.nextInt();
			}
		}
		
		int k = fastSc.nextInt();
		short x1 = fastSc.nextShort();
		short y1 = fastSc.nextShort();
		short x2 = fastSc.nextShort();
		short y2 = fastSc.nextShort();
		
		n += 2;
		m += 2;
		Top[][] minimalPath = new Top[n][m];
		for(short i = 0; i < n; i++) {
			for(short j = 0; j < m; j++) {
				minimalPath[i][j] = (i == 0 || j == 0 || i == n-1 || j == m-1 ) ? 
						new Top(-1, i, j) :
						new Top(Long.MAX_VALUE, i, j);
			}
		}
		minimalPath[x1][y1].setPath(0);
		
		Queue<Top> tops = new PriorityQueue<>();
		tops.add(minimalPath[x1][y1]);
		
		while(!tops.isEmpty()) {
			Top temp = tops.remove();
			if(temp.isProcessed()) {
				continue;
			}
			
			short i = temp.getI();
			short j = temp.getJ();
			
			minimalPath[i][j].setProcessed();
			
			Top[] neiboors = {minimalPath[i-1][j], minimalPath[i+1][j],
								minimalPath[i][j-1], minimalPath[i][j+1]};
			
			for(int t = 0; t < neiboors.length; t++) {
				Top neib = neiboors[t];
				if(!neib.isWall() && !neib.isProcessed()) {
					neib.setPath(min(neib.getPath(),
								abs(height[i-1][j-1] - height[neib.getI()-1][neib.getJ()-1]) + k + temp.getPath()));
					tops.add(neib);
				}
			}
		} 
		
		PrintWriter writer = new PrintWriter("out.txt");
		writer.print(minimalPath[x2][y2].getPath());
		writer.close();
	}
}

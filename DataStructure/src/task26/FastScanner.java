package task26;

import java.io.*;
import java.util.*;

public class FastScanner {
    BufferedReader reader;
    StringTokenizer tokenizer;

    public FastScanner(String fileName) throws IOException {
        reader = new BufferedReader(new FileReader(fileName));
    }
    
    public FastScanner(InputStream source) {
    	reader = new BufferedReader(new InputStreamReader(source));
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
    
    public byte nextByte() throws IOException {
    	return Byte.parseByte(next());
    }
}
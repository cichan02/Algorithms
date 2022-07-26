package kr;

import java.io.*;

import java.util.StringTokenizer;

public class FastScanner {
    BufferedReader reader;
    StringTokenizer tokenizer;

    public FastScanner(String fileName) {
    	try {
    		reader = new BufferedReader(new FileReader(fileName));
    	}
    	catch (FileNotFoundException e) {
    		e.printStackTrace();
    	}
    }
    
    public FastScanner(InputStream source) {
    	reader = new BufferedReader(new InputStreamReader(source));
    }
    
    public String next() {
        while (tokenizer == null || !tokenizer.hasMoreTokens()) {
        	try {
	            String line = reader.readLine();
	            tokenizer = new StringTokenizer(line);
        	}
        	catch(IOException e) {
        		e.printStackTrace();
        	}
        }
        return tokenizer.nextToken();
    }

    public int nextInt() {
        return Integer.parseInt(next());
    }
    
    public short nextShort() {
        return Short.parseShort(next());
    }
    
    public long nextLong() {
        return Long.parseLong(next());
    }
    
    public byte nextByte() {
    	return Byte.parseByte(next());
    }
    
    public String nextLine() {
    	String str = "";
    	try {
	    	if(tokenizer.hasMoreTokens()) {
	    		str = tokenizer.nextToken("\n");
	    	}
	    	else {
	    		str = reader.readLine();
	    	}
    	}
    	catch (IOException e) {
    		e.printStackTrace();
    	}
    	return str;
    }
}
package kr;

public class DSU {
	private int rootNumber;
	private int[] parent;
	private int[] size;
	
	public DSU(int n) {
		rootNumber = n;

		parent = new int[n];
		size = new int[n];
		for(int i = 0; i < n; i++) {
			parent[i] = i+1;
			size[i] = 1;
		}
	}
	
	private boolean isRoot(int x) {
		return x == parent[x-1];
	}
	
	public int getRootNumber() {
		return rootNumber;
	}

	public int findSet(int x) {
		if(isRoot(x))
			return x;
		
		if(!isRoot(parent[x-1])) {
			size[parent[x-1]-1] -= size[x-1];
		}
		
		return parent[x-1] = findSet(parent[x-1]);
	}
	
	public void union(int x, int y) {
		x = findSet(x);
		y = findSet(y);
		
		if(x == y) return;
			
		if(size[x-1] < size[y-1]) {
//				swap
				int temp = x;
				x = y;
				y = temp;
		}
		parent[y-1] = x;
		size[x-1] += size[y-1];
		rootNumber--;
	}
}
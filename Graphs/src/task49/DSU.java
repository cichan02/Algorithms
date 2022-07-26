package task49;

public class DSU implements Cloneable {
	private int rootNumber;
	private int[] parent;
	private int[] size;
	
	public DSU(int n) {
		rootNumber = n;

		parent = new int[n];
		size = new int[n];
		for(int i = 0; i < n; i++) {
			parent[i] = i;
			size[i] = 1;
		}
	}
	
	private boolean isRoot(int x) {
		return x == parent[x];
	}
	
	public int getRootNumber() {
		return rootNumber;
	}

	public int findSet(int x) {
		if(isRoot(x))
			return x;
		
		if(!isRoot(parent[x])) {
			size[parent[x]] -= size[x];
		}
		
		return parent[x] = findSet(parent[x]);
	}
	
	public void union(int x, int y) {
		x = findSet(x);
		y = findSet(y);
		
		if(x == y) return;
			
		if(size[x] < size[y]) {
//				swap
				int temp = x;
				x = y;
				y = temp;
		}
		parent[y] = x;
		size[x] += size[y];
		rootNumber--;
	}

	@Override
	protected DSU clone() throws CloneNotSupportedException {
		DSU newDsu = (DSU)super.clone();
		newDsu.parent = parent.clone();
		newDsu.size = size.clone();
		return newDsu;
	}
}

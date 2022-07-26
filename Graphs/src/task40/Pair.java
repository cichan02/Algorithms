package task40;

public class Pair {
	private boolean flag;
	private int[] prev;
	
	public Pair(boolean flag, int[] prev) {
		this.flag = flag;
		this.prev = prev;
	}

	public boolean first() {
		return flag;
	}

	public int[] second() {
		return prev;
	}
}

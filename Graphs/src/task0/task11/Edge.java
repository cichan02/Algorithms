package task0.task11;

public class Edge {
	private int from;
	private int to;
	private int capacity;
	private int flow;
	
	public Edge() {}	
	
	public Edge(int from, int to, int capacity, int flow) {
		this.from = from;
		this.to = to;
		this.capacity = capacity;
		this.flow = flow;
	}

	public int getFrom() {
		return from;
	}
	public int getTo() {
		return to;
	}
	public int getCapacity() {
		return capacity;
	}
	public int getFlow() {
		return flow;
	}
	
	public void increaseFlow(int flow) {
		this.flow += flow;
	}
	public void decreaseFlow(int flow) {
		this.flow -= flow;
	}
	
	public int available() {
		return capacity - flow;
	}
}

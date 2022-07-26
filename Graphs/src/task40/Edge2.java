package task40;

import task0.task11.Edge;

public class Edge2 extends Edge {
	private int cost;
	
	public Edge2(int from, int to, int capacity, int flow, int cost) {
		super(from, to, capacity, flow);
		this.cost = cost;
	}

	public int getCost() {
		return cost;
	}
	
	public void changeCostSign() {
		cost *= -1;
	}
}

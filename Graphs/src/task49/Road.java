package task49;

public class Road {
	private int from;
	private int to;
	private RoadType roadType;
	
	public Road(int from, int to, RoadType roadType) {
		super();
		this.from = from;
		this.to = to;
		this.roadType = roadType;
	}

	public int getFrom() {
		return from;
	}
	public int getTo() {
		return to;
	}
	public RoadType getRoadType() {
		return roadType;
	}	
}

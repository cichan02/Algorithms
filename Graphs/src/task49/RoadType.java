package task49;

public enum RoadType {
	NONE(-1), ROYAL(1), ROBBER(2), NEUTRAL(3);
	
	private final int id;
	private RoadType(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public static RoadType getRoad(int id) {
		for(RoadType road: RoadType.values()) {
			if(road.getId() == id) {
				return road;
			}
		}
		return RoadType.NONE;
	}
}

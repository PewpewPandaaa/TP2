package simulator.model;

public class NewJunctionEvent extends Event {

	private String id;
	private LightSwitchingStrategy lsStrategy;
	private DequeuingStrategy dsStrategy;
	private int x;
	private int y;
	
	
	public NewJunctionEvent(int time, String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dsStrategy, int x, int y) {
		super(time);
		this.id = id;
		this.lsStrategy = lsStrategy;
		this.dsStrategy = dsStrategy;
		this.x=x;
		this.y = y;
	}

	@Override
	void execute(RoadMap map) {
		Junction js = new Junction( id, lsStrategy, dsStrategy, x, y);
		map.addJunction(js);
		
	}

	@Override
	public String toString() {
		return "New Junction '" + id + "'";
	}

	

	
}

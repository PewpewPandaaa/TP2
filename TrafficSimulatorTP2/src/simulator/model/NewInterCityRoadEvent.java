package simulator.model;

public class NewInterCityRoadEvent extends Event{

	private String id;
	private String srcJun;
	private String destJunc;
	private int length;
	private int co2Limit;
	private int maxSpeed;
	private Weather weather;
	private Junction src;
	private Junction des;

	
	public NewInterCityRoadEvent(int time, String id, String srcJun, String destJunc, int length, int co2Limit, int maxSpeed, Weather weather) {
		super(time);
		this.id=id;
		this.srcJun = srcJun;
		this.destJunc = destJunc;
		this.length = length;
		this.co2Limit = co2Limit;
		this.maxSpeed = maxSpeed;
		this.weather = weather;
		// TODO Auto-generated constructor stub
	}

	@Override
	void execute(RoadMap map) {
		
		src = map.getJunction(srcJun);
		des = map.getJunction(destJunc);
		
		InterCityRoad ir = new InterCityRoad(id,src, des, length, co2Limit, maxSpeed, weather);
		map.addRoad(ir);

	}

	@Override
	public String toString() {
		return "Road '" + id + "'";
	}



}

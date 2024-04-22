package simulator.model;

public class NewCityRoadEvent extends Event {

	private String id;
	private String srcJun;
	private String destJunc;
	private int length;
	private int co2Limit;
	private int maxSpeed;
	private Weather weather;
	private Junction src;
	private Junction des;
	
	public NewCityRoadEvent(int time, String id, String srcJun, String destJunc, int length, int co2Limit, int maxSpeed, Weather weather) {
		super(time);
		this.id=id;
		this.srcJun = srcJun;
		this.destJunc = destJunc;
		this.length = length;
		this.co2Limit = co2Limit;
		this.maxSpeed = maxSpeed;
		this.weather = weather;

	}

	@Override
	void execute(RoadMap map) {
		src = map.getJunction(srcJun);
		des = map.getJunction(destJunc);
		
		CityRoad cr = new CityRoad(id, src, des, length, co2Limit, maxSpeed, weather);
		map.addRoad(cr);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Road '" + id + "'";
	}


}

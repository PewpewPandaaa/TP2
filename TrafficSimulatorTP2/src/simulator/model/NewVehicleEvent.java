package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class NewVehicleEvent extends Event{
	
	private String id;
	private int maxSpeed;
	private int contClass;
	private List<String> itinerary;
	private List<Junction> itinerario;

	public NewVehicleEvent(int time, String id, int maxSpeed, int contClass, List<String> itinerary) {
		super(time);
		this.id = id;
		this.maxSpeed = maxSpeed;
		this.contClass = contClass;
		this.itinerary = new ArrayList<>(itinerary);
		
	}

	@Override
	void execute(RoadMap map) {
		
		itinerario = new ArrayList<>();
		
		for(int i = 0 ; i < itinerary.size();i++) {
			itinerario.add(map.getJunction(itinerary.get(i)));
		}
		Vehicle v1 = new Vehicle(id, maxSpeed, contClass, itinerario);
		
		map.addVehicle(v1);
		v1.moveToNextRoad();
		
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "New Vehicle'" + id +"'";
	}


	
	

}

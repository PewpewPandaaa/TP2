package simulator.factories;

import java.util.List;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewVehicleEvent;

public class NewVehicleEventBuilder extends Builder<Event>{

	//private final String ID_TYPE = "new_vehicle";
	
	public NewVehicleEventBuilder() {
		super("new_vehicle");
	}

	@Override
	protected NewVehicleEvent createTheInstance(JSONObject data) {
		
		NewVehicleEvent info = null;
		
		//if(data.getString("_type").equals(ID_TYPE)) {
			
			List<String> veh1 = new ArrayList<>();
			
			JSONArray ja = data.getJSONArray("itinerary");
			
			for(int i= 0; i < ja.length(); i++) {
				veh1.add(ja.getString(i));
			}
			
			info = new NewVehicleEvent(data.getInt("time"), data.getString("id"), data.getInt("maxspeed"), 
					data.getInt("class"), veh1);
		
		//}
		
		return info;
	}
	

}

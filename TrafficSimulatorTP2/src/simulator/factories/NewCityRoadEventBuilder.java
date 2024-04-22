package simulator.factories;

import org.json.JSONObject;


import simulator.model.Event;
import simulator.model.NewCityRoadEvent;
import simulator.model.NewJunctionEvent;
import simulator.model.Weather;

public class NewCityRoadEventBuilder extends Builder<Event> {
	
	//private final String ID_TYPE = "new_junction";
	//private Factory <Weather> weather;
	
	public NewCityRoadEventBuilder() {
		super("new_city_road");
		
	}

	@Override
	protected NewCityRoadEvent createTheInstance(JSONObject data) {

		NewCityRoadEvent info = null;
		
		//if(data.getString("_type").equals(ID_TYPE)) {
			
			info = new NewCityRoadEvent(data.getInt("time"),data.getString("id"), data.getString("src"), data.getString("dest"),
					data.getInt("maxspeed"), data.getInt("co2limit"), data.getInt("length"), Weather.valueOf(data.getString("weather")));
		
	//}
		return info;
	}
	
	

	
	
}

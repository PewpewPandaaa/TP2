package simulator.factories;

import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewCityRoadEvent;
import simulator.model.NewInterCityRoadEvent;
import simulator.model.Weather;

public class NewInterCityRoadEventBuilder extends Builder<Event>{
	
    //private final String ID_TYPE = "new_junction";
	//private Factory <Weather> weather;
	
	public NewInterCityRoadEventBuilder() {
		super("new_inter_city_road");
	}

	@Override
	protected NewInterCityRoadEvent createTheInstance(JSONObject data) {

		NewInterCityRoadEvent info = null;
		
		//if(data.getString("_type").equals(ID_TYPE)) {
			
			info = new NewInterCityRoadEvent(data.getInt("time"),data.getString("id"), data.getString("src"), data.getString("dest"),
					data.getInt("maxspeed"), data.getInt("co2limit"), data.getInt("length"), Weather.valueOf(data.getString("weather")));
		
	//}
		return info;
	}
	
	
}

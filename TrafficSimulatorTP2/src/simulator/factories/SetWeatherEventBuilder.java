package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.SetWeatherEvent;
import simulator.model.Weather;

public class SetWeatherEventBuilder extends Builder<Event>{
	
	public SetWeatherEventBuilder() {
		super("set_weather");
	}

	//private final String ID_TYPE = "set_weather";

	@Override
	protected SetWeatherEvent createTheInstance(JSONObject data) {
		
		SetWeatherEvent info = null;
		
		//if(data.getString("_type").equals(ID_TYPE)) {
			
			List <Pair<String, Weather>> weather = new ArrayList<>();

	        JSONArray ja = data.getJSONArray("info");

	        for (int i =0; i< ja.length();i++) {
	            JSONObject jo = ja.getJSONObject(i);
	            weather.add(new Pair<String, Weather>(jo.getString("road"), Weather.valueOf(jo.getString("weather").toUpperCase())));
	        }
	        
	        info = new SetWeatherEvent(data.getInt("time"), weather); 
	    //}

		return info;
	}
}

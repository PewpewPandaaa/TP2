package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.NewSetContClassEvent;


public class SetContClassEventBuilder extends Builder<Event>{
	
	//private final String ID_TYPE = "new_vehicle";
	
	public SetContClassEventBuilder() {
		super("set_cont_class");
	}

	@Override
	protected NewSetContClassEvent createTheInstance(JSONObject data) {
		
		NewSetContClassEvent info = null;
		
		//if(data.getString("_type").equals(ID_TYPE)) {
		
			List <Pair<String, Integer>> contClass = new ArrayList<>();
		
			JSONArray ja = data.getJSONArray("info");
			
			for(int i=0; i< ja.length(); i++) {
				JSONObject jo = ja.getJSONObject(i);
				contClass.add(new Pair<String, Integer>(jo.getString("vehicle"), jo.getInt("class")));
			}
			
			info = new NewSetContClassEvent(data.getInt("time"), contClass);
		
		//}
		
		return info;
	}
}

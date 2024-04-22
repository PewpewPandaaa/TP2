package simulator.factories;

import org.json.JSONObject;

import simulator.model.DequeuingStrategy;
import simulator.model.MoveAllStrategy;
import simulator.model.MoveFirstStrategy;

public class MoveAllStrategyBuilder extends Builder<DequeuingStrategy> {

	//private final String ID_TYPE = "move_all_dqs";
	
	public MoveAllStrategyBuilder() {
		super("move_all_dqs");
	}

	@Override
	protected MoveAllStrategy createTheInstance(JSONObject data) {
		MoveAllStrategy info = null;
		
		//if(data.getString("_type").equals(ID_TYPE)) {
			info = new MoveAllStrategy();
		
		//}
	
		return info;
		

	}
	

}

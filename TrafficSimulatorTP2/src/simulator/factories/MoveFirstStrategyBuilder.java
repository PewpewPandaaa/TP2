package simulator.factories;

import org.json.JSONObject;

import simulator.model.DequeuingStrategy;
import simulator.model.MoveFirstStrategy;


public class MoveFirstStrategyBuilder extends Builder<DequeuingStrategy> {

	//private final String ID_TYPE = "move_first_dqs";
	
	public MoveFirstStrategyBuilder() {
		super("move_first_dqs");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected MoveFirstStrategy createTheInstance(JSONObject data) {
		MoveFirstStrategy info = null;
		
		//if(data.getString("_type").equals(ID_TYPE)) {
				info = new MoveFirstStrategy();
			
		//}
		return info;
	}
	

}

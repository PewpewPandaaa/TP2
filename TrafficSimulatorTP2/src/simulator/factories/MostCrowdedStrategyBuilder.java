package simulator.factories;

import org.json.JSONObject;

import simulator.model.LightSwitchingStrategy;
import simulator.model.MostCrowdedStrategy;
import simulator.model.RoundRobinStrategy;

public class MostCrowdedStrategyBuilder extends Builder<LightSwitchingStrategy>{

	//private final String ID_TYPE = "most_crowded_lss";
	
	public MostCrowdedStrategyBuilder() {
		super("most_crowded_lss");
		
	}

	@Override
	protected MostCrowdedStrategy createTheInstance(JSONObject data) {
		MostCrowdedStrategy info = null;
		
		//if(data.getString(_type).equals(ID_TYPE)) {
			if(data.getInt("timeslot") >= -1)
				info = new MostCrowdedStrategy(data.getInt("timeslot"));
			else info = new MostCrowdedStrategy(1);
			
		//}
		return info;
	}

}

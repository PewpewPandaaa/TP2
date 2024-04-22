package simulator.factories;

import org.json.JSONObject;

import simulator.model.LightSwitchingStrategy;
import simulator.model.RoundRobinStrategy;

public class RoundRobinStrategyBuilder extends Builder<LightSwitchingStrategy>{

	//private final String ID_TYPE = "round_robin_lss";
	public RoundRobinStrategyBuilder() {
		super("round_robin_lss");
		
	}

	@Override
	protected RoundRobinStrategy createTheInstance(JSONObject data) {
		RoundRobinStrategy info = null;
		
		//if(data.getString("_type").equals(ID_TYPE)) {
			if(data.getInt("timeslot") >= -1)
				info = new RoundRobinStrategy(data.getInt("timeslot"));
			else info = new RoundRobinStrategy(1);
		//}
		
		return info;
	}
	

}

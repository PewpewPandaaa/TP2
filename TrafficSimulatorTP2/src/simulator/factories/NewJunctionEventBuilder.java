package simulator.factories;

import org.json.JSONObject;

import simulator.model.DequeuingStrategy;
import simulator.model.Event;
import simulator.model.LightSwitchingStrategy;
import simulator.model.NewJunctionEvent;
import simulator.model.RoundRobinStrategy;

public class NewJunctionEventBuilder extends Builder<Event> {

	//private final String ID_TYPE = "new_junction";
	private Factory<LightSwitchingStrategy>lsStrategy;
	private Factory <DequeuingStrategy> dqStrategy;
	
	public NewJunctionEventBuilder(Factory<LightSwitchingStrategy>lsStrategy, Factory <DequeuingStrategy> dqStrategy) {
		super("new_junction");
		this.lsStrategy = lsStrategy;
		this.dqStrategy = dqStrategy;
	}

	@Override
	protected NewJunctionEvent createTheInstance(JSONObject data) {
		
		NewJunctionEvent info = null;
		
		//if(data.getString("_type").equals(ID_TYPE)) {
		
				info = new NewJunctionEvent(data.getInt("time"),data.getString("id"),this.lsStrategy.createInstance(data.getJSONObject("ls_strategy")),
						this.dqStrategy.createInstance(data.getJSONObject("dq_strategy")), data.getJSONArray("coor").getInt(0), data.getJSONArray("coor").getInt(1));
			
		//}
		
		return info;

	}

}

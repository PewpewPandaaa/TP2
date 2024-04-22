package simulator.model;

import java.util.List;

public class MostCrowdedStrategy implements LightSwitchingStrategy{
	
	private int timeSlot;
	
	public MostCrowdedStrategy(int timeSlot){
		this.timeSlot=timeSlot;
	}
	
	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime,
			int currTime) {
		
		int max=0;
		int j = 0;
		int k = (currGreen+2) % qs.size();
		
		if(roads.isEmpty()) return -1;
		else 
			if(currGreen == -1) {
				for(int i = 1; i < qs.size(); i++) {
					if(max < qs.get(i).size()) {
						max = qs.get(i).size();
						j = i;
					}
				}
				return j;
			}
			else 
				if((currTime-lastSwitchingTime) < timeSlot) return currGreen;
				
				else {
					while(k != currGreen+1) {
						if(qs.get(max).size() < qs.get(k).size()) {
							max = qs.get(k).size();
							j = k;
						}
						k = (k+1) %  qs.size() ;
					}
					return j;
				} 
					
	
	}

}

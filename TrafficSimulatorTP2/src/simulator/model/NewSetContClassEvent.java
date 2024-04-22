package simulator.model;

import java.util.List;

import simulator.misc.Pair;

public class NewSetContClassEvent extends Event{

	List<Pair <String, Integer>> cs;
	
	public NewSetContClassEvent(int time, List<Pair<String, Integer>> cs) {
		super(time);
		try {
			if(cs != null)
				this.cs = cs;
			else throw new ExceptionClass();	
		}catch (ExceptionClass e) {
			
			System.out.println("ContaminationClass no deberia ser nulo");
		}
	
		
	}

	@Override
	void execute(RoadMap map) {
		for(int i=0 ; i < cs.size(); i++) {
				try {
					if(map.getVehicle(cs.get(i).getFirst())!=null) {
						map.getVehicle(cs.get(i).getFirst()).setGradoContamination(cs.get(i).getSecond());
					}else throw new ExceptionClass();
				}catch (ExceptionClass e) { 
						System.out.println("No se ha encontrado dicho vehiculo");
					
				}
						
			}
		}

	@Override
	public String toString() {
		String w = "Change CO2 class : [(";
		for(int i = 0; i < cs.size(); i++) {
			 if(i == 0 ) w += cs.get(i).getFirst() + "," + cs.get(i).getSecond() + ")";
			 else w += ",(" + cs.get(i).getFirst() + "," + cs.get(i).getSecond() + ")";
		}
		w += "]";
		
		return w;
	}

	
	}
		


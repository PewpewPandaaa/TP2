package simulator.model;

import java.util.List;

import simulator.misc.Pair;

public class SetWeatherEvent extends Event{

	List<Pair <String, Weather>> ws;
	
	public SetWeatherEvent(int time, List <Pair <String, Weather>> ws) {
		super(time);
		try {
			if(ws!=null){
				this.ws = ws;
			}
			else throw new ExceptionClass();
		}catch (ExceptionClass e) {
			System.out.println("Las condiciones atmosfericas no debe ser nulo");
		}
		
	}

	@Override
	void execute(RoadMap map) {

		for(int i=0 ; i < ws.size(); i++) {
			try {
				if(map.getRoad(ws.get(i).getFirst())!=null) {
					map.getRoad(ws.get(i).getFirst()).setCondicionAmbiental(ws.get(i).getSecond());
				}else throw new ExceptionClass();
			}catch (ExceptionClass e) { 
					System.out.println("No se ha encontrado dicha carretera en el mapa de carreteras");
				
			}
					
		}
	}

	@Override
	public String toString() {
		String w = "Change Weather:[(";
		for(int i = 0; i < ws.size(); i++) {
			if(i == 0) 
			w += ws.get(i).getFirst() + "," + ws.get(i).getSecond() + ")";
			else w += ",(" +ws.get(i).getFirst() + "," + ws.get(i).getSecond() + ")";
		}
		w+="]";
		
		return w;
	}


}
	


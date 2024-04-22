package simulator.model;


import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import simulator.misc.SortedArrayList;

public class TrafficSimulator implements Observable<TrafficSimObserver>{
	private RoadMap mapaCarreteras;
	private List<Event> listEvent;
	private List<TrafficSimObserver> listas;
	private int tiempo;
	
	public TrafficSimulator() {
		this.mapaCarreteras = new RoadMap();
		this.listEvent = new SortedArrayList<Event>();
		this.tiempo = 0;
		this.listas = new ArrayList<TrafficSimObserver>();
	}
	
	public void addEvent(Event e) {
		listEvent.add(e);
		onEventAdded(mapaCarreteras, listEvent, e, tiempo);
		
	}
	
	public void advance() {
		this.tiempo++;
		onAdvanceStart(mapaCarreteras, listEvent, tiempo);
		int z = 0;
		while(z < listEvent.size()){
			if(listEvent.get(0).getTime() == this.tiempo) {
				listEvent.remove(0).execute(mapaCarreteras);
			
			}else z++;
		}
		
		
		for(int i= 0; i < mapaCarreteras.getJunctions().size(); i++) {
			mapaCarreteras.getJunctions().get(i).advance(tiempo);
		}
		
		for(int j = 0; j < mapaCarreteras.getRoads().size(); j++) {
			mapaCarreteras.getRoads().get(j).advance(tiempo);
		}
		
		onAdvanceEnd(mapaCarreteras, listEvent, tiempo);
		
	}
	
	
	public void reset() {
		mapaCarreteras.reset();
		listEvent.clear();
		this.tiempo = 0;
		onReset(mapaCarreteras, listEvent, tiempo);
	}
	
	public JSONObject report() {
		JSONObject ob1 = new JSONObject();
		ob1.put("time", this.tiempo);
		ob1.put("state", mapaCarreteras.report());
		
		return ob1;
	}

	@Override
	public void addObserver(TrafficSimObserver o) {
		// TODO Auto-generated method stub
		//o.onRegister(mapaCarreteras, listEvent, tiempo);
		listas.add(o);
	}

	@Override
	public void remomveObserber(TrafficSimObserver o) {
		listas.remove(o);
	}
	
	void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		for(TrafficSimObserver i: listas) {
			i.onAdvanceStart(map, events, time);
		}
	}
	void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		for(TrafficSimObserver i: listas) {
			i.onAdvanceEnd(map, events, time);
		}
	}
	void onEventAdded(RoadMap map, List<Event>events, Event e, int time) {
		for(TrafficSimObserver i: listas) {
			i.onEventAdded(map, events, e, time);
		}
	}
	void onReset(RoadMap map, List<Event>events, int time) {
		for(TrafficSimObserver i: listas) {
			i.onReset(map, events, time);
		}
	}
	void onRegister(RoadMap map, List<Event> events, int time) {
		for(TrafficSimObserver i: listas) {
			i.onRegister(map, events, time);
		}
	}
	void onError(String err) {}

	public int getTiempo() {
		return tiempo;
	}

	public void setTiempo(int tiempo) {
		this.tiempo = tiempo;
	}
	
	
	
}

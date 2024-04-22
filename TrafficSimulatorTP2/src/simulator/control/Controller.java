package simulator.control;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Event;
import simulator.model.ExceptionClass;
import simulator.model.TrafficSimObserver;
import simulator.model.TrafficSimulator;

public class Controller {
	
	private TrafficSimulator sim;
	private Factory<Event> eventsFactory;
	
	
	public Controller(TrafficSimulator sim, Factory<Event> eventsFactory) {
		try {
			if(sim != null || eventsFactory != null) {
				this.sim = sim;
				this.eventsFactory = eventsFactory;
			}else throw new ExceptionClass();
		}catch (ExceptionClass e) {
			System.out.println("Los valores son nulos");
		}
		
		}
	
	
	public void loadEvents(InputStream in) {
		
		JSONObject jo = new JSONObject(new JSONTokener(in));
		JSONArray array = jo.getJSONArray("events");
		
		
		for(int i = 0; i < array.length(); i++) {
			sim.addEvent(eventsFactory.createInstance(array.getJSONObject(i)));
		}
		
		
	}
	
	public void run(int n, OutputStream out) {
		
		if(out == null) {
			for(int i = 0; i < n ; i++ ) {
				sim.advance();
			}
			
		}else {
			for(int i = 0; i < n ; i++ ) {
				sim.advance();
				PrintStream p = new PrintStream(out);
			
				if(i==0) {
					p.println("{");
					p.println(" \"states\":[");
				}
			
				p.print(sim.report());
			
				if(i == n-1) {
					p.println();
					p.println("]");
					p.print("}");
				} else p.println(",");
			}
		}
		
	}
	
	public void reset() {
		sim.reset();
	}
	
	public void addObserver(TrafficSimObserver o) {
		sim.addObserver(o);
	}
	
	public void removeObserver(TrafficSimObserver o) {
		sim.remomveObserber(o);
	}
	public void addEvent(Event e) {
		sim.addEvent(e);
	}
}

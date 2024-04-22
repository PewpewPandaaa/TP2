package simulator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class Junction extends SimulatedObject {
	private List<Road> listaCarreteras;
	private Map<Junction, Road> mapCarreteras;
	private List<List<Vehicle>> listaCola;
	private Map<Road, List<Vehicle>> mapCola;
	private int indiceSemaforoVerde;
	private int pasoSemaforo;
	private LightSwitchingStrategy lsStrategy;
	private DequeuingStrategy dqStrategy;
	private int x;
	private int y;
	
	protected Junction(String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int x, int y) {
		super(id);
		try {
			if(lsStrategy != null && dqStrategy != null && x >= 0 && y >= 0) {
				this.x = x;
				this.y = y;
				this.dqStrategy = dqStrategy;
				this.lsStrategy = lsStrategy;
				this.mapCarreteras = new HashMap<>();
				this.listaCarreteras = new ArrayList<>();
				this.mapCola = new HashMap<>();
				this.listaCola = new ArrayList<List<Vehicle>>();
				this.indiceSemaforoVerde = -1;
			} 
			else throw new ExceptionClass();
		}catch (ExceptionClass e){
			System.out.println("Por favor, introduzca valores correctos:");
			System.out.println("--> las estrategias no deben tener valores nulos");
			System.out.println("--> las coordenadas no deben tener valores negativos");
		}
		
	}

	@Override
	void advance(int time) {
		if(indiceSemaforoVerde != -1) {
			for(Vehicle vehiculo:dqStrategy.dequeue(listaCola.get(indiceSemaforoVerde))) {
				vehiculo.moveToNextRoad();
				listaCola.get(indiceSemaforoVerde).remove(vehiculo);
			}
        
		}
		int indice = lsStrategy.chooseNextGreen(listaCarreteras, listaCola, indiceSemaforoVerde, pasoSemaforo, time);
		if(indice != indiceSemaforoVerde) {
			indiceSemaforoVerde = indice;
			pasoSemaforo = time;
		}
	}
			

	

	@Override
	public JSONObject report() {
	JSONObject ob1 = new JSONObject();
		
	
	
	
		if(indiceSemaforoVerde == -1) {
			ob1.put("green", "none");
		}
		else ob1.put("green",this.listaCarreteras.get(indiceSemaforoVerde)._id);
		JSONArray ja = new JSONArray();
		
		for(int i= 0; i < listaCarreteras.size();i++){
			JSONObject ob3 = new JSONObject();
			ob3.put("road",listaCarreteras.get(i)._id);
			JSONArray ob4 = new JSONArray();
			ob3.put("vehicles", ob4);
			for(int j=0; j < mapCola.get(listaCarreteras.get(i)).size(); j++) {
				ob4.put(mapCola.get(listaCarreteras.get(i)).get(j)._id);
			}
			ja.put(ob3);
		}
		
		
		ob1.put("queues", ja);
		ob1.put("id", this._id);
		
		
	
	return ob1;	
	}
	

	void addIncommingRoad(Road r) {
		try {
			
			if(r.getCruceDestino() == this) {
				listaCarreteras.add(r);
				List<Vehicle> list = new LinkedList<Vehicle>();
				listaCola.add(list);
				mapCola.put(r, list);
			}else throw new ExceptionClass();
		
		}catch (ExceptionClass e) {
			System.out.println("Esta carretera no es realmente una carretera entrante");
		}
	
	}
		
	
	void addOutGoingRoad(Road r) {
			mapCarreteras.put(r.getCruceDestino(),r);
	}
	
	void enter(Vehicle v) {
		
		//listaCarreteras.add(v.getCarretera());
		
		List<Vehicle> r = mapCola.get(v.getCarretera());
		r.add(v);
		
		
		
	}
	
	Road roadTo(Junction j) {;
		return mapCarreteras.get(j);
		
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getIndiceSemaforoVerde() {
		return indiceSemaforoVerde;
	}

	public void setIndiceSemaforoVerde(int indiceSemaforoVerde) {
		this.indiceSemaforoVerde = indiceSemaforoVerde;
	}

	public List<Road> getListaCarreteras() {
		return listaCarreteras;
	}

	public void setListaCarreteras(List<Road> listaCarreteras) {
		this.listaCarreteras = listaCarreteras;
	}

	public Map<Road, List<Vehicle>> getMapCola() {
		return mapCola;
	}

	public void setMapCola(Map<Road, List<Vehicle>> mapCola) {
		this.mapCola = mapCola;
	}


	
	
	
	
	
}

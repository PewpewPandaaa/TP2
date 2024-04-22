package simulator.model;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class RoadMap {
	private List<Junction> listCruces;
	private List<Road> listCarreteras;
	private List<Vehicle> listVehiculos;
	private Map<String, Junction> mapCruces;
	private Map<String, Road> mapCarreteras;
	private Map<String, Vehicle> mapVehiculos;
	
	protected RoadMap() {
		
		this.listCruces = new ArrayList<Junction>();
		this.listCarreteras = new ArrayList<Road> ();
		this.listVehiculos = new ArrayList <Vehicle> ();
		this.mapCruces = new HashMap<String, Junction>();
		this.mapCarreteras = new HashMap<String, Road> ();
		this.mapVehiculos = new HashMap <String, Vehicle> ();
		
	}
	
	//asi???
	void addJunction(Junction j) {
		try {
			if(mapCruces.containsKey(j._id))throw new ExceptionClass();
		
		}catch ( ExceptionClass e) {
			System.out.println("Ya existe otro cruce con el mismo identificador");
		}
		
		listCruces.add(j);
		mapCruces.put(j._id, j);
	}	
	
	void addRoad(Road r) {
		try {
			
			if(mapCarreteras.containsKey(r._id)) throw new ExceptionClass();

		}catch ( ExceptionClass e) {
				System.out.println("Ya existe otro cruce con el mismo identificador");
		}
		
		//y si los cruces que conecta la carretera existen en el mapa de carreteras.
		
		try {
			int j=0;
			boolean encontrado1 = false;
			boolean encontrado2 = false;
			while(j < listCruces.size() && (!encontrado1||!encontrado2)){
					if(r.getCruceDestino() == listCruces.get(j)) {
						encontrado2= true;
					}
					if(r.getCruceOrigen() == listCruces.get(j)) {
						encontrado1=true;
					}
				
				j++;
			}
			if((encontrado1 && encontrado2) != true) throw new ExceptionClass();
			
		}catch (ExceptionClass e) {
			System.out.println("No existen todas las cruces que conecten todas las carreteras.");
		}
		
		listCarreteras.add(r);
		for(int i=0; i < listCruces.size(); i++) {
			if(listCruces.get(i)._id == r.getCruceOrigen()._id) {
				listCruces.get(i).addOutGoingRoad(r);
			}
			if(listCruces.get(i)._id == r.getCruceDestino()._id) {
				listCruces.get(i).addIncommingRoad(r);
			}
		}
		
		mapCarreteras.put(r._id, r);
	}
	
	void addVehicle(Vehicle v) {
		try {
			if(mapVehiculos.containsKey(v._id)) throw new ExceptionClass();
			
			int i = 0;
			
			while(i < v.getItinerary().size()-1) {
				if(v.getItinerary().get(i).roadTo(v.getItinerary().get(i+1))==null) throw new ExceptionClass();			
				i++;
			}
			
			}catch ( ExceptionClass e) {
				System.out.println("Ya existe otro vehiculo con el mismo identificador");
			}
		listVehiculos.add(v);
		mapVehiculos.put(v._id, v);
	}
	
	public Junction getJunction(String id) {
		
		if(mapCruces.containsKey(id)) return mapCruces.get(id);
		else return null;
		/*while( i < listCruces.size() && !encontrado) {
			if(listCruces.get(i)._id == id) {
				encontrado=true;
				return listCruces.get(i);
			}
			i++;
		}*/
		
	}
	
	public Road getRoad(String id) {
		
		if(mapCarreteras.containsKey(id)) return mapCarreteras.get(id);
		else return null;
		/*int i = 0;
		boolean encontrado=false;
		
		while( i < listCarreteras.size() && !encontrado) {
			if(listCarreteras.get(i)._id == id) {
				encontrado=true;
				return listCarreteras.get(i);
			}
			i++;
		}
	
		return null;*/
		
	}
	
	public Vehicle getVehicle(String id) {
		
		if(mapVehiculos.containsKey(id)) return mapVehiculos.get(id);
		else return null;
		/*int i = 0;
		boolean encontrado=false;
		
		while( i < listVehiculos.size() && !encontrado) {
			if(listVehiculos.get(i)._id == id) {
				encontrado=true;
				return listVehiculos.get(i);
			}
			i++;
		}*/
	}
	
	public List <Junction> getJunctions(){
		return Collections.unmodifiableList(listCruces);
		
	}
	
	public List <Road> getRoads(){
		return Collections.unmodifiableList(listCarreteras);
		
	}
	
	public List <Vehicle> getVehicles(){
		return Collections.unmodifiableList(listVehiculos);
		
	}
	
	void reset() {
		listVehiculos.clear();
		listCruces.clear();
		listCarreteras.clear();
		mapCruces.clear();
		mapVehiculos.clear();
		mapCarreteras.clear();
	}
	
	public JSONObject report() {
	
		JSONArray jun = new JSONArray();
		for(int i=0; i < listCruces.size(); i++) {
			jun.put(listCruces.get(i).report());
		}
		
		JSONArray road= new JSONArray();
		for(int j=0; j < listCarreteras.size(); j++) {
			road.put(listCarreteras.get(j).report());
		}
		
		JSONArray veh = new JSONArray();
		for(int z=0; z < listVehiculos.size(); z++) {
			veh.put(listVehiculos.get(z).report());
		}
		
		JSONObject ob1= new JSONObject();
		
		ob1.put("roads", road);
		ob1.put("vehicles", veh);
		ob1.put("junctions", jun);
		

		return ob1;
	}
	
	
	
	
	
	
	
}

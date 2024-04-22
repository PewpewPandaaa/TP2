package simulator.model;

import java.util.List;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class Road extends SimulatedObject{

	private Junction cruceOrigen;
	private Junction cruceDestino;
	private int longitud;
	private List<Vehicle> vehicle;
	private int alarmaContaminacion;
	
	protected Weather condicionAmbiental;
	protected int limiteVelocidad;
	protected int totalContaminacion;
	protected int maxVelocidad;
	
	
	protected Road(String id, Junction cruceOrigen, Junction cruceDestino, int maxVelocidad,
					int alarmaContaminacion, int longitud, Weather condicionAmbiental) {
		super(id);
		try {
			if(maxVelocidad>= 0 && longitud >=0 && alarmaContaminacion >= 0 && 
			   cruceDestino != null && cruceOrigen !=null && condicionAmbiental != null) {
				this.cruceOrigen = cruceOrigen;
				this.cruceDestino = cruceDestino;
				this.maxVelocidad = maxVelocidad;
				this.alarmaContaminacion = alarmaContaminacion;
				this.longitud = longitud;
				this.condicionAmbiental = condicionAmbiental;
				this.totalContaminacion = 0;
				this.vehicle = new ArrayList<>();
				this.limiteVelocidad = maxVelocidad;
			}else throw new ExceptionClass(); 
			
		}catch(ExceptionClass e) {
			System.out.println("Por favor, introduzca los valores correctos:");
			System.out.println("--> La velocidad m¨¢xima, longitud y alarma por contaminaci¨®n excesiva deben ser positivos");
			System.out.println("--> El cruce origen, cruce destino y las condiciones ambientales no deben ser null");
		}
		
	}
	

	void enter(Vehicle v) {	
		try {
			if(v.getLocalizacion() == 0 && v.getVelocidad() == 0) {
				vehicle.add(v);
			}
			else throw new ExceptionClass();
		}catch (ExceptionClass e) {
			System.out.println("La velocidad y la localizacion del vehiculo debe ser de valor 0");
		}
	}
	
	void exit(Vehicle v) {
		vehicle.remove(v);
	}
	
	void setCondicionAmbiental(Weather w) {
		
		try {
			if(w != null) {
				this.condicionAmbiental = w;
			}else throw new ExceptionClass();
		
		}catch(ExceptionClass e) {
			System.out.println("el tiempo atmosferico no deberia ser nulo");
		}
	
	}
	
	void addContaminacion(int c) {
		try {
			if(c >= 0)
				this.totalContaminacion+=c;
			else throw new ExceptionClass();
		}catch (ExceptionClass e){
			System.out.println("la unidad de CO2 no puede ser negativo");
		}
	}
	
	abstract void reduceTotalContamination();
	
	abstract void updateSpeedLimit();
	
	abstract int calculateVehicleSpeed(Vehicle v);
	
	@Override
	void advance(int time) {
		reduceTotalContamination();
		updateSpeedLimit();
	
		for(int i=0; i < vehicle.size(); i++){
			if(vehicle.get(i).getEstado() != VehicleStatus.WAITING) {
			vehicle.get(i).setVelocidad(calculateVehicleSpeed(vehicle.get(i)));

			//System.out.println(vehicle.get(i).getVelocidad());
			vehicle.get(i).advance(time);
			}
		}
		
		Vehicle s;
		// y ordenar los vehiculos por su localizacion final del metodo ????
		for(int i=1; i < vehicle.size(); i++) {
			for(int j = 0; j < vehicle.size() -1; j++) {
				if(vehicle.get(j).getLocalizacion() < vehicle.get(j+1).getLocalizacion()) {
					s = vehicle.get(j);
					vehicle.set(j, vehicle.get(j+1));
					vehicle.set(j+1,s);
				}
			}
		}
		
	}

	@Override
	public JSONObject report() {
		
		JSONObject ob1 = new JSONObject();
		
		
		ob1.put("speedlimit", this.limiteVelocidad);
		ob1.put("co2", this.totalContaminacion);
		ob1.put("weather", this.condicionAmbiental);
		
		JSONArray ja = new JSONArray();
		
		for (int i = 0; i < vehicle.size(); i++) {
			ja.put(vehicle.get(i).getId());
		}
		
	
		
		ob1.put("vehicles", ja);
		ob1.put("id", this._id);
	
		
		
	
		
		return ob1;	
	}


	public Weather getCondicionAmbiental() {
		return condicionAmbiental;
	}


	public int getTotalContaminacion() {
		return totalContaminacion;
	}


	public void setTotalContaminacion(int totalContaminacion) {
		this.totalContaminacion = totalContaminacion;
	}


	public Junction getCruceOrigen() {
		return cruceOrigen;
	}


	public Junction getCruceDestino() {
		return cruceDestino;
	}


	public int getLongitud() {
		return longitud;
	}


	public int getMaxVelocidad() {
		return maxVelocidad;
	}


	public int getLimiteVelocidad() {
		return limiteVelocidad;
	}


	public int getAlarmaContaminacion() {
		return alarmaContaminacion;
	}


	public List<Vehicle> getVehicle() {
		return vehicle;
	}


	private void setCruceOrigen(Junction cruceOrigen) {
		this.cruceOrigen = cruceOrigen;
	}


	private void setCruceDestino(Junction cruceDestino) {
		this.cruceDestino = cruceDestino;
	}


	private void setLongitud(int longitud) {
		this.longitud = longitud;
	}


	private void setMaxVelocidad(int maxVelocidad) {
		this.maxVelocidad = maxVelocidad;
	}


	private void setLimiteVelocidad(int limiteVelocidad) {
		this.limiteVelocidad = limiteVelocidad;
	}


	private void setAlarmaContaminacion(int alarmaContaminacion) {
		this.alarmaContaminacion = alarmaContaminacion;
	}


	private void setVehicle(List<Vehicle> vehicle) {
		this.vehicle = vehicle;
	}
	
	
}

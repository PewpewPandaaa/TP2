package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

public class Vehicle extends SimulatedObject{
	
	private List<Junction> itinerary;
	private int maxVelocidad;
	private int velocidad;
	private VehicleStatus estado;
	private Road carretera;
	private int localizacion;
	private int gradoContaminacion;
	private int totalContaminacion;
	private int distanciaTotal;
	private int indice;
	
	
	protected Vehicle(String id, int maxVelocidad, int gradoContaminacion, List<Junction> itinerary) {
		super(id);
		try {
		
			if(maxVelocidad >= 0 && gradoContaminacion >= 0 && gradoContaminacion <=10 && itinerary.size()  >= 2 ) {
				this.maxVelocidad = maxVelocidad;
				this.gradoContaminacion = gradoContaminacion;
				this.itinerary = Collections.unmodifiableList(new ArrayList<>(itinerary));
				this.totalContaminacion=0;
				this.distanciaTotal = 0;
				this.localizacion = 0;
				this.indice = 0;
				this.estado = VehicleStatus.PENDING;
		
			} else throw new ExceptionClass(); 
		
		}catch(ExceptionClass e) {
			
			System.out.println("Por favor, introduzca los valores correctos:");
			System.out.println("--> La velocidad m¨¢xima debe ser de valor positivo");
			System.out.println("--> El grado de contaminaci¨®n debe ser de valor entre 0 y 10");
			System.out.println("--> La longitud de la lista itinerario es al menos 2");	
		}
	}
	

	protected void setVelocidad(int s) {
		try {
			if(s>=0) {
				if(s < this.maxVelocidad )
					this.velocidad = s; 
				else this.velocidad = maxVelocidad;
			}
			 
			else throw new ExceptionClass();
		}catch(ExceptionClass e) {
			System.out.println("La velocidad debe ser de valor positivo");
		}
	}
	
	protected void setGradoContamination(int c) {
		try {
			if(c >=0 && c <= 10) {
				this.gradoContaminacion = c; 
			}
			else throw new ExceptionClass();
		}catch(ExceptionClass e) {
			System.out.println("El grado de contaminacion debe ser de valor entre 0 y 10");
		}
	}
	
	
	
	
	@Override
	protected void advance(int time) {
		
		int distancia = 0;
		
		if(this.estado == VehicleStatus.TRAVELING) {

			if(indice == itinerary.size()-1) {
				this.estado = VehicleStatus.ARRIVED;
				this.velocidad = 0;
			}
			if((this.localizacion + this.velocidad) <= this.carretera.getLongitud()) {
				distancia = velocidad;
				this.localizacion += velocidad;
			} else {
				distancia = this.carretera.getLongitud() - this.localizacion;
				this.localizacion = this.carretera.getLongitud();
			}
			
			this.distanciaTotal += distancia;
			
			int c = (distancia*this.gradoContaminacion);
				this.totalContaminacion+=c;
				carretera.addContaminacion(c);
			
			
			
			if(this.localizacion >= carretera.getLongitud()) {
				this.carretera.getCruceDestino().enter(this);
				this.indice++;
				this.velocidad = 0;
				estado = VehicleStatus.WAITING;
					
			}
		
		}
		
	}
	
	void moveToNextRoad() {
		
		try{
			if(this.estado == VehicleStatus.PENDING ) {
				this.carretera = itinerary.get(indice).roadTo(itinerary.get(indice+1));
				this.carretera.enter(this);
				this.estado = VehicleStatus.TRAVELING;
				
			}
			else if(this.estado == VehicleStatus.WAITING) {
				carretera.exit(this);
				this.velocidad = 0; 
				this.localizacion=0;
				if(indice == itinerary.size()-1) {
					
						this.estado = VehicleStatus.ARRIVED;
						this.carretera.exit(this);
					
				} else { 
				this.carretera = itinerary.get(indice).roadTo(itinerary.get(indice+1));
				this.carretera.enter(this);
				this.estado = VehicleStatus.TRAVELING;
				}
			}else if(this.estado == VehicleStatus.ARRIVED) {
				this.maxVelocidad = 0;
				this.localizacion = 0;
			} 
			else throw new ExceptionClass();
		}catch (ExceptionClass e) {
			System.out.println("No es posible entrar a ninguna carretera.");
		}
			
		
	}

	@Override
	public JSONObject report() {
		
		JSONObject ob1 = new JSONObject();
		
		if(this.estado == VehicleStatus.ARRIVED) {
			ob1.put("distance", this.distanciaTotal);
			ob1.put("co2", this.totalContaminacion);
			ob1.put("id", this._id);
			ob1.put("class", this.gradoContaminacion);
			ob1.put("speed", this.velocidad);
			ob1.put("status", this.estado);
			
		}else {
		
		ob1.put("distance", this.distanciaTotal);
		ob1.put("road", this.carretera);
		ob1.put("co2", this.totalContaminacion);
		ob1.put("location", this.localizacion);
		ob1.put("id", this._id);
		ob1.put("class", this.gradoContaminacion);
		ob1.put("speed", this.velocidad);
		ob1.put("status", this.estado);
	
		}
		
		return ob1;	
	}


	public List<Junction> getItinerary() {
		return itinerary;
	}


	public int getMaxVelocidad() {
		return maxVelocidad;
	}


	public int getVelocidad() {
		return velocidad;
	}


	public VehicleStatus getEstado() {
		return estado;
	}


	public Road getCarretera() {
		return carretera;
	}


	public int getLocalizacion() {
		return localizacion;
	}


	public int getGradoContaminacion() {
		return gradoContaminacion;
	}


	public int getTotalContaminacion() {
		return totalContaminacion;
	}


	public int getDistanciaTotal() {
		return distanciaTotal;
	}


	private void setItinerary(List<Junction> itinerary) {
		this.itinerary = itinerary;
	}


	private void setMaxVelocidad(int maxVelocidad) {
		this.maxVelocidad = maxVelocidad;
	}


	private void setEstado(VehicleStatus estado) {
		this.estado = estado;
	}


	private void setCarretera(Road carretera) {
		this.carretera = carretera;
	}


	private void setLocalizacion(int localizacion) {
		this.localizacion = localizacion;
	}


	private void setGradoContaminacion(int gradoContaminacion) {
		this.gradoContaminacion = gradoContaminacion;
	}


	private void setTotalContaminacion(int totalContaminacion) {
		this.totalContaminacion = totalContaminacion;
	}


	private void setDistanciaTotal(int distanciaTotal) {
		this.distanciaTotal = distanciaTotal;
	}



}

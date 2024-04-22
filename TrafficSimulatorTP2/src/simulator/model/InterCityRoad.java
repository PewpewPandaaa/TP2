package simulator.model;

public class InterCityRoad extends Road{

		
	private int speedLimit;
	InterCityRoad(String id, Junction cruceOrigen, Junction cruceDestino, int maxVelocidad,
			int alarmaContaminacion, int longitud, Weather condicionAmbiental) {
		
		super(id, cruceOrigen, cruceDestino, maxVelocidad, alarmaContaminacion, longitud,
				condicionAmbiental);
		this.speedLimit = maxVelocidad;
	}

	@Override
	void reduceTotalContamination() {
		
		if(this.condicionAmbiental == Weather.SUNNY) {
			this.totalContaminacion = (int)(((100.0-2.0)/100.0)*this.totalContaminacion);
			
		}else if(this.condicionAmbiental == Weather.CLOUDY){
			
			this.totalContaminacion = (int)(((100.0-3.0)/100.0)*this.totalContaminacion);
			
		}else if(this.condicionAmbiental == Weather.RAINY) {
			
			this.totalContaminacion = (int)(((100.0-10.0)/100.0)*this.totalContaminacion);
			
		}else if(this.condicionAmbiental == Weather.WINDY) {
			
			this.totalContaminacion = (int)(((100.0-15.0)/100.0)*this.totalContaminacion);
		
		}else 
			this.totalContaminacion = (int)(((100.0-20.0)/100.0)*this.totalContaminacion);

	}

	@Override
	void updateSpeedLimit() {
		if((this.totalContaminacion > this.getAlarmaContaminacion()) && speedLimit <= maxVelocidad ) {
			this.maxVelocidad =  (int)(this.maxVelocidad*0.5);
			this.limiteVelocidad = maxVelocidad;
		}
	}

	@Override
	int calculateVehicleSpeed(Vehicle v) {
		
		if(this.condicionAmbiental == Weather.STORM) {
			return (int) (v.getMaxVelocidad()*0.2);
		}else 
			return this.maxVelocidad;
		
	}
	
	

}

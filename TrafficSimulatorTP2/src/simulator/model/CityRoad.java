package simulator.model;

public class CityRoad extends Road {

	CityRoad(String id, Junction cruceOrigen, Junction cruceDestino, int maxVelocidad, int alarmaContaminacion,
			int longitud, Weather condicionAmbiental) {
		super(id, cruceOrigen, cruceDestino, maxVelocidad, alarmaContaminacion, longitud, condicionAmbiental);
		
	}

	@Override
	void reduceTotalContamination() {
		if(this.condicionAmbiental == Weather.STORM || this.condicionAmbiental == Weather.WINDY) {
			this.totalContaminacion -=10;
			
		}else this.totalContaminacion-=2;
		
		
		if(this.totalContaminacion < 0) {
			this.totalContaminacion = 0;
		}
		
	}

	@Override
	void updateSpeedLimit() {
	}

	@Override
	int calculateVehicleSpeed(Vehicle v) {
		return (int)(((11.0-v.getGradoContaminacion())/11.0)*this.limiteVelocidad);
	}
	
}

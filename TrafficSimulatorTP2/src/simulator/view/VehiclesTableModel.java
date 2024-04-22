package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;

public class VehiclesTableModel extends AbstractTableModel implements  TrafficSimObserver{
	
	private String[] _colNames = {"Id", "Location", "Itirinary", "CO2 Class", 
								"MaxSpeed", "Speed", "TotalCO2", "Distance"};
	private List<Vehicle> _vehicle;
	
	VehiclesTableModel(Controller ctrl){
		ctrl.addObserver(this);
		_vehicle = new ArrayList<>();
	}
	
	public void update() {
		fireTableDataChanged();;		
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

	//si no pongo esto no coge el nombre de las columnas
	//
	//this is for the column header
	@Override
	public String getColumnName(int col) {
		return _colNames[col];
	}

	@Override
	// m¨¦todo obligatorio, probad a quitarlo, no compila
	//
	// this is for the number of columns
	public int getColumnCount() {
		return _colNames.length;
	}

	@Override
	// m¨¦todo obligatorio
	//
	// the number of row, like those in the events list
	public int getRowCount() {
		return _vehicle == null ? 0 : _vehicle.size();
	}

	@Override
	// m¨¦todo obligatorio
	// as¨ª es como se va a cargar la tabla desde el ArrayList
	// el ¨ªndice del arrayList es el n¨²mero de fila pq en este ejemplo
	// quiero enumerarlos.
	//
	// returns the value of a particular cell 
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object s = null;
		switch (columnIndex) {
		/*case 0:
			s = rowIndex;
			break;*/
		case 0:
			s = _vehicle.get(rowIndex).getId();
			break;
		
		case 1:
			s = _vehicle.get(rowIndex).getCarretera() + ": " + _vehicle.get(rowIndex).getLocalizacion();
			break;
		
		case 2:  
			s = _vehicle.get(rowIndex).getItinerary();
			break;
		
		case 3: 
			s = _vehicle.get(rowIndex).getGradoContaminacion();
			break;
			
		case 4:
			s = _vehicle.get(rowIndex).getMaxVelocidad();
			break;
			
		case 5: 
			s =_vehicle.get(rowIndex).getVelocidad();
			break;
		
		case 6: 
			s = _vehicle.get(rowIndex).getTotalContaminacion();
			break;
			
		case 7:
			s = _vehicle.get(rowIndex).getDistanciaTotal();
			break;
		}
		return s;
	}



	//entodas las tablas hay que utilizar fireTableDataChanged()

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		_vehicle=map.getVehicles();
		update();
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		_vehicle=map.getVehicles();
		update();
		
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		_vehicle=map.getVehicles();
		update();
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		_vehicle=map.getVehicles();
		update();
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		_vehicle=map.getVehicles();
		update();
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}


}

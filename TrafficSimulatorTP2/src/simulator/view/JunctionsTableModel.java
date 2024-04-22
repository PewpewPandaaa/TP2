package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class JunctionsTableModel extends AbstractTableModel implements  TrafficSimObserver {

	private List<Junction> _junction;
	private String[] _colNames = {"Id", "Green", "Queues"};
	
	public JunctionsTableModel(Controller ctrl) {
		ctrl.addObserver(this);
		_junction = new ArrayList<>();
	}
	
	public void update() {
		fireTableDataChanged();		
	}
	
	/*public void setEventsList(List<Junction> junction) {
		_junction = junction;
		update();
	}*/

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
		return _junction == null ? 0 : _junction.size();
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
		case 0:
			s = _junction.get(rowIndex).getId();
			break;
		case 1:
			if(_junction.get(rowIndex).getIndiceSemaforoVerde() == -1) {
				s = "NULL";
			}
			else {  
			int t = _junction.get(rowIndex).getIndiceSemaforoVerde();
			s = _junction.get(rowIndex).getListaCarreteras().get(t);
			}break;
			
		case 2:
			if(_junction.get(rowIndex).getListaCarreteras().size() == 0) {
				s = null;
			}else  {
				int i=0;
				for(Road r: _junction.get(rowIndex).getListaCarreteras()) {
					if(i == 0)
					s = r.getId() + ":" + _junction.get(rowIndex).getMapCola().get(r)+ " ";
					else s += r.getId() + ":" + _junction.get(rowIndex).getMapCola().get(r)+ " ";
	
					i++;
				}
			}
		}
		return s;
	}



	//entodas las tablas hay que utilizar fireTableDataChanged()

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		update();
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		//setEventsList(ev);
		_junction = map.getJunctions();
		//this._junction=events;
		update();
		
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		//setEventsList(events);
		//this._events=events;
			update();
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		//this._events=events;
		update();
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		update();
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}
}

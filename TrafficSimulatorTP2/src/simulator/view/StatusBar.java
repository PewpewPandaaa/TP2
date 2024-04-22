package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.TrafficSimulator;

public class StatusBar extends JPanel implements TrafficSimObserver{
	
	private TrafficSimulator sim;
	private JLabel tiempo;
	private JLabel event;
	private Event evento;
	
	public StatusBar(Controller ctrl) {
		ctrl.addObserver(this);
		initGUI();
	}
	
	public void initGUI() {
		JToolBar textPanel = new JToolBar();
		setLayout(new BorderLayout());
		
		tiempo = new JLabel(" " + 0);
		textPanel.add(new JLabel("Time:"));
		textPanel.add(tiempo);
		
		
		textPanel.addSeparator(new Dimension(100, 0));
		event = new JLabel(" ");
		event.setText("Welcome!!");
		textPanel.add(event);
	
		
		this.add(textPanel);
		textPanel.setVisible(true);
	}
	
	public void setPanel(int time) {
		tiempo.setText(" " + time);
		
	}
	
	public void setEventos(Event e) {
		event.setText(" Event Added :" + e.toString());
	}
	
	public void setNull() {
		event.setText(" ");
	}
	

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		setPanel(time);
		setNull();
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		setPanel(time);
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		setEventos(e);
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		setPanel(0);
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}

}

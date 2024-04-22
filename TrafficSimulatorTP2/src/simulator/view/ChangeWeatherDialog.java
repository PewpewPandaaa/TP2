package simulator.view;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import simulator.model.Road;
import simulator.model.Weather;

public class ChangeWeatherDialog extends JDialog{

	private int _status;
	private JSpinner ticks;
	private DefaultComboBoxModel <Road> road;
	private DefaultComboBoxModel <Weather> tiempo;
	private JComboBox<Road> _road;
	private JComboBox<Weather> _tiempo;
	
	private List<Road> carreteras;
	private List<Weather> weathers;
	
	public ChangeWeatherDialog(Frame frame, List<Road> carretera, List <Weather> weather) {
		super(frame, true);
		initGUI();
		carreteras = carretera;
		weathers = weather;
	}
	
	

	private void initGUI() {
		
		setTitle("Change Road Weather");
		JPanel weather = new JPanel();
		weather.setLayout(new BoxLayout(weather, BoxLayout.Y_AXIS));
		setContentPane(weather);
		
		weather.add(Box.createRigidArea(new Dimension(0,20)));
		
		JLabel label = new JLabel("Seleccione el tiempo de la carretera");
		label.setAlignmentX(CENTER_ALIGNMENT);
		weather.add(label);
		
		weather.add(Box.createRigidArea(new Dimension(0,5)));
		
		JPanel viewsPanel = new JPanel();
		viewsPanel.setAlignmentX(CENTER_ALIGNMENT);
		weather.add(viewsPanel);
		
		
		road = new DefaultComboBoxModel<>();
		_road = new JComboBox<>(road);
	
		_road.addActionListener(new  ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		viewsPanel.add(new JLabel("Road: "));
		viewsPanel.add(_road);
		
		tiempo = new DefaultComboBoxModel<>();
		_tiempo = new JComboBox<>(tiempo);
		
		_tiempo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		viewsPanel.add(new JLabel("Weather: "));
		viewsPanel.add(_tiempo);
		
		ticks = new JSpinner (new SpinnerNumberModel(1, 1, 10000, 1));
		viewsPanel.add(new JLabel("Ticks: "));
		viewsPanel.add(ticks);
		
		weather.add(Box.createRigidArea(new Dimension(0,20)));
		
		weather.add(viewsPanel);
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setAlignmentX(CENTER_ALIGNMENT);
		weather.add(buttonsPanel);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				_status = 0;
				ChangeWeatherDialog.this.setVisible(false);
			}
		
		});
		buttonsPanel.add(cancelButton);
		
		JButton okButton = new JButton("ok");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				_status = 1;
				ChangeWeatherDialog.this.setVisible(false);
			}
		});
		buttonsPanel.add(okButton);
		
		setPreferredSize(new Dimension(500,200));
		pack();
		setResizable(false);
		setVisible(false);
		
	}
	

	public int open(){
		
		road.removeAllElements();
		for(Road r: carreteras) {
			road.addElement(r);
		}
		
		tiempo.removeAllElements();
		for(Weather t: weathers) {
			tiempo.addElement(t);
		}
		
		setVisible(true);
		return _status;
	}
	
	public Weather getWeather() {
		return (Weather) tiempo.getSelectedItem();
	}
	
	public Road getRoad() {
		return (Road) road.getSelectedItem();
	}
	
	public Integer getTicks() {
		return (Integer)ticks.getValue();
	}
	
	
}

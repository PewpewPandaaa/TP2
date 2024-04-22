package simulator.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.launcher.Main;
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.NewSetContClassEvent;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.SetWeatherEvent;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;
import simulator.model.Weather;

public class ControlPanel extends JPanel implements TrafficSimObserver{
	private Controller ctrl;
	
	
	private JFileChooser fc;
	private JButton fileButton, contButton, atmButton, startButton, stopButton, exitButton;
	private JSpinner time;
	private boolean _stopped;
	private List <Vehicle> vehiculo;
	private List <Road> carretera;
	private int tiempo = 0;
	
	
	public ControlPanel(Controller ctrl) {
		
		ctrl.addObserver(this);
		this.ctrl = ctrl;
		vehiculo = new ArrayList<>();
		carretera = new ArrayList<>();
		initGui();
		
	}
	
	private void initGui() {
		
		JToolBar controlPanel = new JToolBar();
		controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.X_AXIS));
		setLayout(new BorderLayout());
	
		
		fileButton = new JButton(new ImageIcon("resources/icons/open.png"));
		fileButton.setAlignmentX(LEFT_ALIGNMENT);
		addButton(fileButton, controlPanel);

		fileButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				try {
					select_file(event);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
	
		
		contButton = new JButton(new ImageIcon("resources/icons/co2class.png"));
		addButton(contButton, controlPanel);
		contButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				select_co2();
			}
		});
	
		
		atmButton = new JButton(new ImageIcon("resources/icons/weather.png"));
		addButton(atmButton, controlPanel);
		atmButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				select_weather();
			}
		});
	
		
		startButton = new JButton(new ImageIcon("resources/icons/run.png"));
		addButton(startButton, controlPanel);
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				select_play();
				run_sim(((Integer)time.getValue()));
				//enableToolBar(true);
			}
		});
		
		
		
		stopButton = new JButton(new ImageIcon("resources/icons/stop.png"));
		addButton(stopButton, controlPanel);
		stopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				enableToolBar(true);
			}
		
		});

		time = new JSpinner (new SpinnerNumberModel(10,1,10000,1));
		controlPanel.add(new JLabel("Ticks:"));
		time.setMaximumSize(new Dimension(80,40));
		time.setMinimumSize(new Dimension(80,40));
		controlPanel.add(time);
		
		
		exitButton = new JButton(new ImageIcon("resources/icons/exit.png"));
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				select_quit();
			}
		});
		
	
		exitButton.add(Box.createGlue());
		controlPanel.addSeparator(new Dimension(595,0));
		controlPanel.add(exitButton);

		this.add(controlPanel);
		
	}
	

	private void select_quit() {
		
		int n = JOptionPane.showOptionDialog(new JFrame(), 
				"Are you sure want to quit?", "Quit",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
				null, null);
		if(n==0) {
			System.exit(0);
		}
		
	}
	

	public void select_file(ActionEvent e) throws FileNotFoundException {
		fc = new JFileChooser("resources\\examples");
		int returnVal = fc.showOpenDialog(this);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			try{
				ctrl.reset();
				File file = fc.getSelectedFile();
				Main.set_inFile(file.getName());
				ctrl.loadEvents(new FileInputStream(file));
			}catch(Exception E){
				JOptionPane.showMessageDialog(fc, "Error al abrir archivo");
			}
			}
	
	}
	
	private void select_co2() {
		List <Integer> grado = new ArrayList<Integer>();
		for(int i=1; i <=10 ; i++) {
			grado.add(i);
		}
		
		ChangeCO2ClassDialog co2 = new ChangeCO2ClassDialog(null, vehiculo, grado);
		
		int status = co2.open();
	
		if(status == 1) {
				List<Pair<String, Integer>> cont =  new ArrayList<Pair<String, Integer>>();
				cont.add(new Pair<String, Integer>(co2.getVehicle().getId(),co2.getGrado()));
				ctrl.addEvent(new NewSetContClassEvent(tiempo + co2.getTicks(), cont));
		}	
		
	}
	
	private void select_weather() {
		
		List<Weather> weathers = new ArrayList<Weather>();
		weathers.add(Weather.CLOUDY);
		weathers.add(Weather.STORM);
		weathers.add(Weather.RAINY);
		weathers.add(Weather.SUNNY);
		weathers.add(Weather.WINDY);
		
		ChangeWeatherDialog weather = new ChangeWeatherDialog(null, carretera, weathers);
		int status = weather.open();
		if(status == 1) {
			
			List<Pair<String, Weather>> wr =  new ArrayList<Pair<String, Weather>>();
			wr.add(new Pair<String, Weather>(weather.getRoad().getId(), weather.getWeather()));
			ctrl.addEvent(new SetWeatherEvent(tiempo + weather.getTicks(), wr));
		}
	}
	
	private void select_play() {
		enableToolBar(false);
		_stopped =false;
	}

	private void addButton(JButton button, Container container) {
		button.setAlignmentX(Component.LEFT_ALIGNMENT);
		container.add(button);
		
	}
	
	private void run_sim(int n) {
		
		if (n > 0 && !_stopped) { 
			try { 
				ctrl.run(1,null); 
			
			} catch (Exception e) { 
				// TODO show error message
				_stopped = true;
				return; 
				}
			SwingUtilities.invokeLater(new Runnable() {
				@Override 
	
					public void run() { 
						run_sim(n - 1); 
					} 
			}); 
		} else {
			enableToolBar(true); 
			_stopped = true; 
		}
	
	}
	
 	
	private void enableToolBar(boolean b) {
		// TODO Auto-generated method stub
		fileButton.setEnabled(b);
		atmButton.setEnabled(b);
		contButton.setEnabled(b);
		startButton.setEnabled(b);
		exitButton.setEnabled(b);
	
	}

	private void stop() {
		_stopped = true; 
	}
			
		
	
	
	
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		vehiculo = map.getVehicles();
		carretera = map.getRoads();
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		vehiculo = map.getVehicles();
		carretera = map.getRoads();
		tiempo = time;
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		vehiculo = map.getVehicles();
		carretera = map.getRoads();
		tiempo = time;
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		vehiculo = map.getVehicles();
		carretera = map.getRoads();
		tiempo = time;
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


package simulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;
import simulator.model.VehicleStatus;
import simulator.model.Weather;

public class MapByRoadComponent extends JComponent implements TrafficSimObserver {
	
	private RoadMap _map;
	
	private static final int _JRADIUS = 10;
	private Image _car;
	private Image[] cont;
	private Image[] weather;
	
	private static final Color _BG_COLOR = Color.WHITE;
	private static final Color _JUNCTION_COLOR = Color.BLUE;
	private static final Color _JUNCTION_LABEL_COLOR = new Color(200, 100, 0);
	private static final Color _GREEN_LIGHT_COLOR = Color.GREEN;
	private static final Color _RED_LIGHT_COLOR = Color.RED;
	
	
	MapByRoadComponent(Controller ctrl) {
		this.setPreferredSize(new Dimension(300,200));
		_car = loadImage("car.png");
		loadImagenContaminacion();
		loadImageWeather();
		ctrl.addObserver(this);
	}
	
	private void loadImageWeather() {
		// TODO Auto-generated method stub
		weather = new Image[5];
		weather[0] = loadImage("rain.png");
		weather[1] = loadImage("sun.png");
		weather[2] = loadImage("cloud.png");
		weather[3] = loadImage("storm.png");
		weather[4] = loadImage("wind.png");
	}

	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D g= (Graphics2D) graphics;
		
		g.setColor(Color.WHITE);
		g.clearRect(0, 0, getWidth(), getHeight());
		
		if(_map == null || _map.getRoads().size() == 0) {
				g.setColor(Color.red);
				g.drawString("No map yet!", getWidth()/ 2- 50, getHeight()/2);
		}else {
			//updatePrefferedSize();
			drawMap(g);
		}
		
	}
	
	private void loadImagenContaminacion() {
		 cont = new Image[6];
		 for(int i = 0; i < 6; i++) {
			cont[i] = loadImage("cont_" + i + ".png");
		 }
	}
	
	private void drawMap(Graphics g) {
		draw(g);
//		drawVehicles(g);
//		drawJunction(g);
	}
	
	private void draw(Graphics g) {
		int i=0;
		for(Road r: _map.getRoads()) {
			
			int x1 = 50;
			int y1 = (i+1)*50;
			int x2 = getWidth()-100;
			
			
			g.setColor(Color.BLACK);
			g.drawLine(x1, y1, x2, y1);
			g.drawString(r.getId(), x1-25, y1);
		
			
			g.setColor(_JUNCTION_COLOR);
			g.fillOval(x1 - _JRADIUS / 2, y1 - _JRADIUS / 2, _JRADIUS, _JRADIUS);
			g.setColor(_JUNCTION_LABEL_COLOR);
			g.drawString(r.getCruceOrigen().getId(), x1, y1);
			
			Color arrowColor = _RED_LIGHT_COLOR;
			int idx = r.getCruceDestino().getIndiceSemaforoVerde();
			if (idx != -1 && r.equals(r.getCruceDestino().getListaCarreteras().get(idx))) {
				arrowColor = _GREEN_LIGHT_COLOR;
			}
		
			g.setColor(arrowColor);
			g.fillOval(x2 - _JRADIUS / 2, y1 - _JRADIUS / 2, _JRADIUS, _JRADIUS);
			g.drawString(r.getCruceDestino().getId(), x2, y1);
		
			
			for(Vehicle v : r.getVehicle()) {
				int x;
				if(v.getEstado() != VehicleStatus.ARRIVED) {
					 x = x1 + (int) ((x2 - x1) * ((double) v.getLocalizacion() / (double) v.getCarretera().getLongitud()));
					 g.drawImage(_car, x, y1-12, 18, 18, this);
					 g.drawString(v.getId(), x, y1-10);
				}
			}
			if(r.getCondicionAmbiental() == Weather.RAINY) {
				g.drawImage(weather[0], x2+5, y1-12, 32, 32, this);
			}
			else if(r.getCondicionAmbiental() == Weather.SUNNY) {
				g.drawImage(weather[1], x2+5, y1-12, 32, 32, this);
			}
			else if(r.getCondicionAmbiental() == Weather.CLOUDY) {
				g.drawImage(weather[2], x2+5, y1-12, 32, 32, this);
			}
			else if(r.getCondicionAmbiental() == Weather.STORM) {
				g.drawImage(weather[3], x2+5, y1-12, 32, 32, this);
			}
			else if(r.getCondicionAmbiental() == Weather.WINDY) {
				g.drawImage(weather[4], x2+5, y1-12, 32, 32, this);
			}
			
			
			int c = (int) Math.floor(Math.min((double)r.getTotalContaminacion() /(1.0 + (double) r.getAlarmaContaminacion()),1.0 / 0.19));
			
			g.drawImage(cont[c], x2+40, y1-12, 32, 32, this);
			
			i++;
		}
	}
	
	/*private void drawJunction(Graphics g) {
		for(Junction j: _map.getJunctions()){
			j
	}
		
	}

	private void drawVehicles(Graphics g) {
		for(Vehicle v: _map.getVehicles()) {
			if(v.getEstado() != VehicleStatus.ARRIVED) { 
				
				/*Road r = v.getCarretera();
				int x1= r.getCruceOrigen().getX();
				int y1 = r.getCruceOrigen().getY();
				int x2 = r.getCruceDestino().getX();
				int y2 = r.getCruceDestino().getY();
				
				double roadLength = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
				double alpha = Math.atan(((double) Math.abs(x1 - x2)) / ((double) Math.abs(y1 - y2)));
				double relLoc = roadLength * ((double) v.getLocalizacion()) / ((double) r.getLongitud());
				double x = Math.sin(alpha) * relLoc;
				double y = Math.cos(alpha) * relLoc;
				int xDir = x1 < x2 ? 1 : -1;
				int yDir = y1 < y2 ? 1 : -1;

				int vX = x1 + xDir * ((int) x);
				int vY = y1 + yDir * ((int) y);

				// Choose a color for the vehcile's label and background, depending on its
				// contamination class
				int vLabelColor = (int) (25.0 * (10.0 - (double) v.getGradoContaminacion()));
				g.setColor(new Color(0, vLabelColor, 0));

				// draw an image of a car (with circle as background) and it identifier
				g.fillOval(vX - 1, vY - 6, 14, 14);
				//g.drawImage(_car, vX, vY - 6, 12, 12, this);
				g.drawString(v.getId(), vX, vY - 6);
				
			}
		}
	}*/
	
	/*private void drawJunctions(Graphics g) {
		for(Junction j: _map.getJunctions()) {
			int x = j.getX();
			int y = j.getY();
			
			// draw a circle with center at (x,y) with radius _JRADIUS
			g.setColor(_JUNCTION_COLOR);
			g.fillOval(x - _JRADIUS / 2, y - _JRADIUS / 2, _JRADIUS, _JRADIUS);

			// draw the junction's identifier at (x,y)
			g.setColor(_JUNCTION_LABEL_COLOR);
			g.drawString(j.getId(), x, y);
		}
	}*/
	
	public void update(RoadMap map) {
		_map=map;
		repaint();
	}
	
	private Image loadImage(String img) {
		Image i = null;
		try {
			return ImageIO.read(new File("resources/icons/" + img));
		} catch (IOException e) {
		}
		return i;
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		update(map);
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		update(map);
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		update(map);
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		update(map);
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}
	
}

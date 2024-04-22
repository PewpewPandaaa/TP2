package simulator.view;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import simulator.model.Vehicle;

public class ChangeCO2ClassDialog extends JDialog{
	
	private int _status;
	private JSpinner ticks;
	private DefaultComboBoxModel <Integer> grado;
	private JComboBox<Integer> _grado;
	
	private DefaultComboBoxModel <Vehicle> vh;
	private JComboBox<Vehicle> _vh;
	private List<Vehicle> vehicle;
	private List<Integer> grade;
	
	public ChangeCO2ClassDialog(Frame parent, List<Vehicle> vehiculo, List<Integer> grado) {
		super(parent, true);
		initGUI();
		vehicle = vehiculo;
		grade =  grado;
	} 
	
	private void initGUI() {
		setTitle("Change CO2 Class");
		JPanel co2 = new JPanel();
		co2.setLayout(new BoxLayout(co2, BoxLayout.Y_AXIS));
		setContentPane(co2);
		
		co2.add(Box.createRigidArea(new Dimension(0,20)));
		
		JLabel label = new JLabel("Seleccione un veh¨ªculo para cambiar el CO2 liberado");
		label.setAlignmentX(CENTER_ALIGNMENT);
		co2.add(label);
		
		co2.add(Box.createRigidArea(new Dimension(0,5)));
		
		JPanel viewsPanel = new JPanel();
		viewsPanel.setAlignmentX(CENTER_ALIGNMENT);

		vh = new DefaultComboBoxModel<> ();
		_vh = new JComboBox<>(vh);
		_vh.addActionListener(new  ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}	
		});
		
		grado = new DefaultComboBoxModel<>();
		_grado = new JComboBox<>(grado);
		
		viewsPanel.add(new JLabel("Vehicles: "));
		viewsPanel.add(_vh);
		
		viewsPanel.add(new JLabel("CO2 Class: "));
		viewsPanel.add(_grado);
		
		ticks = new JSpinner (new SpinnerNumberModel(1, 1, 10000, 1));
		viewsPanel.add(new JLabel("Ticks: "));
		viewsPanel.add(ticks);
		
		co2.add(Box.createRigidArea(new Dimension(0,20)));
		
		co2.add(viewsPanel);
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setAlignmentX(CENTER_ALIGNMENT);
		co2.add(buttonsPanel);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				_status = 0;
				ChangeCO2ClassDialog.this.setVisible(false);
			}
		
		});
		buttonsPanel.add(cancelButton);
		
		JButton okButton = new JButton("ok");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				_status = 1;
				ChangeCO2ClassDialog.this.setVisible(false);
				
			}
		});
		buttonsPanel.add(okButton);

		setPreferredSize(new Dimension(500,200));
		pack();
		setLocationRelativeTo(co2);
		setResizable(false);
		setVisible(false);
	}
	
	public int open(){
		vh.removeAllElements();
		for(Vehicle v : vehicle) {
			vh.addElement(v);
		}
		
		grado.removeAllElements();
		for(Integer i : grade) {
			grado.addElement(i);
		}
		setLocation(getParent().getLocation().x + 10, getParent().getLocation().y + 10);
		setVisible(true);
		
		return _status;
	}
	
	public Vehicle getVehicle() {
		return (Vehicle)vh.getSelectedItem();
	}
	
	public Integer getGrado() {
		return (Integer) grado.getSelectedItem();
	}
	
	public Integer getTicks() {
		return (Integer)ticks.getValue();
	}
	
	
	

}

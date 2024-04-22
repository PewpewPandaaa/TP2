package simulator.misc;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class PanelExample extends JFrame {

	public PanelExample() {
		super("[=] There's a JPanel in here! [=]");
		initGUI();
	}

	private void initGUI() {
		JPanel mainPanel = new JPanel();
		mainPanel.setOpaque(true);
		
		mainPanel.setBackground(Color.BLUE);
	
		this.setContentPane(mainPanel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(290, 100);
		this.setVisible(true);
		

		JLabel tiempo = new JLabel("tiempo");
		mainPanel.add(tiempo);
		
		
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new PanelExample();
			}
		});
	}
}


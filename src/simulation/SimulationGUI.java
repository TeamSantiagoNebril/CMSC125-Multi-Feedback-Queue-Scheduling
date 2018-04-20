package simulation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SimulationGUI extends JFrame {
	
	private JPanel main;
	private JPanel sideMain;
	private JPanel resultsMain;
	private JTextField numberOfQueuesField;
	private JTextField processIDField;
	private JTextField arrivalTimeField;
	private JTextField burstTimeField;
	private JTextField priorityField;

	
	public SimulationGUI() {
		super("MultiLevel Feedback Queue Analyzer");
		setLayout(new BorderLayout());
	    setSize(1000, 550);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setResizable(false);
	    setLocationRelativeTo(null);

	    
		main = new JPanel();
		main.setLayout(new BorderLayout());
		add(main, BorderLayout.CENTER); // Always add contents to this main
	    initSideBar();
	    initMain();
	    add(main, BorderLayout.CENTER);
	    main.setBackground(Color.GREEN);
	    setVisible(true);
	}
	
	public void initSideBar() {
		/*
		 * Currently For Testing
		 * 
		 * */
		JPanel reminderPanel = new JPanel();
		reminderPanel.setLayout(new BorderLayout());
		JLabel reminder = new JLabel("Currently build set permanently to 1 Queue");
		reminder.setHorizontalAlignment(JLabel.CENTER);
		reminderPanel.add(reminder, BorderLayout.CENTER);
		reminderPanel.setBackground(Color.GRAY);
		add(reminderPanel, BorderLayout.NORTH);
		
		sideMain = new JPanel();
		sideMain.setLayout(new GridLayout(15, 0));
		JLabel sideTitle = new JLabel("               MLFQ Control Bar               ");
		sideTitle.setHorizontalAlignment(JLabel.CENTER);
		sideMain.add(sideTitle);
		
		JLabel numQueuesLabel = new JLabel("Number of Queues: ");
		numberOfQueuesField = new JTextField("1",10);
		numberOfQueuesField.setHorizontalAlignment(JTextField.CENTER);
		numberOfQueuesField.setEditable(false);
		sideMain.add(numQueuesLabel);
		sideMain.add(numberOfQueuesField);
		
		JLabel processIDLabel = new JLabel("Process ID(Integer): ");
		processIDField = new JTextField(10);
		processIDField.setHorizontalAlignment(JTextField.CENTER);
		sideMain.add(processIDLabel);
		sideMain.add(processIDField);
		
		JLabel arrivalTimeLabel = new JLabel("Arrival Time: ");
		arrivalTimeField = new JTextField(10);
		arrivalTimeField.setHorizontalAlignment(JTextField.CENTER);
		sideMain.add(arrivalTimeLabel);
		sideMain.add(arrivalTimeField);
		
		JLabel burstTimeLabel = new JLabel("Burst Time: ");
		burstTimeField = new JTextField(10);
		burstTimeField.setHorizontalAlignment(JTextField.CENTER);
		
		sideMain.add(burstTimeLabel);
		sideMain.add(burstTimeField);
		
		main.add(sideMain,BorderLayout.WEST);
		
		
		
	}
	
	public void initMain() {
		
	}
}

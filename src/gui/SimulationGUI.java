package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import mlfq.MLFQ;
import process.GanttChart;

public class SimulationGUI extends JFrame {
	
	private JPanel main;
	private JPanel leftSideMain;
	private JPanel rightSideMain;
	private JPanel resultsMain;
	private JPanel upperMain;
	private JPanel lowerMain;
	
	private JTextField numberOfQueuesField;
	private JTextField algoOfQueuesField;
	private JTextField timeSliceField;
	private JTextField priorityPolicyField;
	private JTextField entryQueueField;
	
	private JTextField processIDField;
	private JTextField arrivalTimeField;
	private JTextField burstTimeField;
	private JTextField priorityField;
	
	private JButton submitButton;
	
	private JTextArea ganttChartArea;

	private JLabel[] performanceLabels;
	private JTextField[] performanceTextFields;
	
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
	    initLeftSideBar();
	    initRightSideBar();
	    initMain();
	    add(main, BorderLayout.CENTER);
	    main.setBackground(Color.GREEN);
	    setVisible(true);
	}
	
	public void initLeftSideBar() {
		/*
		 * Currently For Testing
		 * 
		 * */
/*		JPanel reminderPanel = new JPanel();
		reminderPanel.setLayout(new BorderLayout());
		JLabel reminder = new JLabel("Currently build set permanently to 1 Queue");
		reminder.setHorizontalAlignment(JLabel.CENTER);
		reminderPanel.add(reminder, BorderLayout.CENTER);
		reminderPanel.setBackground(Color.GRAY);
		add(reminderPanel, BorderLayout.NORTH);*/
		
		leftSideMain = new JPanel();
		leftSideMain.setLayout(new GridLayout(15, 0));
		leftSideMain.setBackground(Color.GRAY);
		JLabel sideTitle = new JLabel("               MLFQ Control Bar               ");
		sideTitle.setHorizontalAlignment(JLabel.CENTER);
		leftSideMain.add(sideTitle);
		
		JLabel numQueuesLabel = new JLabel("Number of Queues: ");
		numberOfQueuesField = new JTextField("1",12);
		numberOfQueuesField.setHorizontalAlignment(JTextField.CENTER);
		leftSideMain.add(numQueuesLabel);
		leftSideMain.add(numberOfQueuesField);
		
		JLabel algoQueuesLabel = new JLabel("Algorithm of Queues: ");
		algoOfQueuesField = new JTextField("",12);
		algoOfQueuesField.setHorizontalAlignment(JTextField.CENTER);
		leftSideMain.add(algoQueuesLabel);
		leftSideMain.add(algoOfQueuesField);
		
		JLabel timeSliceLabel = new JLabel("TimeSlice(Round Robin Queue): ");
		timeSliceField = new JTextField("",12);
		timeSliceField.setHorizontalAlignment(JTextField.CENTER);
		leftSideMain.add(timeSliceLabel);
		leftSideMain.add(timeSliceField);
		
		
		JLabel prioPolicyLabel = new JLabel("MLFQ Priority Type(1/0): ");
		priorityPolicyField = new JTextField(12);
		priorityPolicyField.setHorizontalAlignment(JTextField.CENTER);
		leftSideMain.add(prioPolicyLabel);
		leftSideMain.add(priorityPolicyField);
		
		JLabel entryLabel = new JLabel("Entry Queue(e.g. '1' for 1st queue): ");
		entryQueueField = new JTextField(12);
		entryQueueField.setHorizontalAlignment(JTextField.CENTER);
		leftSideMain.add(entryLabel);
		leftSideMain.add(entryQueueField);
		
		JPanel d,e,f;
		e = new JPanel();
		f = new JPanel();
		e.setBackground(Color.GRAY);
		f.setBackground(Color.GRAY);
		leftSideMain.add(e);
		leftSideMain.add(f);
		
		submitButton = new JButton("Start");
		submitButton.addActionListener(new ButtonListener());
		leftSideMain.add(submitButton);
		
/*		JLabel arrivalTimeLabel = new JLabel("Arrival Time: ");
		arrivalTimeField = new JTextField(10);
		arrivalTimeField.setHorizontalAlignment(JTextField.CENTER);
		leftSideMain.add(arrivalTimeLabel);
		leftSideMain.add(arrivalTimeField);
		
		JLabel burstTimeLabel = new JLabel("Burst Time: ");
		burstTimeField = new JTextField(10);
		burstTimeField.setHorizontalAlignment(JTextField.CENTER);
		
		leftSideMain.add(burstTimeLabel);
		leftSideMain.add(burstTimeField);*/
		
		main.add(leftSideMain,BorderLayout.WEST);		
	}
	
    public void initRightSideBar(){
		rightSideMain = new JPanel();
		rightSideMain.setBackground(Color.GRAY);
		rightSideMain.setLayout(new GridLayout(15, 0));
		JLabel sideTitle = new JLabel("               Process Control Bar               ");
		sideTitle.setHorizontalAlignment(JLabel.CENTER);
		rightSideMain.add(sideTitle);
		
		JLabel processIDLabel = new JLabel("Process ID(Integer): ");
		processIDField = new JTextField(10);
		processIDField.setHorizontalAlignment(JTextField.CENTER);
		rightSideMain.add(processIDLabel);
		rightSideMain.add(processIDField);
		
		JLabel arrivalTimeLabel = new JLabel("Arrival Time: ");
		arrivalTimeField = new JTextField(10);
		arrivalTimeField.setHorizontalAlignment(JTextField.CENTER);
		rightSideMain.add(arrivalTimeLabel);
		rightSideMain.add(arrivalTimeField);
		
		JLabel burstTimeLabel = new JLabel("Burst Time: ");
		burstTimeField = new JTextField(10);
		burstTimeField.setHorizontalAlignment(JTextField.CENTER);
		rightSideMain.add(burstTimeLabel);
		rightSideMain.add(burstTimeField);
		
		JLabel priorityLabel = new JLabel("Priority: ");
		priorityField = new JTextField(10);
		priorityField.setHorizontalAlignment(JTextField.CENTER);
		rightSideMain.add(priorityLabel);
		rightSideMain.add(priorityField);
		
		main.add(rightSideMain,BorderLayout.EAST);

    }
	
	public void initMain() {
		//resulsMain is the center JPanel
		
		resultsMain = new JPanel();
		main.add(resultsMain, BorderLayout.CENTER);
		resultsMain.setBackground(Color.DARK_GRAY);
		
		resultsMain.setLayout(new GridLayout(2, 0, 0, 10));
		upperMain = new JPanel();
		lowerMain = new JPanel();
		
		upperMain.setBackground(Color.BLACK);
		lowerMain.setBackground(Color.BLACK);
		
		upperMain.setLayout(new BorderLayout());
		JLabel gtLabel = new JLabel("Gantt Chart:");
		gtLabel.setForeground(Color.ORANGE);
		gtLabel.setFont(new Font("FREEDOM", Font.BOLD, 14));
		upperMain.add(gtLabel, BorderLayout.NORTH);
		
		ganttChartArea = new JTextArea();
		ganttChartArea.setEditable(false);
		ganttChartArea.setForeground(Color.ORANGE);
		ganttChartArea.setBackground(Color.BLACK);
		ganttChartArea.setLineWrap(true);
		
		upperMain.add(ganttChartArea, BorderLayout.CENTER);
		
		
		lowerMain.setLayout(new GridLayout(9, 0));
		JLabel psLabel = new JLabel("Performance Average Summary:");
		psLabel.setForeground(Color.ORANGE);
		psLabel.setFont(new Font("FREEDOM", Font.BOLD, 14));
		lowerMain.add(psLabel);
		
		performanceLabels = new JLabel[3];
		performanceTextFields = new JTextField[3];

		performanceLabels[0] = new JLabel("Turn Around Time: ");
		performanceLabels[1] = new JLabel("Waiting Time:");
		performanceLabels[2] = new JLabel("Response Time Time");
		
		for(int i = 0; i < performanceLabels.length; i++) {
			performanceLabels[i].setForeground(Color.ORANGE);
			performanceTextFields[i] = new JTextField();
			performanceTextFields[i].setForeground(Color.ORANGE);
			performanceTextFields[i].setEditable(false);
			performanceTextFields[i].setBackground(Color.DARK_GRAY);
			lowerMain.add(performanceLabels[i]);
			lowerMain.add(performanceTextFields[i]);
		}

		
		
		resultsMain.add(upperMain);
		resultsMain.add(lowerMain);
	}
	
	
	private class ButtonListener implements ActionListener{
		
		int[] PIDs;
		int[] AT;
		int[] BT;
		int[] PT;
		
		int numQ;
		int[] shedAlgo;
		int prioPolicy;
		int[] timeSlices;
		int entry;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			PIDs = parseValues(processIDField.getText());
			AT = parseValues(arrivalTimeField.getText());
			BT = parseValues(burstTimeField.getText());
			PT = parseValues(priorityField.getText());
			shedAlgo = parseValues(algoOfQueuesField.getText());
			timeSlices = parseValues(timeSliceField.getText());
			
			numQ = Integer.parseInt(numberOfQueuesField.getText());
			prioPolicy = Integer.parseInt(priorityPolicyField.getText());
			entry = Integer.parseInt(entryQueueField.getText());
			
			int v = PIDs.length;
			int w = AT.length;
			int x = BT.length;
			int y = PT.length;
			int z = timeSlices.length;
			
			int totalrr = 0;
			for(int i = 0; i < shedAlgo.length; i++) {
				if(shedAlgo[i] == 6) {
					totalrr++;
				}
			}
			System.out.println(timeSlices.length);
			System.out.println(totalrr);
			boolean allPositive = true;
			if((v == w && w == x && x == y) == false) { //all input are NOT of equal length
				allPositive = false;
				String output = "";
				output += "PIDs: "+ v +"\n";
				output += "Arrival Time: "+ w +"\n";
				output += "Burst Time: "+ x +"\n";
				output += "Waiting Time: "+ y  +"\n";
				JOptionPane.showMessageDialog(null,
					    "Input lengths not equal: \n" + output,
					    "Input Error",
					    JOptionPane.ERROR_MESSAGE);
			} else if(numQ <= 0) { //number of queue specified less than 1
				allPositive = false;
				JOptionPane.showMessageDialog(null,
					    "Incorrect possible number of queues",
					    "Input Error",
					    JOptionPane.ERROR_MESSAGE);
			} else if(totalrr > 0 && timeSlices.length < totalrr) { //timeslices less than total time slices
				allPositive = false;
				JOptionPane.showMessageDialog(null,
					    "Time slices number of input less than total number of round robin queues",
					    "Input Error",
					    JOptionPane.ERROR_MESSAGE);
			} else if((entry > 0 && entry <= numQ) == false) { // entry queue out of bounds between numQ
				allPositive = false;
				JOptionPane.showMessageDialog(null,
					    "Entry queue out of bounds",
					    "Input Error",
					    JOptionPane.ERROR_MESSAGE);
			} else if(prioPolicy != 0 && prioPolicy != 1) { // Invalid Priority
				allPositive = false;
				JOptionPane.showMessageDialog(null,
						"Invalid Priority Policy Choice",
						"Input Error",
						JOptionPane.ERROR_MESSAGE);
			}
			
			
			if(allPositive) {
				MLFQ mlfq = new MLFQ(numQ, shedAlgo, prioPolicy, timeSlices, entry);
				mlfq.initProcesses(PIDs, AT, BT, PT);
				mlfq.execute();
				GanttChart res = mlfq.getPerformance();
				ganttChartArea.setText("\t" + res.getOutput());
				performanceTextFields[0].setText(res.getAveTurnAroundTime() + "");
				performanceTextFields[1].setText(res.getAveWaitTime() + "");
				performanceTextFields[2].setText(res.getAveResponseTime() + "");
			}
		}
		
		public int[] parseValues(String input) {
			int[] result = null;
			String[] input2 = input.split(",");
			result = new int[input2.length];
			for(int i = 0; i < input2.length; i++) {
				result[i] = Integer.parseInt(input2[i]);
			}
			
			return result;
		}
		
		
	}
}

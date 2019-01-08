package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.Format;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import mlfq.MLFQ;
import net.miginfocom.swing.MigLayout;
import utility.Block;
import utility.Formatter;
import utility.Label;
import utility.RoundEdgedPanel;

public class MLFQSimulatorGUI extends JFrame implements ActionListener{
	
	private RoundEdgedPanel ganttChartPanel;
	private RoundEdgedPanel performancePanel;
	private RoundEdgedPanel processesPanel;
	private RoundEdgedPanel mlfqPanel;
	private RoundEdgedPanel additionalConfigPanel;
	
	private static JScrollPane scrollPane;
	
	private JPanel bottomPanel, mainPanel, processesContainer, mlfqContainer;
	private static JPanel chartContainer, labelContainer, subChartContainer;
	
	private static JTextField aveWaitTime;
	private static JTextField aveResponseTime;
	private static JTextField aveTurnAroundTime;
	private JFormattedTextField entryQueue;
	
	private JFormattedTextField[] arrival, burst, priority;
	
	private JFormattedTextField numProcesses, numQueues, timeSlice[];
	
	private JButton generateProcesses, generateQueues, clearProcesses, clearQueues, randomize;
	private static JButton start, clearAll;
	
	private JComboBox<String> prioPolicy;
	private String prioPolicyList[] = {"Higher Before Lower", "Fixed Time Slot"};
	
	private JComboBox<String> schedAlgo[];
	private String schedAlgoList[] = {"First Come First Serve", "Shortest Job First", "Shortest Remaining Time First",
											"Non-Preemptive Priority", "Preemptive Priority", "Round Robin"};
	
	private static Block recentBlock;
	private static Label recentLabel;
	
	private static int blockCount;
	private static int labelCount;
	
	private static Font font1 = new Font("Arial", Font.PLAIN, 12);
	private Font font2 = new Font("Arial", Font.BOLD, 12);
	
	private static boolean previouslyExecutedProcess;
	
	private Random rand = new Random();
	
	public MLFQSimulatorGUI() {
		try {
		    UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		setLayout(new MigLayout("fillx, insets 0"));

		mainPanel = new JPanel(new MigLayout("fillx"));
		add(mainPanel, "width 100%, height 100%");
		
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		initGanttChartPanel();
		initPerformancePanel();
		
		bottomPanel = new JPanel(new MigLayout("fillx, insets 0"));
		mainPanel.add(bottomPanel, "width 100%, height 50%, spanx 2");
		
		initProcessesPanel();
		initMLFQPanel();
		initAdditionalConfigPanel();
		
		setVisible(true);
	}
	
	public void initGanttChartPanel() {
		ganttChartPanel = new RoundEdgedPanel("GanttChart");
		ganttChartPanel.setLayout(new MigLayout("fillx"));
		
		chartContainer = new JPanel(new MigLayout("fillx"));
		scrollPane = new JScrollPane(chartContainer);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		ganttChartPanel.add(scrollPane, "width 100%, height 100%");
		
		subChartContainer = new JPanel(new MigLayout("fillx"));
		chartContainer.add(subChartContainer, "height 20%, wrap, gapTop 75px");
		
		labelContainer = new JPanel(new MigLayout("fillx"));
		chartContainer.add(labelContainer, "height 15%");
		
		mainPanel.add(ganttChartPanel, "width 70%, height 50%");
	}
	
	public void initPerformancePanel() {
		performancePanel = new RoundEdgedPanel("Performance Analysis");
		performancePanel.setLayout(new MigLayout("fillx"));
		
		performancePanel.add(new Label(font2, "Average Waiting Time"), "wrap");

		aveWaitTime = new JTextField();
		aveWaitTime.setEditable(false);
		aveWaitTime.setFocusable(false);
		performancePanel.add(aveWaitTime, "wrap, grow");
		
		performancePanel.add(new Label(font2, "Average Response Time"), "wrap");
		
		aveResponseTime = new JTextField();
		aveResponseTime.setEditable(false);
		aveResponseTime.setFocusable(false);
		performancePanel.add(aveResponseTime, "grow, wrap");
		
		performancePanel.add(new Label(font2, "Average Turn Around Time"), "wrap");
		
		aveTurnAroundTime = new JTextField();
		aveTurnAroundTime.setEditable(false);
		aveTurnAroundTime.setFocusable(false);
		performancePanel.add(aveTurnAroundTime, "grow, wrap");
		
		mainPanel.add(performancePanel, "width 30%, height 50%, wrap");
	}
	
	public void initProcessesPanel() {
		processesPanel = new RoundEdgedPanel("Processes Configuration");
		processesPanel.setLayout(new MigLayout("fillx"));
		
		processesPanel.add(new Label(font2, "No. of Processes"));
		
		numProcesses = new JFormattedTextField(new Formatter(20));
		numProcesses.setColumns(25);
		processesPanel.add(numProcesses);
		
		generateProcesses = new JButton("Generate");
		generateProcesses.addActionListener(this);
		processesPanel.add(generateProcesses);
		
		clearProcesses = new JButton("Clear");
		clearProcesses.addActionListener(this);
		clearProcesses.setEnabled(false);
		processesPanel.add(clearProcesses, "wrap");
		
		processesContainer = new JPanel(new MigLayout("fillx"));
		JScrollPane scrollPane = new JScrollPane(processesContainer);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		processesPanel.add(scrollPane, "width 100%, spanx 4, height 75%, wrap");
		
		randomize = new JButton("Randomize");
		randomize.addActionListener(this);
		processesPanel.add(randomize, "grow, spanx 4");
		
		bottomPanel.add(processesPanel, "width 35%, height 100%");
	}
	
	public void initMLFQPanel() {
		mlfqPanel = new RoundEdgedPanel("MLFQ Configuration Panel");
		mlfqPanel.setLayout(new MigLayout("fillx"));
		
		mlfqPanel.add(new Label(font2, "No. of Queues"));
		
		numQueues = new JFormattedTextField(new Formatter(3));
		numQueues.setColumns(25);
		mlfqPanel.add(numQueues);
		
		generateQueues = new JButton("Generate");
		generateQueues.addActionListener(this);
		mlfqPanel.add(generateQueues);
		
		clearQueues = new JButton("Clear");
		clearQueues.addActionListener(this);
		clearQueues.setEnabled(false);
		mlfqPanel.add(clearQueues, "wrap");
		
		mlfqContainer = new JPanel(new MigLayout("fillx"));
		mlfqPanel.add(mlfqContainer, "width 100%, spanx 4");

		bottomPanel.add(mlfqPanel, "width 35%, height 100%");
	}
	
	public void initAdditionalConfigPanel() {
		additionalConfigPanel = new RoundEdgedPanel("Additional Configuration Panel");
		additionalConfigPanel.setLayout(new MigLayout("fillx"));
		
		additionalConfigPanel.add(new Label(font2, "Priority Policy"), "wrap, gapTop 10px");
		
		prioPolicy = new JComboBox<String>(prioPolicyList);
		additionalConfigPanel.add(prioPolicy, "grow, wrap");
		
		additionalConfigPanel.add(new Label(font2, "Entry Queue"), "wrap, gapTop 15px");
		
		entryQueue = new JFormattedTextField(new Formatter(3));
		additionalConfigPanel.add(entryQueue, "wrap, grow");
		
		start = new JButton("Start");
		start.addActionListener(this);
		additionalConfigPanel.add(start, "wrap, grow, gapTop 150px");
		
		clearAll = new JButton("Clear All");
		clearAll.addActionListener(this);
		additionalConfigPanel.add(clearAll, "wrap, grow");
		
		bottomPanel.add(additionalConfigPanel, "width 30%, height 100%");
	}
	
	public static void addLabel(int time) {
		Label label = new Label(font1, time + "");			
		if(time == 0 || !previouslyExecutedProcess) {
			labelContainer.add(label);
		}else{	
			int distance;
			if(recentBlock == null) {
				distance = 0;
			}else {
				distance = recentBlock.getX() - (recentLabel.getX() + recentLabel.getWidth()) + 45 - (label.getWidth()/2);
			}
			
			labelContainer.add(label, "gapLeft " + distance + "px");
		}
		recentLabel = label;
		labelCount++;
		labelContainer.repaint();
		labelContainer.revalidate();
		scrollPane.getHorizontalScrollBar().setValue(scrollPane.getHorizontalScrollBar().getMaximum());
		previouslyExecutedProcess = false;
	}
	
	public static void setPerformance(float responseTime, float waitingTime, float turnAroundTime) {
		aveResponseTime.setText(responseTime + "");
		aveWaitTime.setText(waitingTime + "");
		aveTurnAroundTime.setText(turnAroundTime + "");
		
		start.setEnabled(true);
		clearAll.setEnabled(true);
	}
	
	public static void addBlock(Block block) {
		
		try {
			Thread.sleep(30);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		if(labelCount - 1 != blockCount) {	
			int distance;
			if(recentBlock != null) {
				distance = (recentLabel.getX() + recentLabel.getWidth()) - (recentBlock.getX() + recentBlock.getWidth());
			}else {
				distance = (recentLabel.getX() + recentLabel.getWidth()) ;
			}
			subChartContainer.add(block, "gapLeft " + distance + "px");
			blockCount = labelCount -1;
		}else {
			subChartContainer.add(block);
		}
		
		blockCount++;
		
		recentBlock = block;
		subChartContainer.repaint();
		subChartContainer.revalidate();
		//scrollPane.getHorizontalScrollBar().setValue(scrollPane.getHorizontalScrollBar().getMaximum());
		previouslyExecutedProcess = true;
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		};
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == generateProcesses) {
			if(numProcesses.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Fill out required fields for this function", "Message", JOptionPane.ERROR_MESSAGE);
			}else {
				numProcesses.setEnabled(false);
				generateProcesses.setEnabled(false);
				randomize.setEnabled(false);
				clearProcesses.setEnabled(true);
				
				int size = Integer.parseInt(numProcesses.getText());
				
				processesContainer.add(new Label(font2, "Process ID"), "align center, gapTop 10px");
				processesContainer.add(new Label(font2, "Arrival Time"), "align center");
				processesContainer.add(new Label(font2, "Burst Time"), "align center");
				processesContainer.add(new Label(font2, "Priority"), "align center, wrap");
				
				arrival = new JFormattedTextField[size];
				burst = new JFormattedTextField[size];
				priority = new JFormattedTextField[size];
				
				for(int a = 0; a < size; a++) {
					processesContainer.add(new Label(font1, "Process " + a), "align center");
					
					arrival[a] = new JFormattedTextField(new Formatter(0, 50));
					arrival[a].setColumns(7);
					processesContainer.add(arrival[a], "align center");
					
					burst[a] = new JFormattedTextField(new Formatter(50));
					burst[a].setColumns(7);
					processesContainer.add(burst[a], "align center");
					
					priority[a] = new JFormattedTextField(new Formatter(Integer.MIN_VALUE, Integer.MAX_VALUE));
					priority[a].setColumns(7);
					processesContainer.add(priority[a], "align center, wrap");
					
				}
				
				processesContainer.repaint();
				processesContainer.revalidate();
			}
		}else if(e.getSource() == generateQueues) {
			if(numQueues.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Fill out required fields for this function", "Message", JOptionPane.ERROR_MESSAGE);
			}else {
				numQueues.setEnabled(false);
				generateQueues.setEnabled(false);
				
				int size = Integer.parseInt(numQueues.getText());
				
				mlfqContainer.add(new Label(font2, "Queue"), "align center");
				mlfqContainer.add(new Label(font2, "Scheduling Algorithm"), "align center");
				mlfqContainer.add(new Label(font2, "Time Slice"), "align center, wrap");
				
				schedAlgo = new JComboBox[size];
				timeSlice = new JFormattedTextField[size];
				
				for(int a = 0; a < size; a++) {
					mlfqContainer.add(new Label(font1, "Queue " + a), "align center");
					
					schedAlgo[a] = new JComboBox<String>(schedAlgoList);
					schedAlgo[a].addActionListener(this);
					mlfqContainer.add(schedAlgo[a], "grow");
					
					timeSlice[a] = new JFormattedTextField(new Formatter(100));
					timeSlice[a].setEnabled(false);
					timeSlice[a].setColumns(5);
					mlfqContainer.add(timeSlice[a], "wrap, align center");
				}
				
				clearQueues.setEnabled(true);
				
			}
		}else if(e.getSource() == start) {
			if(!clearProcesses.isEnabled()) {
				JOptionPane.showMessageDialog(null, "You have not generated processes yet", "Message", JOptionPane.ERROR_MESSAGE);
			}else if( !clearQueues.isEnabled()) {
				JOptionPane.showMessageDialog(null, "You have not generated queues yet", "Message", JOptionPane.ERROR_MESSAGE);
			}else if(entryQueue.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Fill out entry queue field!", "Message", JOptionPane.ERROR_MESSAGE);
			}else if(Integer.parseInt(entryQueue.getText()) > Integer.parseInt(numQueues.getText())) {
				JOptionPane.showMessageDialog(null, "Entry queue exceeds the number of generated queues", "Message", JOptionPane.ERROR_MESSAGE);
			}else {
				subChartContainer.removeAll();
				labelContainer.removeAll();
				int mlfqSize = Integer.parseInt(numQueues.getText());
				int[] algo = new int[mlfqSize], slice = new int[mlfqSize];
				
				try {
					for(int a = 0; a < mlfqSize; a++) {
						algo[a] = schedAlgo[a].getSelectedIndex() + 1;
						if(!timeSlice[a].isEnabled()) {
							slice[a] = 0;
						}else {
							slice[a] = Integer.parseInt(timeSlice[a].getText());
						}
					}
					
					int processesSize = Integer.parseInt(numProcesses.getText());
					
					int PIDs[] = new int [processesSize];
					int AT[] = new int [processesSize];
					int BT[] = new int [processesSize];
					int PT[] = new int [processesSize];
					for(int a = 0; a < processesSize; a++) {
						PIDs[a] = a;
						AT[a] = Integer.parseInt(arrival[a].getText());
						BT[a] = Integer.parseInt(burst[a].getText());
						PT[a] = Integer.parseInt(priority[a].getText());
					}
					
					MLFQ mlfq = new MLFQ(Integer.parseInt(numQueues.getText()), algo, prioPolicy.getSelectedIndex(), slice, Integer.parseInt(entryQueue.getText()));
					mlfq.initProcesses(PIDs, AT, BT, PT);
					mlfq.start();
					
					start.setEnabled(false);
					clearAll.setEnabled(false);
					
				}catch(NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Fill out all fields!", "Message", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		}else if(e.getSource() == clearAll) {
			
			subChartContainer.removeAll();
			subChartContainer.repaint();
			subChartContainer.revalidate();
			
			labelContainer.removeAll();
			labelContainer.repaint();
			labelContainer.revalidate();
			
			aveWaitTime.setText("");
			aveResponseTime.setText("");
			aveTurnAroundTime.setText("");
			entryQueue.setText("");
			numQueues.setText("");
			numProcesses.setText("");
			
			processesContainer.removeAll();
			processesContainer.repaint();
			processesContainer.revalidate();
			
			mlfqContainer.removeAll();
			mlfqContainer.repaint();
			mlfqContainer.revalidate();
			
			generateProcesses.setEnabled(true);
			generateQueues.setEnabled(true);
			randomize.setEnabled(true);
			
			clearProcesses.setEnabled(false);
			clearQueues.setEnabled(false);
			
			numQueues.setEnabled(true);
			numProcesses.setEnabled(true);
			
		}else if(e.getSource() == randomize) {
			if(numProcesses.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Fill out required fields for this function", "Message", JOptionPane.ERROR_MESSAGE);
			}else {
				numProcesses.setEnabled(false);
				generateProcesses.setEnabled(false);
				randomize.setEnabled(false);
				clearProcesses.setEnabled(true);
				
				int size = Integer.parseInt(numProcesses.getText());
				
				processesContainer.add(new Label(font2, "Process ID"), "align center, gapTop 10px");
				processesContainer.add(new Label(font2, "Arrival Time"), "align center");
				processesContainer.add(new Label(font2, "Burst Time"), "align center");
				processesContainer.add(new Label(font2, "Priority"), "align center, wrap");
				
				arrival = new JFormattedTextField[size];
				burst = new JFormattedTextField[size];
				priority = new JFormattedTextField[size];
				
				for(int a = 0; a < size; a++) {
					processesContainer.add(new Label(font1, "Process " + a), "align center");
					
					arrival[a] = new JFormattedTextField(new Formatter(0, 50));
					arrival[a].setColumns(7);
					arrival[a].setText(rand.nextInt(50) + "");
					processesContainer.add(arrival[a], "align center");
					
					burst[a] = new JFormattedTextField(new Formatter(50));
					burst[a].setColumns(7);
					burst[a].setText(rand.nextInt(50) + 1 + "");
					processesContainer.add(burst[a], "align center");
					
					priority[a] = new JFormattedTextField(new Formatter(Integer.MIN_VALUE, Integer.MAX_VALUE));
					priority[a].setColumns(7);
					priority[a].setText(rand.nextInt(20) + "");
					processesContainer.add(priority[a], "align center, wrap");
					
				}
				
				processesContainer.repaint();
				processesContainer.revalidate();
			}
		}else if(e.getSource() == clearProcesses) {
			processesContainer.removeAll();
			processesContainer.repaint();
			processesContainer.revalidate();
			
			clearProcesses.setEnabled(false);
			numProcesses.setEnabled(true);
			generateProcesses.setEnabled(true);
			randomize.setEnabled(true);
			
		}else if(e.getSource() == clearQueues) {
			mlfqContainer.removeAll();
			mlfqContainer.repaint();
			mlfqContainer.revalidate();
			
			clearQueues.setEnabled(false);
			numQueues.setEnabled(true);
			generateQueues.setEnabled(true);
			
		}else {
			for(int a = 0; a < Integer.parseInt(numQueues.getText()); a++) {
				if(e.getSource() == schedAlgo[a]) {
					if(schedAlgo[a].getSelectedIndex()== 5){						
						timeSlice[a].setEnabled(true);
					}else {
						timeSlice[a].setEnabled(false);
					}
				}
			}
		}
	}
} 
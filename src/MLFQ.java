import java.util.ArrayList;

import classicalSchedulingAlgorithms.FCFS;
import classicalSchedulingAlgorithms.NPPriority;
import classicalSchedulingAlgorithms.Priority;
import classicalSchedulingAlgorithms.RoundRobinScheduling;
import classicalSchedulingAlgorithms.ShortestRemainingTimeFirst;
import classicalSchedulingAlgorithms.SchedulingAlgorithm;
import classicalSchedulingAlgorithms.SJF;
import processInformation.GanttChart;
import processInformation.ProcessControlBlock;
import java.util.Scanner;

public class MLFQ {

	private ArrayList<ProcessControlBlock> processes = new ArrayList<ProcessControlBlock>();
	private ArrayList<SchedulingAlgorithm> schedulingAlgorithms = new ArrayList<SchedulingAlgorithm>(); 
	private ArrayList<Integer>timeSlice = new ArrayList<Integer>();
	private GanttChart ganttChart;
	private int time;
	public MLFQ(){
		Scanner scan = new Scanner(System.in);
		ganttChart = new GanttChart();
		int queueNumber;

		System.out.print("Enter number of queues: ");
		queueNumber = scan.nextInt();
		
		listSchedulingAlgorithms();
		
		int choice;
		for(int a = 0; a < queueNumber; a++){
			System.out.print("Scheduling Algorithm for queue " + (a+1) + ": ");
			choice = scan.nextInt();
			if(choice == 1){
				schedulingAlgorithms.add(new FCFS());
			}else if(choice == 2){
				schedulingAlgorithms.add(new SJF());
			}else if(choice == 3){
				schedulingAlgorithms.add(new ShortestRemainingTimeFirst());
			}else if(choice == 4){
				schedulingAlgorithms.add(new NPPriority());
			}else if(choice == 5){
				schedulingAlgorithms.add(new Priority());
			}else if(choice == 6){
				System.out.print("Enter time quatum: ");
				schedulingAlgorithms.add(new RoundRobinScheduling(scan.nextInt()));
			}
			
			if(a < queueNumber - 1){
				System.out.print("Enter time slice for this queue: ");
				timeSlice.add(scan.nextInt());
			}
			
		}
		scan.close();
	}
	
	public void listSchedulingAlgorithms(){
		System.out.println("1. First Come First Serve");
		System.out.println("2. Shortest Job First");
		System.out.println("3. Shortest Remaining Time First");
		System.out.println("4. Non-Preemptive Priority");
		System.out.println("5. Preemptive Priority");
		System.out.println("6. Round Robin");
	}
	
	public void execute(){
		while(true){
			if((processes.size() != 0) && (processes.get(0).getArrivalTime() <= time)){
				if(timeSlice.size() != 0){
					processes.get(0).setTimeSlice(timeSlice.get(0));
				}
				schedulingAlgorithms.get(0).addProcess(processes.get(0));
				processes.remove(0);
			}
			
			boolean toEnd = true;
			int a = 0;
			int size = schedulingAlgorithms.size();
			ProcessControlBlock temp;
			while(a < size){
				if(schedulingAlgorithms.get(a).isProcessing()){
					
					toEnd = false;
					
					schedulingAlgorithms.get(a).execute(ganttChart);
					if(a != size - 1){															//demotion. Not applicable to the last queue
						if(schedulingAlgorithms.get(a).getProcess() != null){
							schedulingAlgorithms.get(a).getProcess().subtractTimeSlice();
							if(schedulingAlgorithms.get(a).getProcess().getTimeSlice() == 0){
								temp = schedulingAlgorithms.get(a).removeProcess();
								if(timeSlice.size() >= a + 2){
									temp.setTimeSlice(timeSlice.get(a+1));
								}
								schedulingAlgorithms.get(a+1).addProcess(temp);
							}
						}
					}
					break;
				}
				a++;
			}
			if(processes.size() == 0 && toEnd){
				break;
			}
			time++;
			ganttChart.incTime();
		}
	}
	
	public void addProcess(ProcessControlBlock process){
		processes.add(process);
		for(int a = 0; a < processes.size(); a++){
			if((process.getArrivalTime() < processes.get(a).getArrivalTime()) && processes.size() > 1){
				for(int b = processes.size() - 1; b >= a + 1 ; b--){
					processes.set(b, processes.get(b-1));
				}
				processes.set(a, process);
				break;
			}
		}
	}
	
	public GanttChart getGanttChart() {
		return ganttChart;
	}
}

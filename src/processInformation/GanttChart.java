package processInformation;

import java.util.ArrayList;

public class GanttChart{
	
	private ArrayList<GanttChartElement> ganttChart;
	int time;
	
	
	public GanttChart() {
		ganttChart = new ArrayList<GanttChartElement>();
		time = 0;
	}
	
	public void addGTElement(int PID) {
		ganttChart.add(new GanttChartElement(PID, time, 0));
	}
	
	public GanttChartElement getLastElement() {
		return ganttChart.get(ganttChart.size()-1);
	}
	
	public void setEndTime() {
		getLastElement().setEndTime(time+1);
	}
	
	public void incTime(){
		time++;
	}
	
	public boolean isEmpty() {
		if(ganttChart.size() == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public void printContents() {
		System.out.println("\n\n|***************************************************************************************************|");
		System.out.println("Analysis: ");
		System.out.println("Guide: ");
		System.out.println("   +Arrival 	-> E: ");
		System.out.println("   +Process ID  -> P: ");
		System.out.println("   +End Time 	-> D: \n");
		System.out.println("GanttChart: ");
		System.out.print("<START>->");
		int line = 0;
		for(int index = 0; index < ganttChart.size(); index++) {
			GanttChartElement e = ganttChart.get(index);
			if(index == 0 || index != ganttChart.size()-1) {
				System.out.print("[E:"+ e.getBeginTime()+" P:"+e.getPID()+" D:"+e.getEndTime()+"]->");
			}
			else {
				System.out.print("[E:"+ e.getBeginTime()+" P:"+e.getPID()+" D:"+e.getEndTime()+"]");
			}

			if(line == 4) {
				line = 0;
				System.out.println();
			}else {
				line++;
			}
		}
		System.out.println();
		System.out.println("|***************************************************************************************************|");
	}
	
	public void closeCurrentGantt() {
		getLastElement().close();
	}
	
	public boolean currentIsClosed() {
		if(getLastElement().isClose()) {
			return true;
		} else {
			return false;
		}
	}
}

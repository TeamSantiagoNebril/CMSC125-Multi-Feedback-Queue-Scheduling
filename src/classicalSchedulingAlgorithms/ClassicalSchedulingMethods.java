package classicalSchedulingAlgorithms;

import java.util.ArrayList;

import processInformation.GanttChartElement;
import processInformation.ProcessControlBlock;

public interface ClassicalSchedulingMethods {
	public void addProcess(ProcessControlBlock e);
	public void execute(ArrayList<GanttChartElement> gc, int time);
	//public void execute(ArrayList<ProcessControlBlock> e, ArrayList<GanttChartElement> f, int time);
	public int getProcessList();
}

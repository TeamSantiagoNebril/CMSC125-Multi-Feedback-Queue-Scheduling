package classicalSchedulingAlgorithm;

import gui.MLFQSimulatorGUI;
import process.ProcessControlBlock;
import utility.Block;

public class FCFS extends SchedulingAlgorithm{

	
	public FCFS() {
		super();
	}

	@Override
	public void addProcess(ProcessControlBlock e) {
		processQueue.add(e);
	}

	@Override
	public ProcessControlBlock executeScheduling() {
		ProcessControlBlock r = null;
		r = processQueue.get(0);
		MLFQSimulatorGUI.addBlock(new Block(processQueue.get(0).getPID()));
		if(processQueue.get(0).getBurstTime() > 0) {
			processQueue.get(0).decBurstTime();
			newRP = false;
		}
		
		if(processQueue.get(0).getBurstTime() <= 0) {
			processQueue.remove(0);
			newRP = true;
		}
		return r;
	}
	
}

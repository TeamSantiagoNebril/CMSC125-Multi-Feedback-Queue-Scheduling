package classicalSchedulingAlgorithm;

import gui.MLFQSimulatorGUI;
import process.ProcessControlBlock;
import utility.Block;

public class Prio extends SchedulingAlgorithm{
	
	public Prio() {
		super();
	}
	
	public void sortProcesses() {
		processQueue.sort((o1, o2) -> new Integer(o1.getPriority()).compareTo(o2.getPriority())); //sort ascending based on burst time
	}

	@Override
	public void addProcess(ProcessControlBlock e) {
		int PID = 0;
		if(processQueue.size() > 0) {
			PID = processQueue.get(0).getPID();
		}
		processQueue.add(e);
		sortProcesses();
		if(PID != 0 && PID != processQueue.get(0).getPID()) {
			newRP = true;
			for(int i = 0; i < processQueue.size(); i++) {
				if(processQueue.get(i).getPID() == PID) {
					ppIndex = i;
				}
			}

		}
	}

	@Override
	public ProcessControlBlock executeScheduling() {
		ProcessControlBlock r = null;
		r = processQueue.get(0);
		MLFQSimulatorGUI.addBlock(new Block(processQueue.get(0).getPID()));
		if(processQueue.get(0).getBurstTime() > 0) {
			processQueue.get(0).decBurstTime();
			newRP = false;
			ppIndex = -1;
		}
		
		if(processQueue.get(0).getBurstTime() <= 0) {
			processQueue.remove(0);
			newRP = true;
		}

		return r;
	}
}

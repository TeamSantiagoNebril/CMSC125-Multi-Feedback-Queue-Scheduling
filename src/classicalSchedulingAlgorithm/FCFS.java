package classicalSchedulingAlgorithm;

import process.ProcessControlBlock;

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

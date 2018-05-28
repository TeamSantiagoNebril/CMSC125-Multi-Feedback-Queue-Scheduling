package classicalSchedulingAlgorithm;

import process.ProcessControlBlock;

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
		if(PID != processQueue.get(0).getPID()) {
			newRP = true;
		}
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

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
		processQueue.add(e);
		sortProcesses();
	}

	@Override
	public ProcessControlBlock executeScheduling() {
		ProcessControlBlock r = null;
		r = processQueue.get(0);
		if(processQueue.get(0).getBurstTime() > 0) {
			processQueue.get(0).decBurstTime();
			newRP = false;
		}

		if(processQueue.get(0).getBurstTime() == 0) {

			processQueue.remove(0);
			newRP = true;
		}
		return r;
	}
}

package classicalSchedulingAlgorithm;

import process.ProcessControlBlock;

public class SRTF extends SchedulingAlgorithm{

	public SRTF() {
		super();
	}
	
	@Override
	public void addProcess(ProcessControlBlock e) {
		processQueue.add(e);
		sortProcesses();
		//System.out.println(processQueue.size());
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
	
	public void sortProcesses() {
		processQueue.sort((o1, o2) -> new Integer(o1.getBurstTime()).compareTo(o2.getBurstTime())); //sort ascending based on burst time
	}

}

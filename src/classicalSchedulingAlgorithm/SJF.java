package classicalSchedulingAlgorithm;

import process.ProcessControlBlock;

public class SJF extends SchedulingAlgorithm{

	public SJF() {
		super();
	}
	
	@Override
	public void addProcess(ProcessControlBlock e) {
		if(processQueue.isEmpty() || processQueue.size() == 1) {
			processQueue.add(e);
		} else {
			for(int i = 1; i < processQueue.size(); i++) {
				if(e.getBurstTime() < processQueue.get(i).getBurstTime()) {
					processQueue.add(i, e);
					break;
				} else if((i + 1) == processQueue.size()) {
					processQueue.add(e);
					break;
				}
			}
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
		
		if(processQueue.get(0).getBurstTime() == 0) {

			processQueue.remove(0);
			newRP = true;
		}
		return r;
	}

}

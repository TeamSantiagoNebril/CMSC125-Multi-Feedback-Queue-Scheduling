package classicalSchedulingAlgorithm;

import process.ProcessControlBlock;

public class RR extends SchedulingAlgorithm{
	
	public RR(int timeSlice) {
		super();
		currentCounter = 0;
		this.timeSlice = timeSlice;
	}
	
	
	@Override
	public void addProcess(ProcessControlBlock e) {
		processQueue.add(e);
	}

	@Override
	public ProcessControlBlock executeScheduling() {
		ppIndex = -1;
		ProcessControlBlock r = null;
		r = processQueue.get(0);
		if(processQueue.get(0).getBurstTime() > 0) {
			processQueue.get(0).decBurstTime();
			newRP = false;
		}
		if(processQueue.get(0).getBurstTime() == 0) {
			processQueue.remove(0);
			currentCounter = 0;
			newRP = true;
		}
		currentCounter++;
		if(currentCounter == timeSlice) {
			currentCounter = 0;
			if(!isEmptyQueue() && !newRP) {
				ProcessControlBlock ee = processQueue.remove(0);
				processQueue.add(ee);
				ppIndex = processQueue.size()-1;
				//System.out.println(ppIndex);
			}
		}

		return r;
	}
	
}

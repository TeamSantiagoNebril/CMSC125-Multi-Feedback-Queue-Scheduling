package classicalSchedulingAlgorithm;

import gui.MLFQSimulatorGUI;
import process.ProcessControlBlock;
import utility.Block;

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

	public void setPendingAdditionToFalse() {
		
		pendingAddition = false;
	}
	
	@Override
	public ProcessControlBlock executeScheduling() {
		ppIndex = -1;
		ProcessControlBlock r = null;
		r = processQueue.get(0);
		
		if(pendingAddition){
			processQueue.add(addProcess);
		}
		
		pendingAddition = false;
		
		MLFQSimulatorGUI.addBlock(new Block(processQueue.get(0).getPID()));
		if(r.getBurstTime() > 0) { //process has not used up its alloted time yet and burst time is not yet zero
			processQueue.get(0).decBurstTime();
			newRP = false;
			ppIndex = -1;
			currentCounter++;
		}
		if(r.getBurstTime() == 0) { //process has not used up its alloted time yet and burst time is zero
			
			processQueue.remove(0);
			currentCounter = 0;
			newRP = true;
		}
		if(currentCounter == timeSlice) {
			currentCounter = 0;

			if(!isEmptyQueue() && !newRP) {
				int PID = processQueue.get(0).getPID();
				ProcessControlBlock ee = processQueue.remove(0);
				addProcess = ee;
				pendingAddition = true;
				ppIndex = processQueue.size()-1;
				if(PID == r.getPID()) {
					newRP = false;
				}else {
					newRP = true;
				}
			}/*else if(!isEmptyQueue() && !newRP) {
				System.out.println("wawaw");
				int PID = processQueue.get(0).getPID();
				ProcessControlBlock ee = processQueue.remove(0);
				processQueue.add(ee);
				ppIndex = processQueue.size()-1;
				if(PID == processQueue.get(0).getPID()) {
					newRP = false;
				}else {
					newRP = true;
				}
			}*/
		}
		return r;
	}
}

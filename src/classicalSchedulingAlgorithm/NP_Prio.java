package classicalSchedulingAlgorithm;

import gui.MLFQSimulatorGUI;
import process.ProcessControlBlock;
import utility.Block;

public class NP_Prio  extends SchedulingAlgorithm{

	public NP_Prio() {
		super();
	}
	
	@Override
	public void addProcess(ProcessControlBlock e) {
		if(processQueue.isEmpty() || processQueue.size() == 1) {
			processQueue.add(e);
		} else {
			for(int i = 1; i < processQueue.size(); i++) {
				if(e.getPriority() < processQueue.get(i).getPriority()) {
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
		MLFQSimulatorGUI.addBlock(new Block(processQueue.get(0).getPID()));
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
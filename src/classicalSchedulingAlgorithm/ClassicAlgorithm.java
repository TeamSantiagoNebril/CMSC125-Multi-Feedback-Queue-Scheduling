package classicalSchedulingAlgorithm;

import process.ProcessControlBlock;

public interface ClassicAlgorithm {
	
	public void addProcess(ProcessControlBlock e);
	
	public ProcessControlBlock executeScheduling();
	
	public boolean isEmptyQueue();
	
	public boolean isReplaced();
}

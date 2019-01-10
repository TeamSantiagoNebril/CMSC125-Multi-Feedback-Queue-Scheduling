package classicalSchedulingAlgorithm;
import java.util.ArrayList;
import process.ProcessControlBlock;

public class SchedulingAlgorithm {
	
	protected int timeSlice;
	protected int currentCounter;
	protected ArrayList<ProcessControlBlock> processQueue;
	protected boolean newRP;
	protected ProcessControlBlock previousProcess;
	protected int ppIndex;
	protected int previousPID;
	protected boolean pendingAddition;

	protected ProcessControlBlock addProcess;
	
	
	public SchedulingAlgorithm() {
		ppIndex = -1;
		processQueue = new ArrayList<ProcessControlBlock>();
		newRP = true;
	}
	
	public boolean hasPendingAddition() {
		
		return pendingAddition;
	}
	
	public ProcessControlBlock getPendingAdditionProcess() {
		return addProcess;
	}
	
	public void setPendingAdditionToFalse() {
		pendingAddition = false;
	}
	
	public ArrayList<ProcessControlBlock> getsQueue() {
		return processQueue;
	}
	
	public boolean isNew() {
		return newRP;
	}
	
	public void addProcess(ProcessControlBlock e) {
		
	}
	
	public ProcessControlBlock executeScheduling() {
		return null;
	}
	
	public boolean isEmptyQueue() {
		if(processQueue.size() == 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean isReplaced() {
		return newRP;
	}
	
	public int getppIndex() {
		return ppIndex;
	}
	
	public void resetPPIndex() {
		ppIndex = -1;
	}
	
	public int getTimeSlice() {
		return timeSlice;
	}
	
	public int getCurrentTimeSlice() {
		return timeSlice - currentCounter;
	}
	
	public ProcessControlBlock removeFirstProcess() {
		return processQueue.remove(0);
	}
}

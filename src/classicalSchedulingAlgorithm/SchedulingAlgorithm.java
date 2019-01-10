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
	
	public SchedulingAlgorithm() {
		ppIndex = -1;
		processQueue = new ArrayList<ProcessControlBlock>();
		newRP = true;
	}
	
	public boolean hasPendingAddition() {
		boolean temp = pendingAddition;
		pendingAddition = false;
		return temp;
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
		return currentCounter;
	}
	
	public ProcessControlBlock removeFirstProcess() {
		return processQueue.remove(0);
	}
}

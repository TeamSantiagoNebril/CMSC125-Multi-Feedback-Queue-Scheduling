package mlfq;

import java.util.ArrayList;
import processInformation.ProcessControlBlock;

public class MLFQQueue {
	private int ID;
	private int priority;
	private ArrayList<ProcessControlBlock> queue;
	
	public MLFQQueue(int priority) {
		this.priority = priority;
		this.ID = priority;
		queue = new ArrayList<ProcessControlBlock>();
	}
}

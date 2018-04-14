package processInformation;

public class ProcessControlBlock {
	private	int pID;
	private	int arrivalTime;
	private	int burstTime;
	private	int priority;
	private	int cpuPreemptionCounter;
	
	public ProcessControlBlock(int pID, int arrivalTime, int burstTime, int priority){
		this.pID = pID;
		this. arrivalTime = arrivalTime;
		this.burstTime = burstTime;
		this.priority = priority;
	}
	
	public void incCPUPreempCounter() {
		cpuPreemptionCounter++;
	}
	
	public int getPID(){
		return pID;
	}
	
	public int getArrivalTime(){
		return arrivalTime;
	}
	
	public int getBurstTime(){
		return burstTime;
	}
	
	public int getPriority(){
		return priority;
	}
	
	public void setPID(int pID){
		this.pID = pID;
	}
	
	public void setArrivalTime(int arrivalTime){
		this.arrivalTime = arrivalTime;
	}
	
	public void setBurstTime(int burstTime){
		this.burstTime = burstTime;
	}
	
	public void setPriority(int priority){
		this.priority = priority;
	}
	
	public Integer getArrivalTimeInInteger() {
		Integer in = new Integer(arrivalTime);
		return in;
	}
	
	
}

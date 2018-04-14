package processInformation;

public class ProcessControlBlock {
	int pID;
	int arrivalTime;
	int burstTime;
	int priority;
	int cpuPreemptionCounter;
	
	public ProcessControlBlock(int pID, int arrivalTime, int burstTime, int priority){
		this.pID = pID;
		this. arrivalTime = arrivalTime;
		this.burstTime = burstTime;
		this.priority = priority;
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
	
	
}

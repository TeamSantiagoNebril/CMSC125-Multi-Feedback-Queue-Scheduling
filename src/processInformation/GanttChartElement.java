package processInformation;

public class GanttChartElement {
	
	private int PID;

	private int beginTime;
	private int EndTime;
	
	public GanttChartElement(int PID, int beginTime, int EndTime) {
		this.PID = PID;
		this.beginTime = beginTime;
		this.EndTime = EndTime;
	}
	
	public int getPID() {
		return PID;
	}

	public int getBeginTime() {
		return beginTime;
	}

	public int getEndTime() {
		return EndTime;
	}

	public void incEndTime() {
		EndTime++;
	}
	
	public void setEndTime(int et) {
		EndTime = et;
	}
}

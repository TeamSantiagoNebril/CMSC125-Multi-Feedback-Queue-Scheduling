package processInformation;

public class GanttChartElement {
	
	private int PID;

	private int beginTime;
	private int endTime;
	
	public GanttChartElement(int PID, int beginTime, int EndTime) {
		this.PID = PID;
		this.beginTime = beginTime;
		this.endTime = EndTime;
	}
	
	public int getPID() {
		return PID;
	}

	public int getBeginTime() {
		return beginTime;
	}

	public int getEndTime() {
		return endTime;
	}

	public void incEndTime() {
		endTime++;
	}
	
	public void setEndTime(int end) {
		endTime = end;
	}
}

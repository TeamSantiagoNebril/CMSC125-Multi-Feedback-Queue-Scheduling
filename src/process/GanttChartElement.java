package process;

public class GanttChartElement {
	
	private int PID;
	private int beginTime;
	private int EndTime;
	private boolean isClosed = false;
	
	public GanttChartElement(int PID, int beginTime) {
		this.PID = PID;
		this.beginTime = beginTime;
		this.EndTime = beginTime;
		isClosed = false;
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
	
	public void setEndTime(int time) {
		EndTime = time;
	}
	
	public void close() {
		isClosed = true;
		incEndTime();
	}
	
	public boolean isClose() {
		if(isClosed) {
			return true;
		} else {
			return false;
		}
	}
}

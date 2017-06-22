package helper;

import java.util.TimerTask;

public class Timer extends Thread {
	@Override
	public synchronized void start() {
		runState = true;
		super.start();
	}
	public synchronized void Pause(){
		runState = false;
	}
	public synchronized void ReStart(){ 
		runState = true;
	}
	
	public void setStop(){
		isStop = true;
	}
	public boolean runState = false;
	boolean isStop;
	int time;
	TimerTask task;
	public void run() {
		while (!isStop) {
			try {
				if (runState) {
					if (task != null) {
						task.run();
					}
				}
				Thread.currentThread();
				Thread.sleep(time);
			} catch (InterruptedException e) {
			}
		}
	}

	public void SetTimerTaskAndTime(TimerTask task,int time) {
		this.task = task;
		this.time =time;
	}
	
}

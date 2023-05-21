package game_4_utils;

import static game_4_main.Auswahlbereich.*;

import java.util.Timer;
import java.util.TimerTask;

public class smallTimer {
	
	private TimerTask task;
	private Timer timer = new Timer();
	
	public smallTimer() {
		solutionSubmitted = true;
		task = new TimerTask() {
			@Override
			public void run() {
				solutionSubmitted = false;
			}
		};
		timer.schedule(task, 5000);
	}
	
}

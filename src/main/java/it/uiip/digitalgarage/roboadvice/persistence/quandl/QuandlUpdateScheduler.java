package it.uiip.digitalgarage.roboadvice.persistence.quandl;

import java.util.Calendar;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class QuandlUpdateScheduler {

	public static void execute() {
		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 2);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		
		Timer timer = new Timer();
		timer.schedule(new QuandlUpdateTask(), today.getTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.SECONDS));
	}
	
	public static void main(String[] args) {
		QuandlUpdateScheduler.execute();
	}
	
}

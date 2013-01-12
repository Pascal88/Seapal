package en.htwg.seapal.gui.listener.trips;

import java.util.Calendar;

import en.htwg.seapal.gui.activity.TripsActivity;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;

public class DateChangedListener implements OnDateChangedListener {
	
	private DatePicker other = null;
	private TripsActivity activity = null;
	
	public DateChangedListener(TripsActivity activity, DatePicker other) {
		this.activity = activity;
		this.other = other;
	}

	@Override
	public void onDateChanged(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		Calendar first = Calendar.getInstance();
		first.set(year, monthOfYear, dayOfMonth);
		
		Calendar second = Calendar.getInstance();
		second.set(other.getYear(), other.getMonth(), other.getDayOfMonth());
		
		activity.dateChanged(first.getTimeInMillis(), second.getTimeInMillis());
	}

}

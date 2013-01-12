package en.htwg.seapal.gui.listener.trips;

import en.htwg.seapal.gui.fragment.NewTripDialogFragment;
import en.htwg.seapal.model.models.Trip;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;

public class NewTripClickListener implements OnClickListener {

	private Activity activity = null;
	private ArrayAdapter<Trip> adapter = null;
	
	public NewTripClickListener(Activity activity, ArrayAdapter<Trip> adapter) {
		this.activity = activity;
		this.adapter = adapter;
	}
	
	@Override
	public void onClick(View v) {
		NewTripDialogFragment dialog = new NewTripDialogFragment(activity, adapter);
		dialog.show(activity.getFragmentManager(), "NewTrip");
	}

}

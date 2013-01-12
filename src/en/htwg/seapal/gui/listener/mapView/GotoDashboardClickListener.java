package en.htwg.seapal.gui.listener.mapView;

import en.htwg.seapal.gui.activity.DashboardActivity;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class GotoDashboardClickListener implements OnClickListener {

	private Activity activity = null;
	
	public GotoDashboardClickListener(Activity activity) {
		this.activity = activity;
	}
	@Override
	public void onClick(View v) {
		Intent intent = new Intent(activity, DashboardActivity.class);
		activity.startActivity(intent);
	}

}

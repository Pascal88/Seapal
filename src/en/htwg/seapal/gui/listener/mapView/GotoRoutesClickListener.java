package en.htwg.seapal.gui.listener.mapView;

import en.htwg.seapal.gui.activity.RouteActivity;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class GotoRoutesClickListener implements OnClickListener {

	private Activity activity = null;
	
	public GotoRoutesClickListener(Activity activity) {
		this.activity = activity;
	}
	@Override
	public void onClick(View v) {
		Intent intent = new Intent(activity, RouteActivity.class);
		activity.startActivity(intent);
	}

}

package en.htwg.seapal.gui.listener.mapView;

import en.htwg.seapal.gui.activity.MarkActivity;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class GotoMarksClickListener implements OnClickListener {

	private Activity activity = null;
	
	public GotoMarksClickListener (Activity activity) {
		this.activity = activity;
	}
	
	@Override
	public void onClick(View v) {
		Intent intent = new Intent(activity, MarkActivity.class);
		activity.startActivity(intent);
	}

}

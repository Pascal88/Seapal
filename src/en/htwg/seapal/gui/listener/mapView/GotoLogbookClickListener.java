package en.htwg.seapal.gui.listener.mapView;

import en.htwg.seapal.gui.activity.LogbookActivity;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class GotoLogbookClickListener implements OnClickListener {

	private Activity activity = null;
	
	public GotoLogbookClickListener(Activity activity) {
		this.activity = activity;
	}
	
	@Override
	public void onClick(View v) {
		Intent intent = new Intent(activity, LogbookActivity.class);
		activity.startActivity(intent);
	}

}

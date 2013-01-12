package en.htwg.seapal.gui.listener.route;

import en.htwg.seapal.gui.activity.RouteActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class ListItemSelectedListener implements OnItemClickListener {

private RouteActivity activity = null;
	
	public ListItemSelectedListener(RouteActivity activity) {
		this.activity = activity;
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Log.d("Listener", "Selected " + arg2);
		activity.setSelected(arg2);
		arg1.setSelected(true);
	}

}

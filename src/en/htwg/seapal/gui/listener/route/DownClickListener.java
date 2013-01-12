package en.htwg.seapal.gui.listener.route;

import en.htwg.seapal.controller.RouteController;
import en.htwg.seapal.gui.activity.RouteActivity;
import android.database.Cursor;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

public class DownClickListener implements OnClickListener {

	private RouteActivity activity = null;
	
	public DownClickListener(RouteActivity activity) {
		this.activity = activity;
	}
	
	@Override
	public void onClick(View v) {
		if(activity.getSelected() != 0) {
			ListView listView = activity.getListView().getListView();
			
			RouteController routeController = new RouteController(activity);
			Cursor rp1 = (Cursor) listView.getItemAtPosition(activity.getSelected());
			
			routeController.swap(rp1, "down");
			activity.setSelected(activity.getSelected()+1);
			listView.invalidateViews();
		}
	}

}

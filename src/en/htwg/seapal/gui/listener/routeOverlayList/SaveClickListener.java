package en.htwg.seapal.gui.listener.routeOverlayList;

import en.htwg.seapal.controller.RouteController;
import en.htwg.seapal.gui.activity.MapViewActivity;
import en.htwg.seapal.gui.overlay.RouteOverlayList;
import android.view.View;
import android.view.View.OnClickListener;

public class SaveClickListener implements OnClickListener {

	private MapViewActivity activity = null;
	
	public SaveClickListener(MapViewActivity activity) {
		this.activity = activity;
	}
	
	@Override
	public void onClick(View v) {
		RouteOverlayList routeOverlayList = activity.getRouteOverlayList();
		RouteController routeController = new RouteController(activity);
		routeController.addRoute(routeOverlayList.getList());
	}

}

package en.htwg.seapal.gui.listener.routeOverlayList;

import en.htwg.seapal.gui.activity.MapViewActivity;
import en.htwg.seapal.gui.overlay.RouteOverlayList;
import android.view.View;
import android.view.View.OnClickListener;

public class FinishClickListener implements OnClickListener {

	private MapViewActivity activity = null;
	
	public FinishClickListener(MapViewActivity activity) {
		this.activity = activity;
	}
	
	@Override
	public void onClick(View v) {
		RouteOverlayList routeOverlayList = activity.getRouteOverlayList();
		routeOverlayList.finish();
		activity.getDoubleTapDragListener().endRoute();
	}

}

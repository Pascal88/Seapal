package en.htwg.seapal.gui.listener.SelectOnMap;

import com.google.android.maps.GeoPoint;

import en.htwg.seapal.gui.activity.SelectMarkOnMapActivity;
import en.htwg.seapal.gui.overlay.RouteOverlay;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class SelectOnMapTouchListener implements OnTouchListener {

	private SelectMarkOnMapActivity activity = null;
	private Boolean wasDown = false;
	
	public SelectOnMapTouchListener (SelectMarkOnMapActivity activity) {
		this.activity = activity;
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch(event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			wasDown = true;
		case MotionEvent.ACTION_UP:
			if(wasDown) {
				GeoPoint gp = activity.getMapView().getProjection().fromPixels((int) event.getX(), (int) event.getY());
				RouteOverlay routeOverlay = new RouteOverlay(activity, gp);
				activity.getMapView().getOverlays().add(routeOverlay);
				activity.setRouteOVerlay(routeOverlay);
				wasDown = false;
				return true;
			}
		}
		
		return false;
	}

}

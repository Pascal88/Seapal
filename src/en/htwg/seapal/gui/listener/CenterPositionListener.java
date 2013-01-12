package en.htwg.seapal.gui.listener;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;

import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;

public class CenterPositionListener implements OnMenuItemClickListener {
	
	private MapController mapController = null;
	private GeoPoint geoPoint = null;
	
	public CenterPositionListener(MapController mapController, GeoPoint geoPoint) {
		this.mapController = mapController;
		this.geoPoint = geoPoint;
	}

	public boolean onMenuItemClick(MenuItem arg0) {
		mapController.setCenter(geoPoint);
		return true;
	}

}

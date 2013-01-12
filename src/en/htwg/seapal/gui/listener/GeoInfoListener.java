package en.htwg.seapal.gui.listener;


import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.Overlay;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import en.htwg.seapal.controller.GeoInformationController;
import en.htwg.seapal.gui.activity.MapViewActivity;
import en.htwg.seapal.gui.overlay.AimOverlay;
import en.htwg.seapal.gui.overlay.MyGoogleLocationOverlay;
import en.htwg.seapal.model.models.GeoInformation;

public class GeoInfoListener implements LocationListener {

	private GeoInformationController geoInformationController = null;
	private MapViewActivity activity = null;
	private MyGoogleLocationOverlay locationOverlay = null;

	public GeoInfoListener(MapViewActivity activity) {
		this.geoInformationController = new GeoInformationController(activity);
		this.activity = activity;
	}




	@Override
	public synchronized void onLocationChanged(Location location) {
		geoInformationController.locationChanged(location);
		
		GeoInformation geoInformation = geoInformationController.getLast();
		GeoPoint geo = new GeoPoint(geoInformation.getLatitude(), geoInformation.getLongitude());
		
		// Add a location mark
		List<Overlay> list = activity.getMapView().getOverlays();
		list.remove(locationOverlay);// delete old
		locationOverlay = new MyGoogleLocationOverlay(activity.getResources(), geo);//make new one
		activity.setLocationOverlay(locationOverlay);
		list.add(locationOverlay);//add it to map
		
		AimOverlay aim = activity.getAim();
		if(aim != null) {//if there is a aim set
			list.remove(aim);//let it be redrawn
			list.add(aim);
		}
		
		activity.editPositionText(geoInformation);//change Main Info Text
		activity.getMapView().getController().animateTo(geo);//show on MapView that location is now a different one
	}

	@Override
	public void onProviderDisabled(String provider) {
		// unused
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// unused
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// unused
		
	}

}

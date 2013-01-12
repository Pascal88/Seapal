package en.htwg.seapal.gui.activity;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

import en.htwg.seapal.R;
import en.htwg.seapal.controller.GeoInformationController;
import en.htwg.seapal.gui.listener.SelectOnMap.SelectOnMapTouchListener;
import en.htwg.seapal.gui.overlay.RouteOverlay;
import en.htwg.seapal.model.models.GeoInformation;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;

public class SelectMarkOnMapActivity extends MapActivity implements IMapActivity{

	private MapView mapView = null;
	private MapController mapController = null;
	private RouteOverlay overlay = null;
	
	
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.selectonmap);
		
		mapView = (MapView) findViewById(R.id.mapviewGoogleMap);
		mapView.setOnTouchListener(new SelectOnMapTouchListener(this));
		
		mapController = mapView.getController();
		mapView.setSatellite(false);
		mapController.setZoom(17);

		GeoInformationController geoInformationController = new GeoInformationController(this);
		GeoInformation geoinfo = geoInformationController.getLast();

		GeoPoint geo = new GeoPoint(geoinfo.getLatitude(),
				geoinfo.getLongitude());

		mapController.setCenter(geo);
		getActionBar().hide();
	}

	@Override
	public MapView getMapView() {
		return mapView;
	}
	
	public void setRouteOVerlay(RouteOverlay r) {
		if(overlay != null) mapView.getOverlays().remove(overlay);
		this.overlay = r;
		Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.addToRoute);
		builder.setCancelable(true);
		builder.setPositiveButton(android.R.string.ok, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent data = new Intent();
				data.putExtra("lat", overlay.getGeoPoint().getLatitudeE6());
				data.putExtra("lon", overlay.getGeoPoint().getLongitudeE6());
				setResult(Activity.RESULT_OK, data);
				finish();
			}
		});

		builder.setNegativeButton(R.string.negative, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		builder.create();
		builder.show();
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	
	
}

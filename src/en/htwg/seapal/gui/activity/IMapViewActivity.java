package en.htwg.seapal.gui.activity;

import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

import en.htwg.seapal.gui.listener.mapView.IMapGestureListener;
import en.htwg.seapal.gui.overlay.AimOverlay;
import en.htwg.seapal.gui.overlay.DistanceOverlayList;
import en.htwg.seapal.gui.overlay.MyGoogleLocationOverlay;
import en.htwg.seapal.gui.overlay.RouteOverlayList;
import en.htwg.seapal.model.models.GeoInformation;

public interface IMapViewActivity {

	public void editPositionText(GeoInformation geoInformation);

	public void useGoogleMap();

	public void useOsmMap();

	public MapView getMapView();

	public void showContextMenu(Overlay gp);

	public void showMarkMenu(Overlay gp);

	public DistanceOverlayList getDistanceOverlayList();

	public RouteOverlayList getRouteOverlayList();

	public AimOverlay getAim();

	public void setAim(AimOverlay aim);

	public MyGoogleLocationOverlay getLocationOverlay();

	public void setLocationOverlay(MyGoogleLocationOverlay locationOverlay);

	public Overlay getMapMenuOverlay();

	public void setMapMenuOverlay(Overlay mapMenuOverlay);

	public IMapGestureListener getDoubleTapDragListener();
		
	public void addMark();
		
	public void addRoute();
		
	public void calcDistance();
		
	public void centerMap ();
		
	public void discardTarget();
	
	public void startAim();
		
}
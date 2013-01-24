package en.htwg.seapal.gui.listener.mapView;

import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.Overlay;

import en.htwg.seapal.R;
import en.htwg.seapal.controller.GeoInformationController;
import en.htwg.seapal.controller.MarkController;
import en.htwg.seapal.gui.IMenuCallback;
import en.htwg.seapal.gui.activity.MapViewActivity;
import en.htwg.seapal.gui.overlay.AimOverlay;
import en.htwg.seapal.gui.overlay.DistanceOverlay;
import en.htwg.seapal.gui.overlay.DistanceOverlayList;
import en.htwg.seapal.gui.overlay.IOverlay;
import en.htwg.seapal.gui.overlay.MarkOverlay;
import en.htwg.seapal.gui.overlay.RouteOverlay;
import en.htwg.seapal.gui.overlay.RouteOverlayList;
import en.htwg.seapal.model.models.Mark;

public class MapMenuItemClickListener implements IMenuCallback {

	private MapViewActivity activity = null;
	
	public MapMenuItemClickListener(MapViewActivity activity) {
		this.activity = activity;
	}
	
	@Override
	public void MyMenuItemClick(int itemId) {
		List<Overlay> list = activity.getMapView().getOverlays();
		AimOverlay aim = activity.getAim();
		Overlay mapMenuOverlay = activity.getMapMenuOverlay();
		IMapGestureListener doubleTapDragListener = activity.getDoubleTapDragListener();
		RouteOverlayList routeOverlayList = activity.getRouteOverlayList();
		DistanceOverlayList distOverlayList = activity.getDistanceOverlayList();
		GeoInformationController geoInformationController = new GeoInformationController(activity);
		
		
			switch(itemId) {
				case R.id.crosshairMenuSetAim:
					if(aim != null) list.remove(aim);
					GeoPoint geo1 = geoInformationController.getLastGeoPoint();
					aim = new AimOverlay(activity, geo1, ((IOverlay) mapMenuOverlay).getGeoPoint());
					activity.setAim(aim);
					list.add(aim);
					activity.setMapMenuOverlay(null);
				case R.id.crosshairMenuSetMark:
					Mark mark = new MarkController(activity).addMark(activity.getResources(), ((IOverlay) mapMenuOverlay).getGeoPoint());
					MarkOverlay mOver = new MarkOverlay(activity, ((IOverlay) mapMenuOverlay).getGeoPoint(), mark.getName());
					list.remove(mapMenuOverlay);
					list.add(mOver);
					activity.setMapMenuOverlay(null);
					break;
				case R.id.crosshairMenuSetRoute:
					doubleTapDragListener.startRoute();
					GeoPoint gPoint = ((IOverlay) mapMenuOverlay).getGeoPoint();
					routeOverlayList.add(new RouteOverlay(activity, gPoint));
					list.remove(mapMenuOverlay);
					activity.setMapMenuOverlay(null);
					break;
				case R.id.crosshairMenuDistance:
					doubleTapDragListener.startDistance();
					GeoPoint geoPoint = ((IOverlay) mapMenuOverlay).getGeoPoint();
					distOverlayList.add(new DistanceOverlay(activity, geoPoint));
					list.remove(mapMenuOverlay);
					activity.setMapMenuOverlay(null);
					break;
				case R.id.crosshairMenuDelete:
					list.remove(mapMenuOverlay);
					activity.setMapMenuOverlay(null);
					break;
			}		
		
	}

}

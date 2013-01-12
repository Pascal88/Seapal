package en.htwg.seapal.gui.listener.mapView;

import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.Overlay;

import en.htwg.seapal.R;
import en.htwg.seapal.controller.GeoInformationController;
import en.htwg.seapal.gui.IMenuCallback;
import en.htwg.seapal.gui.activity.MapViewActivity;
import en.htwg.seapal.gui.fragment.MarkDialogFragment;
import en.htwg.seapal.gui.overlay.AimOverlay;
import en.htwg.seapal.gui.overlay.IOverlay;

public class MarkMenuItemClickListener implements IMenuCallback {

	private MapViewActivity activity = null;
	
	public MarkMenuItemClickListener(MapViewActivity activity) {
		this.activity = activity;
	}
	
	@Override
	public void MyMenuItemClick(int itemId) {
		List<Overlay> list = activity.getMapView().getOverlays();
		AimOverlay aim = activity.getAim();
		Overlay mapMenuOverlay = activity.getMapMenuOverlay();
		IMapGestureListener doubleTapDragListener = activity.getDoubleTapDragListener();
		GeoInformationController geoInformationController = new GeoInformationController(activity);
		
		switch(itemId) {
			case R.id.markMenuAim:
				if(aim != null) list.remove(aim);
				GeoPoint geo = geoInformationController.getLastGeoPoint();
				aim = new AimOverlay(activity, geo, ((IOverlay) mapMenuOverlay).getGeoPoint());
				activity.setAim(aim);
				list.add(aim);
				break;
			case R.id.markMenuDelete:
				list.remove(mapMenuOverlay);
				activity.setMapMenuOverlay(null);
				break;
			case R.id.markMenuMove:
				doubleTapDragListener.nextIsDrag((IOverlay) mapMenuOverlay);
				break;
			case R.id.markMenuRename:
				MarkDialogFragment mdia = new MarkDialogFragment(activity,(IOverlay) mapMenuOverlay);
				mdia.show(activity.getFragmentManager(), "MarkDialog");
				break;
		}	
	}

}

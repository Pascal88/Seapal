package en.htwg.seapal.gui.listener.mapView;

import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

import en.htwg.seapal.gui.activity.MapViewActivity;
import en.htwg.seapal.gui.overlay.DistanceOverlay;
import en.htwg.seapal.gui.overlay.IOverlay;
import en.htwg.seapal.gui.overlay.LongPressOverlay;
import en.htwg.seapal.gui.overlay.MarkOverlay;
import en.htwg.seapal.gui.overlay.RouteOverlay;

import android.content.ClipData;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class MapGestureTapListener implements IMapGestureListener {

	private MapViewActivity mapActivity;
	private LongPressOverlay over = null;
	private Boolean isDrag = false;
	private Boolean isDistance = false;
	private Boolean isRoute = false;
	private IOverlay ov = null;
	
	public MapGestureTapListener(MapViewActivity mapActivity) {
		this.mapActivity = mapActivity;
	}
	
	/* (non-Javadoc)
	 * @see en.htwg.seapal.gui.IMapGestureListener#onDoubleTap(android.view.MotionEvent)
	 */
	@Override
	public boolean onDoubleTap(MotionEvent e) {
		Log.d("Tap", "Double");
		return false;
	}

	/* (non-Javadoc)
	 * @see en.htwg.seapal.gui.IMapGestureListener#onDoubleTapEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onDoubleTapEvent(MotionEvent e) {
		Log.d("Tap", "DoubleEvent");
		return false;
	}

	/* (non-Javadoc)
	 * @see en.htwg.seapal.gui.IMapGestureListener#onSingleTapConfirmed(android.view.MotionEvent)
	 */
	@Override
	public boolean onSingleTapConfirmed(MotionEvent event) {
		return false;
	}

	/* (non-Javadoc)
	 * @see en.htwg.seapal.gui.IMapGestureListener#onDown(android.view.MotionEvent)
	 */
	@Override
	public boolean onDown(MotionEvent arg0) {
		Log.d("Tap", "Down");
		if(isDrag && ov != null) {
			List<Overlay> list = mapActivity.getMapView().getOverlays();
			list.remove(ov);
			ImageView image = new ImageView(mapActivity.getApplicationContext());
			image.setImageBitmap(ov.getBitmap());
			
			mapActivity.getMapView().startDrag(ClipData.newPlainText("", ""), new View.DragShadowBuilder(), ov, 0);	
			image.setVisibility(View.INVISIBLE);
			
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see en.htwg.seapal.gui.IMapGestureListener#onFling(android.view.MotionEvent, android.view.MotionEvent, float, float)
	 */
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		Log.d("Tap", "fling");
		return false;
	}

	/* (non-Javadoc)
	 * @see en.htwg.seapal.gui.IMapGestureListener#onLongPress(android.view.MotionEvent)
	 */
	@Override
	public void onLongPress(MotionEvent event) {
		Log.d("Tap", "LongPress");
		
		List<Overlay> list = mapActivity.getMapView().getOverlays();
		
		IOverlay overlay = hitTestOverlays(list, event);
		if(overlay != null) {
			list.remove(overlay);
			ImageView image = new ImageView(mapActivity.getApplicationContext());
			image.setImageBitmap(((IOverlay) overlay).getBitmap());
			
			mapActivity.getMapView().startDrag(ClipData.newPlainText("", ""), new View.DragShadowBuilder(), overlay, 0);	
			image.setVisibility(View.INVISIBLE);

		} else {
			GeoPoint gp = mapActivity.getMapView().getProjection().fromPixels((int) event.getX(), (int) event.getY());
			
			if(over != null) {
				list.remove(over);
				//over.setHasChanged(true);
			}
			
			over = new LongPressOverlay(mapActivity, gp);
			list.add(over);
		}
	}

	/* (non-Javadoc)
	 * @see en.htwg.seapal.gui.IMapGestureListener#onScroll(android.view.MotionEvent, android.view.MotionEvent, float, float)
	 */
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see en.htwg.seapal.gui.IMapGestureListener#onShowPress(android.view.MotionEvent)
	 */
	@Override
	public void onShowPress(MotionEvent e) {
		Log.d("Tap", "showpress");
		
	}

	/* (non-Javadoc)
	 * @see en.htwg.seapal.gui.IMapGestureListener#onSingleTapUp(android.view.MotionEvent)
	 */
	@Override
	public boolean onSingleTapUp(MotionEvent event) {
		Log.d("Tap", "SingleUp");
		
		if(!isDistance && !isRoute) {
			List<Overlay> list = mapActivity.getMapView().getOverlays();
			
			IOverlay overlay = hitTestOverlays(list, event);
			if(overlay != null && overlay instanceof LongPressOverlay) {
				mapActivity.showContextMenu((Overlay) overlay);
				Log.d("Tap", "SingleUpMap");
				return true;
			} else if(overlay != null && overlay instanceof MarkOverlay) {
				mapActivity.showMarkMenu((Overlay) overlay);
				Log.d("Tap", "SingleUpMArks");
				return true;
			}
		} else if(isDistance){
			GeoPoint geoPoint = mapActivity.getMapView().getProjection().fromPixels((int) event.getX(), (int) event.getY());
			mapActivity.getDistanceOverlayList().add(new DistanceOverlay(mapActivity, geoPoint));
		} else if(isRoute){
			GeoPoint geoPoint = mapActivity.getMapView().getProjection().fromPixels((int) event.getX(), (int) event.getY());
			mapActivity.getRouteOverlayList().add(new RouteOverlay(mapActivity, geoPoint));
		}
		return false;
	}

	private Boolean hitTest(MapView mapView, Point touched, GeoPoint locationOverlay, Bitmap bmp) {
		
		Point location = new Point();
		mapView.getProjection().toPixels(locationOverlay, location);
		RectF r = new RectF(location.x - ((bmp.getWidth()/4)*3), location.y - ((bmp.getHeight()/4)*3), location.x + ((bmp.getWidth()/4)*3), location.y + ((bmp.getHeight()/4)*3));
		
		if(r.contains(touched.x, touched.y)) {
			return true;
		}
		return false;
	}
	
	private IOverlay hitTestOverlays(List<Overlay> list, MotionEvent event){
		for(Overlay overlay: list){
			if(overlay instanceof IOverlay){
				if(hitTest(mapActivity.getMapView(), new Point((int) event.getX(), (int) event.getY()), ((IOverlay) overlay).getGeoPoint(), ((IOverlay) overlay).getBitmap())) {
					return (IOverlay) overlay;
				}
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see en.htwg.seapal.gui.IMapGestureListener#onDrag(android.view.View, android.view.DragEvent)
	 */
	@Override
	public boolean onDrag(View v, DragEvent event) {
		Log.d("listener", "drag");
		
	    switch (event.getAction()) {
	    case DragEvent.ACTION_DRAG_STARTED:
	    	((IOverlay) event.getLocalState()).drag();
	    	break;
	    case DragEvent.ACTION_DRAG_LOCATION:
	    	IOverlay overlayTmp = (IOverlay) event.getLocalState();
	    	mapActivity.getMapView().getOverlays().remove((Overlay) overlayTmp);
	    	overlayTmp.setGeoPoint(new Point( (int) event.getX(), (int) event.getY()));
		    mapActivity.getMapView().getOverlays().add((Overlay) overlayTmp);
		    break;
	    case DragEvent.ACTION_DROP:
	      IOverlay overlay = (IOverlay) event.getLocalState();
	      overlay.drop();
	      overlay.setGeoPoint(new Point( (int) event.getX(), (int) event.getY()));
	      mapActivity.getMapView().getOverlays().add((Overlay) overlay);
	      break;
	      default:
	      break;
	    }
	    return true;
	}

	@Override
	public void nextIsDrag(IOverlay overlay) {
		this.isDrag = true;
		this.ov = overlay;
	}

	@Override
	public void startDistance() {
		isDistance = true;
	}

	@Override
	public void endDistance() {
		isDistance = false;
	}

	@Override
	public void startRoute() {
		isRoute = true;
	}

	@Override
	public void endRoute() {
		isRoute = false;
	}
	
	
	
	
}

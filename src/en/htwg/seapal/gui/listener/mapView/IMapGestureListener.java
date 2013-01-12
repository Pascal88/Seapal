package en.htwg.seapal.gui.listener.mapView;

import en.htwg.seapal.gui.overlay.IOverlay;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnDragListener;

public interface IMapGestureListener extends OnDoubleTapListener, OnGestureListener, OnDragListener {

	public boolean onDoubleTap(MotionEvent e);

	public boolean onDoubleTapEvent(MotionEvent e);

	public boolean onSingleTapConfirmed(MotionEvent event);

	public boolean onDown(MotionEvent arg0);

	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY);

	public void onLongPress(MotionEvent event);

	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY);

	public void onShowPress(MotionEvent e);

	public boolean onSingleTapUp(MotionEvent event);

	public boolean onDrag(View v, DragEvent event);
	
	public void nextIsDrag(IOverlay overlay);
	
	public void startDistance();
	
	public void endDistance();
	
	public void startRoute();
	
	public void endRoute();
}
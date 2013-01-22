package en.htwg.seapal.gui.overlay;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

import en.htwg.seapal.R;
import en.htwg.seapal.gui.activity.IMapActivity;

public class RouteOverlay extends Overlay implements IOverlay {

	private GeoPoint geoPoint;
	private IMapActivity activity;
	private Bitmap bmp = null;
	
	public RouteOverlay(IMapActivity activity, GeoPoint geoPoint) {
		this.activity = activity;
		this.geoPoint = geoPoint;
		this.bmp = BitmapFactory.decodeResource(activity.getResources(), R.drawable.ann_route);
	}
	
	@Override
	public boolean draw(Canvas canvas, MapView mapView, boolean shadow, long when) {
		super.draw(canvas, mapView, shadow);
		
		Paint paint = new Paint();
		paint.setStrokeWidth(1);
		paint.setARGB(255, 255, 255, 255);
		paint.setStyle(Paint.Style.STROKE);
		
		Point point = new Point();
		mapView.getProjection().toPixels(geoPoint, point);
		
		canvas.drawBitmap(bmp, point.x - (bmp.getWidth()/8), point.y - bmp.getHeight(), paint);
		return true;		
	}
	
	@Override
	public Bitmap getBitmap() {
		return bmp;
	}

	@Override
	public GeoPoint getGeoPoint() {
		return geoPoint;
	}

	@Override
	public void setGeoPoint(Point p) {
		this.geoPoint = activity.getMapView().getProjection().fromPixels(p.x, p.y);		
	}

	@Override
	public void setGeoPoint(GeoPoint p) {
		this.geoPoint = p;		
	}

	@Override
	public void drag() {
		// not needed		
	}

	@Override
	public void drop() {
		// not needed		
	}

}

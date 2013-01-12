package en.htwg.seapal.gui.overlay;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

import en.htwg.seapal.R;
import en.htwg.seapal.gui.activity.MapViewActivity;

public class MarkOverlay extends Overlay implements IOverlay{

	private GeoPoint geoPoint;
	private MapViewActivity activity;
	private String name = "";
	private Bitmap bmp = null;
	private Bitmap normal = null;
	private Bitmap drag = null;
	
	public MarkOverlay(MapViewActivity activity, GeoPoint point, String name) {
		this.geoPoint = point;
		this.activity = activity;
		this.name = name;
		normal = BitmapFactory.decodeResource(activity.getResources(), R.drawable.ann_mark);
		drag = BitmapFactory.decodeResource(activity.getResources(), R.drawable.ann_mark_active);
		bmp = normal;
	}
	
	@Override
	public boolean draw(Canvas canvas, MapView mapView, boolean shadow, long when) {
		Paint paint = new Paint();
		
		super.draw(canvas, mapView, shadow);
		
		paint.setStrokeWidth(1);
		paint.setARGB(255, 255, 255, 255);
		paint.setStyle(Paint.Style.STROKE);
		
		Point point = new Point();
		mapView.getProjection().toPixels(geoPoint, point);
		
		Paint paintText = new Paint();
		paintText.setARGB(255, 0, 0, 0);
		paintText.setColor(Color.BLACK);
		paintText.setStyle(Paint.Style.FILL);
		paintText.setTextSize(15.0f);
		paintText.setTypeface(Typeface.create("bold", Typeface.BOLD));
		
		Rect bounds = new Rect();
		paintText.getTextBounds(name, 0, name.length()-1, bounds);
		
		canvas.drawBitmap(bmp, point.x - (bmp.getWidth()/2), point.y - (bmp.getHeight()/2), paint);
		canvas.drawText(name, point.x - bounds.centerX() , point.y - ((bmp.getHeight()/2) + bounds.height()), paintText);
		
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
		geoPoint = activity.getMapView().getProjection().fromPixels(p.x, p.y);
	}

	@Override
	public void setGeoPoint(GeoPoint p) {
		geoPoint = p;
	}

	@Override
	public void drag() {
		bmp = drag;
	}

	@Override
	public void drop() {
		bmp = normal;
	}
}

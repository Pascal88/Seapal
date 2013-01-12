package en.htwg.seapal.gui.overlay;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

import en.htwg.seapal.R;
import en.htwg.seapal.controller.GeoInformationController;
import en.htwg.seapal.gui.activity.MapViewActivity;

public class AimOverlay extends Overlay {
	
	private MapViewActivity activity = null;
	private GeoPoint position = null;
	private GeoPoint aim = null;
	private Bitmap bmp = null;
	private GeoInformationController geoInformationController = null;
	
	public AimOverlay(MapViewActivity activity, GeoPoint position, GeoPoint aim) {
		this.activity = activity;
		this.position = position;
		this.aim = aim;
		this.bmp = BitmapFactory.decodeResource(this.activity.getResources(), R.drawable.aimarrow);
		geoInformationController = new GeoInformationController(activity);
	}

	@Override
	public boolean draw(Canvas canvas, MapView mapView, boolean shadow, long when) {

		Paint paint = new Paint();
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		paint.setColor(activity.getResources().getColor(R.color.aimarrowcolor));
		
		Point point = new Point();
		mapView.getProjection().toPixels(position, point);
		
		Rect r = new Rect(point.x - (bmp.getWidth()/6), point.y - (bmp.getHeight() * 8), point.x + (bmp.getWidth()/6), point.y);
		
		canvas.rotate(180 + (int) geoInformationController.calcCogOf(aim.getLatitudeE6(), aim.getLongitudeE6()), point.x, point.y);
		canvas.drawBitmap(bmp, point.x - (bmp.getWidth()/2), point.y - (bmp.getHeight() * 9), paint);
		canvas.drawRect(r, paint);
		canvas.rotate(180 - (int) geoInformationController.calcCogOf(aim.getLatitudeE6(), aim.getLongitudeE6()), point.x, point.y);
		
		return super.draw(canvas, mapView, shadow, when);
	}
}

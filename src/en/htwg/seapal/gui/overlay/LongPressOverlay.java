package en.htwg.seapal.gui.overlay;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.location.Location;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

import en.htwg.seapal.R;
import en.htwg.seapal.controller.GeoInformationController;
import en.htwg.seapal.gui.activity.MapViewActivity;

public class LongPressOverlay extends Overlay implements IOverlay{

	private MapViewActivity activity = null;
	private GeoPoint geoPoint = null;
	private Bitmap bmp = null;
	private Bitmap drag = null;
	private Bitmap normal = null;
	private GeoInformationController geoInformationController = null;

	public LongPressOverlay(MapViewActivity activity, GeoPoint geoPoint) {
		this.activity = activity;
		this.geoPoint = geoPoint;
		geoInformationController = new GeoInformationController(activity);
		normal = BitmapFactory.decodeResource(activity.getResources(),
				R.drawable.haircross);
		
		drag = BitmapFactory.decodeResource(activity.getResources(),
				R.drawable.haircross_active);
		bmp = normal;
		activity.setMapMenuOverlay(this);
	}

	@Override
	public boolean draw(Canvas canvas, MapView mapView, boolean shadow,
			long when) {
		Paint paint = new Paint();

		paint.setStrokeWidth(1);
		paint.setARGB(255, 255, 255, 255);
		paint.setStyle(Paint.Style.STROKE);

		Point point = new Point();
		mapView.getProjection().toPixels(geoPoint, point);

		canvas.drawBitmap(bmp, point.x - (bmp.getWidth()/2), point.y - (bmp.getHeight()/2), paint);

		NumberFormat numberFormat = new DecimalFormat("000.00");
		numberFormat.setRoundingMode(RoundingMode.DOWN);

		String topLine = "Lat " + Location.convert(geoPoint.getLatitudeE6() * 0.000001, Location.FORMAT_SECONDS) + " Lon " + Location.convert(geoPoint.getLongitudeE6() * 0.000001, Location.FORMAT_SECONDS);
		String secondLine = "BTM "
				+ numberFormat.format(geoInformationController.bearingTo(
						geoPoint.getLatitudeE6(), geoPoint.getLongitudeE6()))
				+ "° DTM "
				+ numberFormat
						.format(geoInformationController.distanceTo(
								geoPoint.getLatitudeE6(),
								geoPoint.getLongitudeE6()) / 1852) + " nm";
		
		Paint paintText = new Paint();
		paintText.setARGB(255, 0, 0, 0);
		paintText.setColor(Color.BLACK);
		paintText.setStyle(Paint.Style.FILL);
		paintText.setTextSize(15.0f);
		paintText.setTypeface(Typeface.create("bold", Typeface.BOLD));
		
		Rect boundsTop = new Rect();
		paintText.getTextBounds(topLine, 0, topLine.length()-1, boundsTop);
		
		Rect boundsSec = new Rect();
		paintText.getTextBounds(secondLine, 0, secondLine.length()-1, boundsSec);
		
		canvas.drawText(topLine, point.x - boundsTop.centerX() , point.y - ((bmp.getHeight()/3) + boundsTop.height() + boundsTop.height()/2 + boundsSec.height()), paintText);
		canvas.drawText(secondLine, point.x - boundsSec.centerX(), point.y - ((bmp.getHeight()/3) + boundsSec.height()), paintText);
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

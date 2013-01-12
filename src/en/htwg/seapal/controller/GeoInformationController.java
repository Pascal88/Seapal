package en.htwg.seapal.controller;

import java.sql.Date;
import java.util.List;

import com.google.android.maps.GeoPoint;

import android.app.Activity;
import android.graphics.Point;
import android.location.Location;
import android.text.format.Time;
import android.util.Log;

import en.htwg.seapal.gui.overlay.IOverlay;
import en.htwg.seapal.model.models.GeoInformation;
import en.htwg.seapal.model.tables.TableGeoInformation;

public class GeoInformationController implements IController {
	
	private GeoInformation geoInformation = null;
	private GeoInformation last = null;
	private Activity activity = null;
	
	public GeoInformationController(Activity activity) {
		this.activity = activity;
	}
	
	public List<GeoInformation> getAllGeoInformation() {
		return null;
	}

	public  GeoInformation getLast() {
		TableGeoInformation tableGeoInformation = new TableGeoInformation(activity);
		
		GeoInformation out = tableGeoInformation.getLast();
		
		if(out == null) {
			Time now = new Time();
			now.setToNow();
			return new GeoInformation(new Date(now.toMillis(false)), 47661980, 9178970, 0.0f ,0.0f);
		}
		return out;
	}
	
	public  GeoPoint getLastGeoPoint(){
		GeoInformation g = getLast();
		return new GeoPoint(g.getLatitude(), g.getLongitude());
	}
	
	public  GeoInformation getBeforeLast() {
		GeoInformation last = getLast();
		GeoInformation out = last;
		TableGeoInformation tableGeoInformation = new TableGeoInformation(activity);
		
		do {
			out = tableGeoInformation.getBefore(out);
		} while(last.getLatitude() == out.getLatitude() && last.getLongitude() == out.getLongitude());
		
		return out;
	}
	
	public void add(GeoInformation in) {
		TableGeoInformation tableGeoInformation = new TableGeoInformation(activity);
		try {
			tableGeoInformation.insert(in);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void locationChanged(Location location) {
		
		last = getLast();
		geoInformation = new GeoInformation();
		
		Time now = new Time();
		now.setToNow();
		geoInformation.setTimestamp(new Date(now.toMillis(false)));
		
		if (last != null) {
			
			
			geoInformation.setLatitude((int) (location.getLatitude() * 1000000));
			geoInformation.setLongitude((int) (location.getLongitude() * 1000000));
			
			long timeDiff = (geoInformation.getTimestamp().getTime() - last.getTimestamp().getTime()) / 1000;
			
			if(timeDiff > 0.0) {
				geoInformation.setCog(calcCog(last, geoInformation));
				geoInformation.setSpeed(calcSpeed(last, geoInformation, timeDiff));
			}
			
		} else {
			geoInformation.setCog(0);
			geoInformation.setLatitude(0);
			geoInformation.setLongitude(0);
			geoInformation.setSpeed(0.0f);
			geoInformation.setTimestamp(new Date(now.toMillis(false)));
		}
		Log.d("geoinfo", "" + geoInformation.getLatitude() + " "
				+ geoInformation.getLongitude() + " " + geoInformation.getCog() + " " + geoInformation.getSpeed() + " " + geoInformation.getTimestamp().getTime());

		this.add(geoInformation);
	}
	
	public float calcCog (GeoInformation last, GeoInformation geoInformation) {
		
		return calcCog(last.getLatitude(), last.getLongitude(), geoInformation.getLatitude(), geoInformation.getLongitude());
	}
	
	public  float calcCog(int lastLat, int lastLon, int curLat, int curLon) {
		float hypertenuse = calcDistance(lastLat, lastLon, curLat, curLon);
		
		int latDiff = lastLat - curLat;
		int lonDiff = lastLon - curLon;
		
		if(latDiff > 0 && lonDiff < 0) {
			float gegenKathede = calcDistance(lastLat, lastLon, curLat, lastLon);
			double beta = Math.toDegrees( Math.asin(gegenKathede/hypertenuse));
			
			return (float) (beta + 270.0f);
		} else if(latDiff < 0 && lonDiff < 0) {
			float gegenKathede = calcDistance(lastLat, lastLon, lastLat, curLon);
			double beta = Math.toDegrees( Math.asin(gegenKathede/hypertenuse));
			
			return (float) (beta + 180.0f);
		} else if (latDiff < 0 && lonDiff > 0) {
			float gegenKathede = calcDistance(lastLat, lastLon, curLat, lastLon);
			double beta = Math.toDegrees( Math.asin(gegenKathede/hypertenuse));
			
			return (float) (beta + 90.0f);
		} else {
			float gegenKathede = calcDistance(lastLat, lastLon, lastLat, curLon);
			double beta = Math.toDegrees( Math.asin(gegenKathede/hypertenuse));

			return (float) beta;
		}
	}
	
	public  float calcCogOf(int Lat, int Lon) {
		GeoInformation cur = getLast();
		
		return calcCog(cur.getLatitude(), cur.getLongitude(), Lat, Lon);
	}
	
	public float calcSpeed(GeoInformation last, GeoInformation geoInformation, long timeDiff) {
		
		return calcSpeed(last.getLatitude(), last.getLongitude(), geoInformation.getLatitude(), geoInformation.getLongitude(), timeDiff);		
	}
	
	public float calcSpeed(int lastLat, int lastLong, int curLat, int curLon, long timeDiff) {
		float distance = calcDistance(lastLat, lastLong, curLat, curLon);
		return (distance / timeDiff);
	}
	
	public  float calcDistance(int firstLat, int firstLon, int secondLat, int secondLon) {
		float[] dist = new float[1];
		Location.distanceBetween(
				Double.parseDouble("" + firstLat * 0.000001),
				Double.parseDouble("" + firstLon * 0.000001),
				Double.parseDouble("" + secondLat
						* 0.000001),
				Double.parseDouble("" + secondLon
						* 0.000001), dist);
		return dist[0];
	}
	
	public  float distanceTo(int destLat, int destLon) {
		GeoInformation cur = getLast();
		return calcDistance(cur.getLatitude(), cur.getLongitude(), destLat, destLon);
	}
	
	public  double calcBearingTo(int curLat, int curLon, int destLat, int destLon) {
		double lat1 = Math.toRadians(curLat * 0.000001);
		double lat2 = Math.toRadians(destLat * 0.000001);
		double dLon = Math.toRadians((destLon * 0.000001) - (curLon * 0.000001));

		double y = Math.sin(dLon) * Math.cos(lat2);
		double x = Math.cos(lat1)*Math.sin(lat2) - Math.sin(lat1)*Math.cos(lat2)*Math.cos(dLon);
		double brng = Math.atan2(y, x);
		  
		return (Math.toDegrees(brng) + 360) % 360;
	}
	
	public  double bearingTo(int destLat, int destLon) {
		GeoInformation cur = getLast();
		
		return calcBearingTo(cur.getLatitude(), cur.getLongitude(), destLat, destLon);
	}
	
	public  Point getPointbyDistanceandBearing(double distance, double bearing) {
		double R = 6371;
		distance = (distance * 1.852)/R;
		GeoInformation cur = getLast();
		double lat = Math.toRadians(cur.getLatitude() * 0.000001);
		double lon = Math.toRadians(cur.getLongitude() * 0.000001);
		bearing = Math.toRadians(bearing);
		
		double lat2 = Math.asin( Math.sin(lat)*Math.cos(distance) + 
	              Math.cos(lat)*Math.sin(distance)*Math.cos(bearing) );
		double lon2 =  (lon + Math.atan2(Math.sin(bearing)*Math.sin(distance)*Math.cos(lat), 
	                     Math.cos(distance)-Math.sin(lat)*Math.sin(lat2)));
		lon2 = ((lon2+3*Math.PI) % (2*Math.PI) - Math.PI);
		
		return new Point((int) (Math.toDegrees(lat2) * 1000000), (int) (Math.toDegrees(lon2) * 1000000));
	}
	
	public  Point getPointByTwoPoints(int latitude1, int longitude1, double brng1, int latitude2, int longitude2, double brng2) {
		
		double lat1 = Math.toRadians(latitude1 * 0.000001);
		double lon1 = Math.toRadians(longitude1 * 0.000001);
		double lat2 = Math.toRadians(latitude2 * 0.000001);
		double lon2 = Math.toRadians(longitude2 * 0.000001);
		double brng13 = Math.toRadians(brng1);
		double brng23 = Math.toRadians(brng2);
		double dLat = lat2-lat1;
		double dLon = lon2-lon1;
		
		double dist12 = 2*Math.asin( Math.sqrt( Math.sin(dLat/2)*Math.sin(dLat/2) + 
			    Math.cos(lat1)*Math.cos(lat2)*Math.sin(dLon/2)*Math.sin(dLon/2) ) );
		if (dist12 == 0) return null;
		  
		double brngA = Math.acos( ( Math.sin(lat2) - Math.sin(lat1)*Math.cos(dist12) ) / ( Math.sin(dist12)*Math.cos(lat1) ) );
		
		double brngB = Math.acos( ( Math.sin(lat1) - Math.sin(lat2)*Math.cos(dist12) ) / ( Math.sin(dist12)*Math.cos(lat2) ) );
		
		double brng12;
		double brng21;
		if (Math.sin(lon2-lon1) > 0) {
			brng12 = brngA;
			brng21 = 2*Math.PI - brngB;
		} else {
			brng12 = 2*Math.PI - brngA;
			brng21 = brngB;
		}
				  
		double alpha1 = (brng13 - brng12 + Math.PI) % (2*Math.PI) - Math.PI;
		double alpha2 = (brng21 - brng23 + Math.PI) % (2*Math.PI) - Math.PI;
		
		if (Math.sin(alpha1)==0 && Math.sin(alpha2)==0) return null;
		if (Math.sin(alpha1)*Math.sin(alpha2) < 0) return null;
		
		double alpha3 = Math.acos( -Math.cos(alpha1)*Math.cos(alpha2) + Math.sin(alpha1)*Math.sin(alpha2)*Math.cos(dist12));
		double dist13 = Math.atan2( Math.sin(dist12)*Math.sin(alpha1)*Math.sin(alpha2), Math.cos(alpha2)+Math.cos(alpha1)*Math.cos(alpha3) );
        double lat3 = Math.asin( Math.sin(lat1)*Math.cos(dist13) + Math.cos(lat1)*Math.sin(dist13)*Math.cos(brng13) );
		double dLon13 = Math.atan2( Math.sin(brng13)*Math.sin(dist13)*Math.cos(lat1), Math.cos(dist13)-Math.sin(lat1)*Math.sin(lat3) );
		double lon3 = lon1+dLon13;
		lon3 = (lon3+3*Math.PI) % (2*Math.PI) - Math.PI;

		return new Point((int) (Math.toDegrees(lat3) * 1000000), (int) (Math.toDegrees(lon3) * 1000000));
	}
	
	public float distance(List<IOverlay> list) {
		float distance = 0;
		IOverlay last = list.get(0);
		for(IOverlay over: list.subList(1, list.size())){
			GeoPoint lastGeoPoint = last.getGeoPoint();
			GeoPoint curGeoPoint = over.getGeoPoint();
			distance += calcDistance(lastGeoPoint.getLatitudeE6(), lastGeoPoint.getLongitudeE6(), curGeoPoint.getLatitudeE6(), curGeoPoint.getLongitudeE6());
			
			last = over;
		}
		
		return distance;
	}
}

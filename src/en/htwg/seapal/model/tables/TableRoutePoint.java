package en.htwg.seapal.model.tables;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import en.htwg.seapal.Observer.IObserver;
import en.htwg.seapal.Observer.ISubject;
import en.htwg.seapal.model.ATable;
import en.htwg.seapal.model.models.RoutePoint;

public class TableRoutePoint extends ATable implements ISubject{

	protected List<IObserver> observers;
	
	public TableRoutePoint(Activity activity) {
		super(activity, new RoutePoint());
		observers = new ArrayList<IObserver>();
	}
	
	public TableRoutePoint(SQLiteDatabase db) {
		super(db, new RoutePoint());
		observers = new ArrayList<IObserver>();
	}

	public List<RoutePoint> selectRoute(int ID) {
		Cursor results = this.db.rawQuery("SELECT * FROM " + this.Table_Name + " WHERE routeID = " + ID + " ORDER BY _id ASC", null);
		
		if (results.getCount() > 0){
			List<RoutePoint> out = new ArrayList<RoutePoint>();
			
			while(results.moveToNext()){
				out.add(new RoutePoint(results));
			}
			this.db.close();
			return out;
		} else {
			List<RoutePoint> list = new ArrayList<RoutePoint>();
			list.add(new RoutePoint());
			this.db.close();
			return list;
		}
	}
	
	public Boolean addRoutePoint(RoutePoint rp) {
		ContentValues values = new ContentValues();
		values.put("routeID", rp.routeID);
		values.put("name", rp.name);
		values.put("lat", rp.lat);
		values.put("lon", rp.lon);
		
		if(-1 == db.insert(this.Table_Name, null, values)) return false;
		else notifyObservers();
		return true;
	}
	
	public RoutePoint get(int ID) {
		Cursor result = db.query(this.Table_Name, null, "_id = " + ID, null, null, null, null, "1");
		result.moveToNext();
		RoutePoint out;
		if(result.getCount() > 0) out = new RoutePoint(result);
		else out = new RoutePoint();
		return out;
	}
	
	public void alter(RoutePoint rp) {
		ContentValues values = new ContentValues();
		values.put("routeID", rp.routeID);
		values.put("name", rp.name);
		values.put("lat", rp.lat);
		values.put("lon", rp.lon);
		
		Log.d("Alter", "alterrp" + rp._id);
		
		if (db.update(this.Table_Name, values, "_id = " + rp._id, null) > 0) {
			notifyObservers();
			Log.d("alterRoutePoint", "Success");
		}
	}
	
	public Cursor getRoute(int ID) {
		return db.query(this.Table_Name, null, "routeID = " + ID, null, null, null, null, "");
	}
	
	public final void notifyObservers() 
	{
		if(!observers.isEmpty()) {
			for(IObserver observer:observers) {
				observer.update();
			}
		}
	}	
	public final void registerObserver(IObserver o) {
		observers.add(o);	
	}

	@Override
	public final void removeObserver(IObserver o) {
			observers.remove(o);
	}
}

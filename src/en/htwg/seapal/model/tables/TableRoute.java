package en.htwg.seapal.model.tables;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import en.htwg.seapal.model.ATable;
import en.htwg.seapal.model.models.Route;
import en.htwg.seapal.model.models.RoutePoint;

public class TableRoute extends ATable {

	private Activity activity = null;
	
	public TableRoute(Activity activity) {
		super(activity, new Route());
		this.activity =  activity;
	}

	public List<Route> selectAll() {
		Cursor results = db.query(this.Table_Name, null, null, null, null, null, "name ASC");
		if (results.getCount() > 0){
			List<Route> list = new ArrayList<Route>();
			
			while(results.moveToNext()){
				list.add(new Route(activity, results));
			}
			return list;
		} else {
			List<Route> list = new ArrayList<Route>();
			list.add(new Route());
			return list;
		}
	}
	
	public Route selectRoute(int ID) {
		String[] selArgs= {"" + ID};
		Cursor result = db.query(this.Table_Name, null, "ID = ?", selArgs, null, null, null, "1");
		result.moveToNext();
		Route out;
		if(result.getCount() > 0) out = new Route(activity, result);
		else out = new Route();
		return out;
	}
	
	public Boolean addRoute(Route route) {
		if(route != null) {
			db.beginTransaction();
			ContentValues values = new ContentValues();
			values.put("ID", route.ID);
			values.put("name", route.name);
			values.put("date", route.date.getTime());
			
			if(db.insert(this.Table_Name, null, values) == -1) {
				db.endTransaction();
				return false;
			}
			
			TableRoutePoint tableRoutePoint = new TableRoutePoint(db);
			for(RoutePoint rp: route.points) {
				if(!tableRoutePoint.addRoutePoint(rp)) {
					db.endTransaction();
					return false;
				}
			}
			db.setTransactionSuccessful();
			db.endTransaction();
			return true;
		}
		return false;
	}
	
	public int getNextID () {
		Cursor result = this.db.rawQuery("SELECT ID FROM " + this.Table_Name + " ORDER BY ID DESC LIMIT 1", null);
		result.moveToFirst();
		int id;
		try {
			id = result.getInt(result.getColumnIndex("ID"));
		} catch(android.database.CursorIndexOutOfBoundsException e) {
			id = 0;
		}
		return (id + 1);
	}
	
	public void alterRoute(Route route) {
		db.beginTransaction();
		ContentValues values = new ContentValues();
		values.put("name", route.name);
		values.put("date", route.date.getTime());
		if(1 == db.update(this.Table_Name, values, "ID = " + route.ID, null)) {
			db.setTransactionSuccessful();
		}
		db.endTransaction();
	}
}

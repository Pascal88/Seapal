package en.htwg.seapal.model.tables;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import en.htwg.seapal.model.ATable;
import en.htwg.seapal.model.models.Boat;
import en.htwg.seapal.model.models.Trip;

public class TableTrip extends ATable {

	private Context context = null;
	
	public TableTrip(Context activity) {
		super(activity, new Trip());
	}
	
	public TableTrip(SQLiteDatabase db) {
		super(db, new Trip());
	}
	
	public Trip selectTrip(int ID) {
		String[] selectionArgs = {""+ID};
		Cursor c = this.db.query(this.Table_Name, null, "ID = ?", selectionArgs, null, null, "_ID ASC", "1");
		return new Trip(c, context);
	}
	
	public List<Trip> selectAll() {
		Cursor c = db.query(this.Table_Name, null, null, null, null, null, "ID ASC");
		
		List<Trip> list = new ArrayList<Trip>();
		
		while(c.moveToNext()) {
			list.add(new Trip(c, context));
		}
		return list;
	}
	
	public List<Trip> selectAllConnectedWithBoat(Boat boat) {
		String[] selArgs = {"" + boat.ID};
		Cursor c = db.query(this.Table_Name, null, "boatID = ?", selArgs, null, null, "ID ASC");
		
		List<Trip> list = new ArrayList<Trip>();
		
		while(c.moveToNext()) {
			list.add(new Trip(c, context));
		}
		return list;
	}
	
	public List<Trip> selectBetween(long from, long to) {
		String[] selArgs = {""+from, ""+to};
		Cursor c = db.query(this.Table_Name, null, "start > ? AND end < ?", selArgs, null, null, "ID ASC");
		
		List<Trip> list = new ArrayList<Trip>();
		
		while(c.moveToNext()) {
			list.add(new Trip(c, context));
		}
		return list;
	}
	
	public List<Trip> selectBetweenConnectedWithBoa(Boat boat, long from, long to) {
		String[] selArgs = {"" + boat.ID, ""+from, ""+to};
		Cursor c = db.query(this.Table_Name, null, "boatID = ? AND start > ? AND end < ?", selArgs, null, null, "ID ASC");
		
		List<Trip> list = new ArrayList<Trip>();
		
		while(c.moveToNext()) {
			list.add(new Trip(c, context));
		}
		return list;
	}
	
	public long minDate() {
		Cursor c = db.query(this.Table_Name, null, null, null, null, null, "start ASC", "1");
		long out = 0L;
		if(c.moveToNext()) out = c.getLong(c.getColumnIndex("start"));
		return out;
	}
	
	public long maxDate() {
		Cursor c = db.query(this.Table_Name, null, null, null, null, null, "end DESC", "1");
		long out = 0L;
		if(c.moveToNext()) out = c.getLong(c.getColumnIndex("end"));
		return out;
	}
	
	public void addTrip(Trip trip) {
		ContentValues values = new ContentValues();
		values.put("title", trip.title);
		values.put("skipper", trip.skipper);
		values.put("from", trip.fromm);
		values.put("to", trip.too);
		
		db.insert(this.Table_Name, null, values);
	}
}

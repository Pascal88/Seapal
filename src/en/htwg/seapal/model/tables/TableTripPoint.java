package en.htwg.seapal.model.tables;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import en.htwg.seapal.model.ATable;
import en.htwg.seapal.model.models.TripPoint;

public class TableTripPoint extends ATable {

	public TableTripPoint(Context activity) {
		super(activity, new TripPoint());
	}
	
	public TableTripPoint(SQLiteDatabase db) {
		super(db, new TripPoint());
	}
	
	public List<TripPoint> selectTrip(int id) {
		String[] selectionArgs = {"" + id};
		Cursor c = this.db.query(this.Table_Name, null, "tripID = ?", selectionArgs, null, null, TripPoint.Primary + " ASC");
	
		List<TripPoint> list = new ArrayList<TripPoint>();
		while(c.moveToNext()) {
			list.add(new TripPoint(c));
		}
		return list;
	}
}

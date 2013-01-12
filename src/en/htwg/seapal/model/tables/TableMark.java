package en.htwg.seapal.model.tables;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.google.android.maps.GeoPoint;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.text.format.Time;
import android.util.Log;
import en.htwg.seapal.model.ATable;
import en.htwg.seapal.model.models.Mark;

public class TableMark extends ATable {

	public TableMark(Activity activity) {
		super(activity, new Mark());
	}
	
	public List<Mark> selectAll() {
		Cursor results = this.db.rawQuery("SELECT * FROM " + this.Table_Name, null);
		Log.d("tableMarkResults", ""+results.getCount());
		if (results.getCount() > 0){
			List<Mark> list = new ArrayList<Mark>();
			
			while(results.moveToNext()){
				list.add(new Mark(results));
			}
			
			this.db.close();
			return list;
		}
		this.db.close();
		List<Mark> list = new ArrayList<Mark>();
		list.add(new Mark());
		return list;
	}

	public Mark getLast() {
		Cursor results = this.db.rawQuery("SELECT * FROM " + this.Table_Name + " ORDER BY ID DESC LIMIT 1", null);
		if (results.getCount() > 0){
			if(results.getCount() > 0) {
			results.moveToFirst();
			Mark out = new Mark(results);
			this.db.close();
			return out;
			}
		}
		this.db.close();
		Time now = new Time();
		now.setToNow();
		return new Mark(0, "Mark0", 0, 0, "Mark0", new Date(now.toMillis(false)));
	}
	
	public int alterMark(GeoPoint p, String name, String label){
		ContentValues val = new ContentValues();
		val.put("name", name);
		val.put("label", label);
		
		String where = "lat = ? AND lon = ?";
		String[] args = {"" + p.getLatitudeE6(), "" + p.getLongitudeE6()};

		return this.db.update(this.Table_Name, val, where, args);
	}
}

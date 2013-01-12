package en.htwg.seapal.model.tables;

import java.sql.Date;

import android.app.Activity;
import android.database.Cursor;
import en.htwg.seapal.model.ATable;
import en.htwg.seapal.model.models.GeoInformation;

public class TableGeoInformation extends ATable {

	public TableGeoInformation(Activity activity) {
		super(activity, new GeoInformation());
	}

	public GeoInformation getLast() {
		Cursor results = this.db.rawQuery("SELECT * FROM " + this.Table_Name + " ORDER BY ID DESC LIMIT 1", null);
		if (results != null){
			if(results.getCount() > 0) {
			results.moveToFirst();
			GeoInformation out = new GeoInformation(results);
			this.db.close();
			return out;
			}
		}
		this.db.close();
		return null;
	}
	
	public GeoInformation getBefore(GeoInformation g) {
		Cursor results = this.db.rawQuery("SELECT * FROM " + this.Table_Name + " WHERE ID == '"+ (g.getID()-1) +"' ORDER BY ID DESC LIMIT 1", null);
		if (results != null){
			if(results.getCount() > 0) {
			results.moveToFirst();
			GeoInformation out = new GeoInformation(results);
			this.db.close();
			return out;
			}
		}
		this.db.close();
		return null;
	}
	
	public GeoInformation getCloseTo(Date d){
		Cursor results = this.db.rawQuery("SELECT * FROM " + this.Table_Name + " WHERE timestamp < '"+ d.getTime() +"' ORDER BY timestamp DESC LIMIT 1", null);
		if (results != null){
			if(results.getCount() > 0) {
			results.moveToFirst();
			GeoInformation out = new GeoInformation(results);
			this.db.close();
			return out;
			}
		}
		this.db.close();
		return null;
	}
}

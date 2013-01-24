package en.htwg.seapal.model.tables;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.Time;
import en.htwg.seapal.model.ATable;
import en.htwg.seapal.model.models.Log;

public class TableLog extends ATable {

	private Activity activity = null;
	
	public static class Tupel<A,B> {
		public A a;
		public B b;
		
		public Tupel(A a, B b) {
			this.a = a;
			this.b = b;
		}
	}
	
	public TableLog(Activity activity) {
		super(activity, new Log());
		this.activity = activity;
	}
	
	public TableLog(SQLiteDatabase db) {
		super(db,new Log());
	}
	
	public void addLog(Log log){
		Time now = new Time();
		now.setToNow();
		ContentValues values = new ContentValues();
		
		values.put("cog", log.cog);
		values.put("sog", log.sog);
		values.put("btm", log.btm);
		values.put("dtm", log.dtm);
		values.put("timestamp", now.toMillis(false));
		
		this.db.insert(this.Table_Name, null, values);
	}
	
	public List<Tupel<Date, Float>> getCog() {
		List<Tupel<Date, Float>> list = new ArrayList<Tupel<Date, Float>>();
		String[] columns = {"timestamp","cog"};
		Cursor c = db.query(this.Table_Name, columns, null, null, null, null, "timestamp ASC");
		while(c.moveToNext()) {
			Tupel<Date, Float> t = new Tupel<Date, Float>(new Date(c.getLong(c.getColumnIndex("timestamp"))), c.getFloat(c.getColumnIndex("cog")));
			list.add(t);
		}
		
		return normalize(list);
	}
	
	public List<Tupel<Date, Float>> getSog() {
		List<Tupel<Date, Float>> list = new ArrayList<Tupel<Date, Float>>();
		String[] columns = {"timestamp","sog"};
		Cursor c = db.query(this.Table_Name, columns, null, null, null, null, "timestamp ASC");
		while(c.moveToNext()) {
			Tupel<Date, Float> t = new Tupel<Date, Float>(new Date(c.getLong(c.getColumnIndex("timestamp"))), c.getFloat(c.getColumnIndex("sog")));
			list.add(t);
		}
		
		return normalize(list);
	}
	
	public List<Tupel<Date, Float>> getBtm() {
		List<Tupel<Date, Float>> list = new ArrayList<Tupel<Date, Float>>();
		String[] columns = {"timestamp","btm"};
		Cursor c = db.query(this.Table_Name, columns, null, null, null, null, "timestamp ASC");
		while(c.moveToNext()) {
			Tupel<Date, Float> t = new Tupel<Date, Float>(new Date(c.getLong(c.getColumnIndex("timestamp"))), c.getFloat(c.getColumnIndex("btm")));
			list.add(t);
		}
		
		return normalize(list);
	}
	
	public List<Tupel<Date, Float>> getDtm() {
		List<Tupel<Date, Float>> list = new ArrayList<Tupel<Date, Float>>();
		String[] columns = {"timestamp","dtm"};
		Cursor c = db.query(this.Table_Name, columns, null, null, null, null, "timestamp ASC");
		while(c.moveToNext()) {
			Tupel<Date, Float> t = new Tupel<Date, Float>(new Date(c.getLong(c.getColumnIndex("timestamp"))), c.getFloat(c.getColumnIndex("dtm")));
			list.add(t);
		}
		
		return normalize(list);
	}
	
	private List<Tupel<Date, Float>> normalize (List<Tupel<Date, Float>> list) {
		List<Tupel<Date, Float>> out = new ArrayList<Tupel<Date, Float>>();
		Iterator<Tupel<Date, Float>> it = list.iterator();
		
		Date timeStep = list.get(0).a;
		long step = 30 * 1000;
		timeStep.setTime(timeStep.getTime() - (timeStep.getTime() % step));
		
		int count = 0;
		float fl = 0;
		while(it.hasNext()) {
			Tupel<Date, Float> tmp = it.next();
			if(tmp.a.getTime() < (timeStep.getTime() + step)) {
				fl += tmp.b;
				count++;
			} else if(tmp.a.getTime() > (timeStep.getTime() + step)) {
				out.add(new Tupel<Date, Float>(timeStep, (fl/count)));
				timeStep.setTime(timeStep.getTime() + step);
				fl = 0;
				count = 0;
			}
		}

		return out;
	}
	
}

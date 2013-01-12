package en.htwg.seapal.model.tables;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
	
	public List<Tupel<Date, Float>> getCog() {
		List<Tupel<Date, Float>> list = new ArrayList<Tupel<Date, Float>>();
		String[] columns = {"timestamp","cog"};
		Cursor c = db.query(this.Table_Name, columns, null, null, null, null, "timestamp ASC");
		while(c.moveToNext()) {
			Tupel<Date, Float> t = new Tupel<Date, Float>(new Date(c.getLong(c.getColumnIndex("timestamp"))), c.getFloat(c.getColumnIndex("cog")));
			list.add(t);
		}
		
		List<Tupel<Date, Float>> out = new ArrayList<Tupel<Date, Float>>();
		Iterator<Tupel<Date, Float>> it = list.iterator();
		Date timeStep = list.get(0).a;
		long step = 30 * 1000;
		timeStep.setTime(timeStep.getTime() - (timeStep.getTime() % step));
		
		int count = 0;
		float cog = 0;
		while(it.hasNext()) {
			Tupel<Date, Float> tmp = it.next();
			if(tmp.a.getTime() < (timeStep.getTime() + step)) {
				cog += tmp.b;
				count++;
			} else if(tmp.a.getTime() > (timeStep.getTime() + step)) {
				out.add(new Tupel<Date, Float>(timeStep, (cog/count)));
				timeStep.setTime(timeStep.getTime() + step);
				cog = 0;
				count = 0;
			}
		}

		return out;
	}

}

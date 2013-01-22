package en.htwg.seapal.model.tables;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import en.htwg.seapal.model.ATable;
import en.htwg.seapal.model.models.Boat;

public class TableBoat extends ATable {

	public TableBoat(Context activity) {
		super(activity, new Boat());
	}
	
	@SuppressWarnings("unused")
	public List<Boat> selectAll() throws IllegalArgumentException, NoSuchFieldException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException{
		Cursor results = this.db.rawQuery("SELECT * FROM " + this.Table_Name, null);
		Log.d("tableBoatResults", ""+results.getCount());
		if (results != null){
			Boat[] out = new Boat[results.getCount()];
			
			//results.moveToFirst();
			
			for(int i = 0; results.moveToNext(); i++){
				out[i] = new Boat(results);
			}
			
			List<Boat> list = new ArrayList<Boat>();
			
			for(Boat result:out) {
				list.add(result);
			}
			this.db.close();
			return list;
		}
		this.db.close();
		return null;
	}
}

package en.htwg.seapal.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import android.app.Activity;
import android.content.Context;

import en.htwg.seapal.model.models.Boat;
import en.htwg.seapal.model.tables.TableBoat;

public class LogbookController implements IController {

	private Context activity = null;
	
	public LogbookController(Context activity) {
		this.activity = activity;
	}
	
	public List<Boat> getBoats() {
		TableBoat tableBoat = new TableBoat(activity);
		List<Boat> list = null;
		try {
			list = tableBoat.selectAll();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	public void addBoat(Boat boat) {
		TableBoat tableBoat = new TableBoat(activity);
		try {
			tableBoat.insert(boat);
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
}

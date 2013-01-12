package en.htwg.seapal.controller;

import java.sql.Date;
import java.util.List;

import com.google.android.maps.GeoPoint;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Point;
import android.text.format.Time;

import en.htwg.seapal.R;
import en.htwg.seapal.gui.adapter.MarkSpinnerAdapter;
import en.htwg.seapal.model.models.Mark;
import en.htwg.seapal.model.tables.TableMark;

public class MarkController implements IController{
	
	private Activity activity = null;
	
	public MarkController(Activity activity) {
		this.activity = activity;
	}
	

	public Mark addMark(String name, Point Point, String label) {
		TableMark table = new TableMark(activity);
		try {
			Time now = new Time();
			now.setToNow();
			Mark mark = new Mark(name, Point.x , Point.y, label, new Date(now.toMillis(false)));
			table.insert(mark);
			return mark;
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
		return null;
	}
	
	public void addMark(Mark mark) {
		TableMark table = new TableMark(activity);
		try {
			table.insert(mark);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public List<Mark> getAllMarks() {
		TableMark table = new TableMark(activity);
		List<Mark> list = table.selectAll();
		return list;
	}
	
	public Mark addMark(Resources res, GeoPoint p) {
		TableMark table = new TableMark(activity);
		Mark mark = table.getLast();
		 return addMark(res.getString(R.string.mark) + (mark.getID() + 1), new Point(p.getLatitudeE6(), p.getLongitudeE6()) , res.getString(R.string.mark) + (mark.getID() + 1));
	}
	
	public boolean alterMark(GeoPoint p, String name, String label) {
		TableMark table = new TableMark(activity);
		int result = table.alterMark(p, name, label);
		return (result == 1) ? true : false;
	}
	
	public Boolean alterMark(Mark mark) {
		return alterMark(new GeoPoint(mark.lat, mark.lon), mark.name, "");
	}
	
	public MarkSpinnerAdapter getMarkSpinnerAdapter(){
		TableMark tableMark = new TableMark(activity);
		return new MarkSpinnerAdapter(activity, tableMark.selectAll());
	}
}

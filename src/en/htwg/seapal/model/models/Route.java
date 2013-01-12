package en.htwg.seapal.model.models;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import en.htwg.seapal.model.tables.TableRoute;
import en.htwg.seapal.model.tables.TableRoutePoint;

import android.app.Activity;
import android.database.Cursor;

public class Route implements IModel {

	public String Primary = "ID";
	public int ID;
	public String name;
	public Date date;
	public List<RoutePoint> points;
	
	public Route() {
		super();
		this.ID = 0;
		this.name = "";
		this.date = new Date(0L);
		
		this.points = new ArrayList<RoutePoint>();
		points.add(new RoutePoint());
	}
	
	public Route(Activity activity, Cursor cursor) {
		super();
		this.ID = cursor.getInt(cursor.getColumnIndex("ID"));
		this.name = cursor.getString(cursor.getColumnIndex("name"));
		this.date = new Date(cursor.getLong(cursor.getColumnIndex("date")));
		
		TableRoutePoint tableRoutePoints = new TableRoutePoint(activity);
		this.points = tableRoutePoints.selectRoute(this.ID);
	}
	
	public Route(Activity activity, int iD, String name, Date date) {
		super();
		ID = iD;
		this.name = name;
		this.date = date;
		
		TableRoutePoint tableRoutePoints = new TableRoutePoint(activity);
		
		this.points = tableRoutePoints.selectRoute(this.ID);
	}
	
	public Route(Activity activity, String name, Date date) {
		super();
		this.ID = new TableRoute(activity).getNextID();
		this.name = name + ID;
		this.date = date;
	}
	
	public Route(int iD, String name, Date date, List<RoutePoint> points) {
		super();
		ID = iD;
		this.name = name;
		this.date = date;
		this.points = points;
	}
	public String getPrimary() {
		return Primary;
	}
	public void setPrimary(String primary) {
		Primary = primary;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public List<RoutePoint> getPoints() {
		return points;
	}
	public void setPoints(List<RoutePoint> points) {
		this.points = points;
	}
	
	
}

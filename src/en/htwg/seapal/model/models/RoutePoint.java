package en.htwg.seapal.model.models;

import android.database.Cursor;

public class RoutePoint implements IModel {

	public String Primary = "_id";
	public int _id;
	public int routeID;
	public String name;
	public int lat;
	public int lon;
	
	public RoutePoint() { 
		super();
		this._id = 0;
		this.routeID = 0;
		this.name = "";
		this.lat = 0;
		this.lon = 0;
	}
	
	public RoutePoint(Cursor cursor) {
		super();
		this._id = cursor.getInt(cursor.getColumnIndex("_id"));
		this.routeID = cursor.getInt(cursor.getColumnIndex("routeID"));
		this.name = cursor.getString(cursor.getColumnIndex("name"));
		this.lat = cursor.getInt(cursor.getColumnIndex("lat"));
		this.lon = cursor.getInt(cursor.getColumnIndex("lon"));
	}
	
	public RoutePoint(int _id, String name,  int routeID, int lat, int lon) {
		super();
		this._id = _id;
		this.routeID = routeID;
		this.name = name;
		this.lat = lat;
		this.lon = lon;
	}
	
	public RoutePoint(int routeID,String name, int lat, int lon) {
		super();
		this.routeID = routeID;
		this.name = name;
		this.lat = lat;
		this.lon = lon;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrimary() {
		return Primary;
	}
	public void setPrimary(String primary) {
		Primary = primary;
	}
	public int getID() {
		return _id;
	}
	public void setID(int _id) {
		this._id = _id;
	}
	public int getRouteID() {
		return routeID;
	}
	public void setRouteID(int routeID) {
		this.routeID = routeID;
	}
	public int getLat() {
		return lat;
	}
	public void setLat(int lat) {
		this.lat = lat;
	}
	public int getLon() {
		return lon;
	}
	public void setLon(int lon) {
		this.lon = lon;
	}
	
	
}

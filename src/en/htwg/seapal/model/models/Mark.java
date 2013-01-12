package en.htwg.seapal.model.models;

import java.sql.Date;

import android.database.Cursor;

public class Mark implements IModel {
	
	public String Primary = "ID";
	public int ID;
	public String name;
	public int lat;
	public int lon;
	public String label;
	public Date created;
	
	public Mark() { 
		super();
		ID = 0;
		this.name = "";
		this.lat = 0;
		this.lon = 0;
		this.label = "";
		this.created = new Date(0L);
	}
	
	public Mark(Cursor cursor) {
		super();
		ID = Integer.parseInt(cursor.getString(cursor.getColumnIndex("ID")));
		this.name = cursor.getString(cursor.getColumnIndex("name"));
		this.lat = cursor.getInt(cursor.getColumnIndex("lat"));
		this.lon = cursor.getInt(cursor.getColumnIndex("lon"));
		this.label = cursor.getString(cursor.getColumnIndex("label"));
		this.created = new Date(cursor.getLong(cursor.getColumnIndex("created")));
	}
	
	public Mark(String name, int lat, int lon, String label, Date created) {
		super();
		this.name = name;
		this.lat = lat;
		this.lon = lon;
		this.label = label;
		this.created = created;
	}
	
	public Mark(int iD, String name, int lat, int lon, String label, Date created) {
		super();
		ID = iD;
		this.name = name;
		this.lat = lat;
		this.lon = lon;
		this.label = label;
		this.created = created;
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
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
}

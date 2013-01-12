package en.htwg.seapal.model.models;

import java.sql.Date;

import android.database.Cursor;

public class GeoInformation implements IModel {

	public String Primary = "ID";
	public int ID;
	public Date timestamp;
	public int latitude;
	public int longitude;
	public float speed;
	public float cog;
	
	public GeoInformation() { }
	
	public GeoInformation(Cursor cursor) { 
		this.ID = cursor.getInt(cursor.getColumnIndex("ID"));
		this.timestamp = new Date(cursor.getLong(cursor.getColumnIndex("timestamp")));
		this.latitude = cursor.getInt(cursor.getColumnIndex("latitude"));
		this.longitude = cursor.getInt(cursor.getColumnIndex("longitude"));
		this.speed = cursor.getFloat(cursor.getColumnIndex("speed"));
		this.cog = cursor.getFloat(cursor.getColumnIndex("cog"));
	}
	
	public GeoInformation(Date timestamp, int latitude, int longitude,
			float speed, float cog) {
		super();
		this.timestamp = timestamp;
		this.latitude = latitude;
		this.longitude = longitude;
		this.speed = speed;
		this.cog = cog;
	}
	public GeoInformation(int iD, Date timestamp, int latitude, int longitude,
			float speed, float cog) {
		super();
		ID = iD;
		this.timestamp = timestamp;
		this.latitude = latitude;
		this.longitude = longitude;
		this.speed = speed;
		this.cog = cog;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public int getLatitude() {
		return latitude;
	}
	public void setLatitude(int latitude) {
		this.latitude = latitude;
	}
	public int getLongitude() {
		return longitude;
	}
	public void setLongitude(int longitude) {
		this.longitude = longitude;
	}
	public float getSpeed() {
		return speed;
	}
	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getCog() {
		return cog;
	}

	public void setCog(float cog) {
		this.cog = cog;
	}
	
}

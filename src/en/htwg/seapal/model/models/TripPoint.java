package en.htwg.seapal.model.models;

import java.sql.Date;

import android.database.Cursor;

public class TripPoint {
	public static String Primary = "_id";
	public int _id;
	public int tripID;
	public int lat;
	public int lon;
	public Date timestamp;
	public long cog;
	public long sog;
	public String maneuver;
	public String headSail;
	public String mainSail;
	public String notes;
	
	public TripPoint() {
		super();
		this._id = 0;
		this.lat = 0;
		this.lon = 0;
		this.timestamp = new Date(0L);
		this.cog = 0;
		this.sog = 0;
		this.maneuver = "";
		this.headSail = "";
		this.mainSail = "";
		this.notes = "";
		this.tripID = 0;
	}
	
	public TripPoint(Cursor cursor) {
		super();
		this._id = cursor.getInt(cursor.getColumnIndex("_id"));
		this.tripID = cursor.getInt(cursor.getColumnIndex("tripID"));
		this.lat = cursor.getInt(cursor.getColumnIndex("lat"));
		this.lon = cursor.getInt(cursor.getColumnIndex("lon"));
		this.timestamp = new Date(cursor.getLong(cursor.getColumnIndex("timestamp")));
		this.cog = cursor.getLong(cursor.getColumnIndex("cog"));
		this.sog = cursor.getLong(cursor.getColumnIndex("sog"));
		this.maneuver = cursor.getString(cursor.getColumnIndex("maneuver"));
		this.headSail = cursor.getString(cursor.getColumnIndex("headSail"));
		this.mainSail = cursor.getString(cursor.getColumnIndex("mainSail"));
		this.notes = cursor.getString(cursor.getColumnIndex("notes"));
	}
	
	public int getID() {
		return _id;
	}
	public void setID(int iD) {
		_id = iD;
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
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public long getCog() {
		return cog;
	}
	public void setCog(long cog) {
		this.cog = cog;
	}
	public long getSog() {
		return sog;
	}
	public void setSog(long sog) {
		this.sog = sog;
	}
	public String getManeuver() {
		return maneuver;
	}
	public void setManeuver(String maneuver) {
		this.maneuver = maneuver;
	}
	public String getHeadSail() {
		return headSail;
	}
	public void setHeadSail(String headSail) {
		this.headSail = headSail;
	}
	public String getMainSail() {
		return mainSail;
	}
	public void setMainSail(String mainSail) {
		this.mainSail = mainSail;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}

	public int getTripID() {
		return tripID;
	}

	public void setTripID(int tripID) {
		this.tripID = tripID;
	}
}

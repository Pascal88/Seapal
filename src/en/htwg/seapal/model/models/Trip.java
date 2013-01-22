package en.htwg.seapal.model.models;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import en.htwg.seapal.model.tables.TableTripPoint;

import android.content.Context;
import android.database.Cursor;

public class Trip implements Serializable{
	private static final long serialVersionUID = 6280753321364235252L;
	public String Primary = "ID";
	public int ID;
	public int boatID;
	public String title;
	public String fromm;
	public String too;
	public long duration;
	public String skipper;
	public String crew;
	public String notes;
	public Date start;
	public Date end;
	public int engine;
	public Boolean tanked;
	public List<TripPoint> points = null;
	
	public Trip() {
		ID = 0;
		boatID = 0;
		title = "";
		fromm = "";
		too = "";
		duration = 0;
		skipper = "";
		crew = "";
		notes = "";
		start = new Date(0L);
		end = new Date(0L);
		engine = 0;
		tanked = false;
		points = new ArrayList<TripPoint>();
	}

	public Trip(Cursor cursor, Context context) {
		super();
		ID = cursor.getInt(cursor.getColumnIndex("ID"));
		this.boatID = cursor.getInt(cursor.getColumnIndex("boatID"));
		this.title = cursor.getString(cursor.getColumnIndex("title"));
		this.fromm = cursor.getString(cursor.getColumnIndex("fromm"));;
		this.too = cursor.getString(cursor.getColumnIndex("too"));;
		this.duration = cursor.getLong(cursor.getColumnIndex("duration"));
		this.skipper = cursor.getString(cursor.getColumnIndex("skipper"));;
		this.crew = cursor.getString(cursor.getColumnIndex("crew"));;
		this.notes = cursor.getString(cursor.getColumnIndex("notes"));;
		this.start = new Date(cursor.getLong(cursor.getColumnIndex("start")));
		this.end = new Date(cursor.getLong(cursor.getColumnIndex("end")));
		this.engine = cursor.getInt(cursor.getColumnIndex("engine"));
		this.tanked = (cursor.getInt(cursor.getColumnIndex("tanked")) == 1);
		this.points = new TableTripPoint(context).selectTrip(ID);
	}
	
	public int getEngine() {
		return engine;
	}

	public void setEngine(int engine) {
		this.engine = engine;
	}

	public Boolean getTanked() {
		return tanked;
	}

	public void setTanked(Boolean tanked) {
		this.tanked = tanked;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getBoatID() {
		return boatID;
	}

	public void setBoatID(int boatID) {
		this.boatID = boatID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFrom() {
		return fromm;
	}

	public void setFrom(String fromm) {
		this.fromm = fromm;
	}

	public String getTo() {
		return too;
	}

	public void setTo(String to) {
		this.too = to;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public String getSkipper() {
		return skipper;
	}

	public void setSkipper(String skipper) {
		this.skipper = skipper;
	}

	public String getCrew() {
		return crew;
	}

	public void setCrew(String crew) {
		this.crew = crew;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public List<TripPoint> getPoints() {
		return points;
	}

	public void setPoints(List<TripPoint> points) {
		this.points = points;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}
}

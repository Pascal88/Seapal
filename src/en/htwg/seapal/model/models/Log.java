package en.htwg.seapal.model.models;

import java.sql.Date;

import android.database.Cursor;

public class Log {
	public String Primary = "ID";
	public int ID;
	public float cog;
	public float sog;
	public float btm;
	public float dtm;
	public Date timestamp;
	
	public Log() {
		ID = 0;
		cog = 0;
		btm = 0;
		dtm = 0;
		timestamp = new Date(0L);
	}
	
	public Log(Cursor cursor) {
		ID = cursor.getInt(cursor.getColumnIndex("ID"));
		cog = cursor.getFloat(cursor.getColumnIndex("cog"));
		sog = cursor.getFloat(cursor.getColumnIndex("sog"));
		btm = cursor.getFloat(cursor.getColumnIndex("btm"));
		dtm = cursor.getFloat(cursor.getColumnIndex("dtm"));
		timestamp = new Date(cursor.getLong(cursor.getColumnIndex("timestamp")));
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public float getCog() {
		return cog;
	}

	public void setCog(float cog) {
		this.cog = cog;
	}

	public float getSog() {
		return sog;
	}

	public void setSog(float sog) {
		this.sog = sog;
	}

	public float getBtm() {
		return btm;
	}

	public void setBtm(float btm) {
		this.btm = btm;
	}

	public float getDtm() {
		return dtm;
	}

	public void setDtm(float dtm) {
		this.dtm = dtm;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
}

package en.htwg.seapal.controller;

import java.sql.Date;
import java.util.List;

import en.htwg.seapal.model.models.Log;
import en.htwg.seapal.model.tables.TableLog;
import en.htwg.seapal.model.tables.TableLog.Tupel;
import android.app.Activity;

public class LogController {
	
	private Activity activity = null;
	
	public LogController(Activity activity) {
		this.activity = activity;
	}
	
	public List<Tupel<Date, Float>> getCog() {
		TableLog table = new TableLog(activity);
		return table.getCog();
	}
	
	public List<Tupel<Date, Float>> getSog() {
		TableLog table = new TableLog(activity);
		return table.getSog();
	}
	
	public List<Tupel<Date, Float>> getBtm() {
		TableLog table = new TableLog(activity);
		return table.getBtm();
	}
	
	public List<Tupel<Date, Float>> getDtm() {
		TableLog table = new TableLog(activity);
		return table.getDtm();
	}
	
	public void add(float cog, float sog, float dtm, float btm) {
		Log log = new Log();
		log.sog = sog;
		log.cog = cog;
		log.btm = btm;
		log.dtm = dtm;
	}
}

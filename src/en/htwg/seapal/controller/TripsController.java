package en.htwg.seapal.controller;

import java.sql.Date;
import java.util.List;

import android.content.Context;

import en.htwg.seapal.model.models.Boat;
import en.htwg.seapal.model.models.Trip;
import en.htwg.seapal.model.tables.TableTrip;

public class TripsController {

	private Context context = null;
	
	public TripsController(Context context) {
		this.context = context;
	}
	
	public List<Trip> getTrips() {
		TableTrip table = new TableTrip(context);
		
		List<Trip> out = table.selectAll();
		return out;
	}
	
	public List<Trip> getTrips(Boat boat) {
		TableTrip table = new TableTrip(context);
		
		List<Trip> out = table.selectAllConnectedWithBoat(boat);
		return out;
	}
	
	public List<Trip> getTripsBetween(long first, long second) {
		TableTrip table = new TableTrip(context);
		List<Trip> out;
		if(first < second) {
			out = table.selectBetween(first, second);
		} else {
			out = table.selectBetween(second, first);
		}
		return out;
	}
	
	public List<Trip> getTripsBetween(Boat boat, long first, long second) {
		TableTrip table = new TableTrip(context);
		List<Trip> out;
		if(first < second) {
			out = table.selectBetweenConnectedWithBoa(boat, first, second);
		} else {
			out = table.selectBetweenConnectedWithBoa(boat, second, first);
		}
		return out;
	}
	
	public Date getMinDate() {
		TableTrip table = new TableTrip(context);
		Date out = new Date(table.minDate());
		return out;
	}
	
	public Date maxDate() {
		TableTrip table = new TableTrip(context);
		Date out = new Date(table.maxDate());
		return out;
	}
	
	public Trip newTrip(String title, String skipper, String from, String to) {
		TableTrip table = new TableTrip(context);
		Trip trip = new Trip();
		trip.setTitle(title);
		trip.setSkipper(skipper);
		trip.setFrom(from);
		trip.setTo(to);
		
		table.addTrip(trip);
		return trip;
	}
}

package en.htwg.seapal.gui.adapter;

import java.util.List;

import en.htwg.seapal.R;
import en.htwg.seapal.model.models.Trip;
import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class TripsAdapter extends ArrayAdapter<Trip> {

	private Context activity = null;
	
	public TripsAdapter(Context context, List<Trip> objects) {
		super(context, R.layout.trip, objects);
		this.activity = context;
	}

	static class ViewHolder {
	    protected TextView title;
	    protected TextView skipper;
	    protected TextView start;
	    protected TextView from;
	    protected TextView to;
	    protected TextView end;
	    protected TextView duration;
	    protected TextView engine;
	    protected CheckBox tanked;
	  }
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		
		if (convertView == null) {
			
			LayoutInflater inflator = LayoutInflater.from(activity);
			view = inflator.inflate(R.layout.trip, null);
			final ViewHolder viewHolder = new ViewHolder();
			viewHolder.title = (TextView) view.findViewById(R.id.title);
			viewHolder.skipper = (TextView) view.findViewById(R.id.skipper);
			viewHolder.start = (TextView) view.findViewById(R.id.start);
			viewHolder.from = (TextView) view.findViewById(R.id.from);
			viewHolder.end = (TextView) view.findViewById(R.id.end);
			viewHolder.to = (TextView) view.findViewById(R.id.to);
			viewHolder.duration = (TextView) view.findViewById(R.id.duration);
			viewHolder.engine = (TextView) view.findViewById(R.id.engine);
			viewHolder.tanked = (CheckBox) view.findViewById(R.id.tankedCheck);
			view.setTag(viewHolder);
		} else {
			view = convertView;
		}
		
		ViewHolder holder = (ViewHolder) view.getTag();
		holder.title.setText(this.getItem(position).getTitle());
		holder.skipper.setText(this.getItem(position).getSkipper());
		holder.start.setText(DateFormat.format("dd, MMMM, yyyy kk:mm", this.getItem(position).getStart()));
		holder.from.setText(this.getItem(position).getFrom());
		holder.end.setText(DateFormat.format("dd, MMMM, yyyy kk:mm", this.getItem(position).getEnd()));
		holder.to.setText(this.getItem(position).getTo());
		int hours = (int) (this.getItem(position).getDuration() / (1000 * 60 * 60));
		int min = (int) ((this.getItem(position).getDuration() % (1000 * 60 * 60)) / (1000 * 60));
		holder.duration.setText("" + hours + "h" + min + "m");
		holder.engine.setText("" + this.getItem(position).getEngine());
		if(this.getItem(position).getTanked()) holder.tanked.setChecked(true);
		else holder.tanked.setChecked(false);
		
		return view;
	}
	
	public void replace(List<Trip> objects) {
		for(int i = 0; i < this.getCount(); i++) {
			this.remove(this.getItem(i));
		}
		this.addAll(objects);
	}
	
}

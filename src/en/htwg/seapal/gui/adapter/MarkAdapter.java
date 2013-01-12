package en.htwg.seapal.gui.adapter;

import java.util.List;

import en.htwg.seapal.R;
import en.htwg.seapal.model.models.Mark;
import android.app.Activity;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MarkAdapter extends ArrayAdapter<Mark> {

	private final List<Mark> list;
	private final Activity context;
	
	public MarkAdapter(Activity context, List<Mark> objects) {
		super(context, R.layout.marklist, objects);
		this.list = objects;
		this.context = context;
	}

	static class ViewHolder {
	    protected TextView name;
	    protected TextView created;
	    protected TextView lat;
	    protected TextView lon;
	    protected TextView label;
	  }
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		
		if (convertView == null) {
			LayoutInflater inflator = context.getLayoutInflater();
			view = inflator.inflate(R.layout.marklist, null);
			final ViewHolder viewHolder = new ViewHolder();
			viewHolder.name = (TextView) view.findViewById(R.id.name);
			viewHolder.created = (TextView) view.findViewById(R.id.createdAt);
			viewHolder.lat = (TextView) view.findViewById(R.id.lati);
			viewHolder.lon = (TextView) view.findViewById(R.id.longi);
			viewHolder.label = (TextView) view.findViewById(R.id.label);
			view.setTag(viewHolder);
		} else {
			view = convertView;
		}
		
		ViewHolder holder = (ViewHolder) view.getTag();
		holder.name.setText(list.get(position).getName());
		holder.created.setText(list.get(position).getCreated().toString());
		holder.lat.setText(Location.convert(list.get(position).getLat() * 0.000001, Location.FORMAT_SECONDS));
		holder.lon.setText(Location.convert(list.get(position).getLon() * 0.000001, Location.FORMAT_SECONDS));
		holder.label.setText(list.get(position).getLabel());
		
		return view;
	}
	
	public void addMark(Mark mark) {
		list.add(mark);
		this.notifyDataSetChanged();
	}
}

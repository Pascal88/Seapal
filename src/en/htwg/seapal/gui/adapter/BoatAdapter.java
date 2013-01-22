package en.htwg.seapal.gui.adapter;

import java.util.List;

import en.htwg.seapal.R;
import en.htwg.seapal.gui.listener.logbook.OnGotoTripsClickListener;
import en.htwg.seapal.model.models.Boat;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

public class BoatAdapter extends ArrayAdapter<Boat> {

	private final Context activity;
	
	public BoatAdapter(Context activity, List<Boat> objects) {
		super(activity, R.layout.boatlist, objects);
		this.activity = activity;
	}
	
	static class ViewHolder {
	    protected TextView name;
	    protected TextView typ;
	    protected TextView draftsman;
	    protected TextView lenght;
	    protected TextView owner;
	    protected ImageButton show;
	  }

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		
		if (convertView == null) {
			
			LayoutInflater inflator = LayoutInflater.from(activity);
			view = inflator.inflate(R.layout.boatlist, null);
			final ViewHolder viewHolder = new ViewHolder();
			viewHolder.name = (TextView) view.findViewById(R.id.name);
			viewHolder.typ = (TextView) view.findViewById(R.id.type);
			viewHolder.draftsman = (TextView) view.findViewById(R.id.draftsman);
			viewHolder.lenght = (TextView) view.findViewById(R.id.length);
			viewHolder.owner = (TextView) view.findViewById(R.id.owner);
			viewHolder.show = (ImageButton) view.findViewById(R.id.show);
			view.setTag(viewHolder);
			viewHolder.show.setTag(this.getItem(position));
		} else {
			view = convertView;
			((ViewHolder) view.getTag()).show.setTag(this.getItem(position));
		}
		
		ViewHolder holder = (ViewHolder) view.getTag();
		holder.name.setText(this.getItem(position).getName());
		holder.typ.setText(this.getItem(position).getTyp());
		holder.draftsman.setText(this.getItem(position).getDraftsman());
		holder.lenght.setText(this.getItem(position).getLength());
		holder.owner.setText(this.getItem(position).getOwner());
		holder.show.setOnClickListener(new OnGotoTripsClickListener(activity, getItem(position)));
		
		return view;
	}
	
}

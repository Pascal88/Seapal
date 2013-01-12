package en.htwg.seapal.gui.adapter;

import java.util.List;

import en.htwg.seapal.model.models.Route;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

public class RouteSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

	private List<Route> content = null;
	private Activity activity = null;
	
	public RouteSpinnerAdapter(Activity activity, List<Route> content) {
		this.content = content;
		this.activity = activity;
	}

	
	@Override
	public int getCount() {
		return content.size();
	}

	@Override
	public Object getItem(int position) {
		return content.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup group) {
		TextView text = new TextView(activity);
		text.setText(content.get(position).name);
		text.setTextSize(20.0f);
		return text;
	}
	
	public void changeDataSet(List<Route> content) {
		this.content = content;
		this.notifyDataSetChanged();
	}

}

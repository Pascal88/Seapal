package en.htwg.seapal.gui.adapter;

import java.util.List;

import en.htwg.seapal.R;
import en.htwg.seapal.model.models.Mark;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

public class MarkSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

	private List<Mark> content = null;
	private Activity activity = null;
	
	public MarkSpinnerAdapter(Activity activity, List<Mark> content) {
		this.content = content;
		this.activity = activity;
	}
	
	@Override
	public int getCount() {
		return content.size() + 4;
	}

	@Override
	public Object getItem(int position) {
		if(position > content.size() && position == 0) {
			return content.get(0);
		}
		return content.get(position-1);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView text = new TextView(activity);
		text.setTextSize(20.0f);
		text.setId(1000);
		if(position == 0) {
			text.setText(activity.getResources().getString(R.string.addMarkDialog));
		} else if(position == content.size()+1) {
			text.setText("----------");
		} else if(position == content.size()+2) {
			text.setText(activity.getResources().getString(R.string.addNewMarkOnMap));
		} else if(position == content.size() + 3) {
			text.setText(activity.getResources().getString(R.string.addNewMarkByData));
		} else {
			text.setText(content.get(position-1).name);
		}
		return text;
	}

	public void changeDataSet(List<Mark> content) {
		this.content = content;
		this.notifyDataSetChanged();
	}
	
}

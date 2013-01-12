package en.htwg.seapal.gui.listener.mark;

import en.htwg.seapal.R;
import en.htwg.seapal.controller.MarkController;
import en.htwg.seapal.gui.adapter.MarkAdapter;
import en.htwg.seapal.gui.listener.ITabListener;
import en.htwg.seapal.model.models.Mark;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.graphics.Point;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class AddMarkClickListener implements OnClickListener {

	private Activity activity = null;
	private ActionBar actionBar = null;
	private MarkAdapter adapter = null;
	
	public AddMarkClickListener(Activity activity, MarkAdapter adapter) {
		this.activity = activity;
		this.adapter = adapter;
		actionBar = activity.getActionBar();
	}

	@Override
	public void onClick(View v) {
		Tab selected = actionBar.getSelectedTab();
		ITabListener<?> tab = (ITabListener<?>) selected.getTag();
		
		Point p = tab.getPoint();
		
		EditText name = (EditText) activity.findViewById(R.id.editMarkName);
		EditText label =  (EditText) activity.findViewById(R.id.editMarkLabel);
		
		Mark mark = new MarkController(activity).addMark(name.getText().toString(), p, label.getText().toString());
		adapter.add(mark);
	}
}

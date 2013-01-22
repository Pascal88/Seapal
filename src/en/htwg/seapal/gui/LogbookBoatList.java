package en.htwg.seapal.gui;

import en.htwg.seapal.controller.LogbookController;
import en.htwg.seapal.gui.adapter.BoatAdapter;
import en.htwg.seapal.model.models.Boat;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class LogbookBoatList extends ListView {

	public LogbookBoatList(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.activity = context;
		this.logbookController = new LogbookController(activity);
		prepare(attrs);
	}


	private LogbookController logbookController = null;
	private Context activity = null;
	
		
	public void prepare(AttributeSet attrs) {
		
			ArrayAdapter<Boat> adapter = new BoatAdapter(activity, logbookController.getBoats());
			this.setAdapter(adapter);
			
			int totalHeight = 0;
	        int desiredWidth = MeasureSpec.makeMeasureSpec(getWidth(), MeasureSpec.AT_MOST);
	        for (int i = 0; i < adapter.getCount(); i++) {
	            View listItem = adapter.getView(i, null, this);
	            listItem.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
	            totalHeight += listItem.getMeasuredHeight();
	        }

	        ViewGroup.LayoutParams params = new LayoutParams(activity, attrs);
	        params.height = totalHeight + (getDividerHeight() * (adapter.getCount() - 1));
	        setLayoutParams(params);
	        requestLayout();
	}

}

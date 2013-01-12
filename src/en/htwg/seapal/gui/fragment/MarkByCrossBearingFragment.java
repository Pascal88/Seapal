package en.htwg.seapal.gui.fragment;

import en.htwg.seapal.R;
import en.htwg.seapal.controller.GeoInformationController;
import en.htwg.seapal.gui.listener.mark.SetPositionClickListener;
import android.app.Activity;
import android.app.Fragment;
import android.graphics.Point;
import android.location.Location;
import android.os.Bundle;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class MarkByCrossBearingFragment extends Fragment implements ITabFragment{

	private Activity activity = null;
	private Button button1 = null;
	private Button button2 = null;
	private View view1 = null;
	private View view2 = null;
	private EditText lat1 = null; 
	private EditText lat2 = null;
	private EditText lon1 = null;
	private EditText lon2 = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.activity = getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.markbycrossbearing, container, false);
		
		view1 = view.findViewById(R.id.inv1);
		view2 = view.findViewById(R.id.inv2);
		lat1 = (EditText) view.findViewById(R.id.marksLatCoordinatesEdit1);
		lat2 = (EditText) view.findViewById(R.id.marksLatCoordinatesEdit2);
		lon1 = (EditText) view.findViewById(R.id.marksLonCoordinatesEdit1);
		lon2 = (EditText) view.findViewById(R.id.marksLonCoordinatesEdit2);
		
		button1 = (Button) view.findViewById(R.id.marksAtCross1Button);
		button1.setOnClickListener(new SetPositionClickListener(getActivity(), view1, lat1, lon1));
		button2 = (Button) view.findViewById(R.id.marksAtCross2Button);
		button2.setOnClickListener(new SetPositionClickListener(getActivity(), view2, lat2, lon2));
		
		return view;
	}

	@Override
	public Point getPoint() {
		EditText ed1 = (EditText) getActivity().findViewById(R.id.marksBearingCross1Edit);
		EditText ed2 = (EditText) getActivity().findViewById(R.id.marksBearingCross2Edit);
		
		Time now = new Time();
		now.setToNow();
		
		double brng1 = Double.parseDouble(ed1.getText().toString());
		double brng2 = Double.parseDouble(ed2.getText().toString());
		
		GeoInformationController geoInformationController = new GeoInformationController(activity);
		return geoInformationController.getPointByTwoPoints(
				(int) (Location.convert(lat1.getText().toString().replace(",", ".")) / 0.000001), 
				(int) (Location.convert(lon1.getText().toString().replace(",", ".")) / 0.000001), 
				brng1, 
				(int) (Location.convert(lat2.getText().toString().replace(",", ".")) / 0.000001), 
				(int) (Location.convert(lon2.getText().toString().replace(",", ".")) / 0.000001), 
				brng2);
	}

	@Override
	public void setPoint(Point point) {
		
	}
	
	public void setViewsInvisible() {
		view1.setVisibility(View.INVISIBLE);
		view2.setVisibility(View.INVISIBLE);
	}
}

package en.htwg.seapal.gui.fragment;

import en.htwg.seapal.R;
import en.htwg.seapal.gui.listener.mapView.GotoDashboardClickListener;
import en.htwg.seapal.gui.listener.mapView.GotoLogbookClickListener;
import en.htwg.seapal.gui.listener.mapView.GotoMarksClickListener;
import en.htwg.seapal.gui.listener.mapView.GotoRoutesClickListener;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class MenuFragment extends Fragment {

	private ImageButton logbook = null;
	private ImageButton dashboard = null;
	private ImageButton marks = null;
	private ImageButton routes = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tabbar, container, false);
		
		logbook = (ImageButton) view.findViewById(R.id.logbook);
		logbook.setClickable(true);
		logbook.setOnClickListener(new GotoLogbookClickListener(getActivity()));
		
		dashboard = (ImageButton) view.findViewById(R.id.dashboard);
		dashboard.setClickable(true);
		dashboard.setOnClickListener(new GotoDashboardClickListener(getActivity()));
		
		marks = (ImageButton) view.findViewById(R.id.mark);
		marks.setClickable(true);
		marks.setOnClickListener(new GotoMarksClickListener(getActivity()));
		
		routes = (ImageButton) view.findViewById(R.id.route);
		routes.setClickable(true);
		routes.setOnClickListener(new GotoRoutesClickListener(getActivity()));
		
		return view;
	}
}

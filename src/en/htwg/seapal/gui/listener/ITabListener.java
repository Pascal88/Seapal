package en.htwg.seapal.gui.listener;

import android.app.Fragment;
import android.graphics.Point;

public interface ITabListener<T extends Fragment> {

	public Point getPoint();

	public void setPoint(Point p);

}
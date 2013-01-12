package en.htwg.seapal.gui.activity;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import org.osmdroid.google.overlay.GoogleTilesOverlay;
import org.osmdroid.tileprovider.MapTileProviderBasic;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.tileprovider.tilesource.XYTileSource;

import android.app.ActionBar;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.View;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

import en.htwg.seapal.R;
import en.htwg.seapal.controller.GeoInformationController;
import en.htwg.seapal.gui.MyMenuView;
import en.htwg.seapal.gui.fragment.DropdownFragmentLeft;
import en.htwg.seapal.gui.fragment.DropdownFragmentRight;
import en.htwg.seapal.gui.listener.GeoInfoListener;
import en.htwg.seapal.gui.listener.MapTouchListener;
import en.htwg.seapal.gui.listener.mapView.IMapGestureListener;
import en.htwg.seapal.gui.listener.mapView.MapGestureTapListener;
import en.htwg.seapal.gui.listener.mapView.MapMenuItemClickListener;
import en.htwg.seapal.gui.listener.mapView.MarkMenuItemClickListener;
import en.htwg.seapal.gui.overlay.AimOverlay;
import en.htwg.seapal.gui.overlay.DistanceOverlayList;
import en.htwg.seapal.gui.overlay.MyGoogleLocationOverlay;
import en.htwg.seapal.gui.overlay.RouteOverlayList;
import en.htwg.seapal.model.models.GeoInformation;

public class MapViewActivity extends MapActivity implements IMapActivity{

	private MapView mapView = null;
	private MapController mapController = null;
	private MyGoogleLocationOverlay locationOverlay = null;
	private DropdownFragmentLeft dropdownFragmentLeft = null;
	private DropdownFragmentRight dropdownFragmentRight = null;
	private GestureDetector gestureDetector = null;
	private ActionBar actionBar = null;
	private GoogleTilesOverlay Seamarktiles = null;
	private GoogleTilesOverlay osmMapTiles = null;
	private MapTileProviderBasic osmTileProvider = null;
	private Overlay mapMenuOverlay = null;
	private MyMenuView mapMenu;
	private MyMenuView markMenu;
	private AimOverlay aim = null;
	private IMapGestureListener doubleTapDragListener = null;
	private DistanceOverlayList distOverlayList = null;
	private RouteOverlayList routeOverlayList = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setupOsmTiles(); // needs to be called before setContentView
		setContentView(R.layout.map);
		actionBar = getActionBar();
		initActionBar(); // initialize Actionbar

		// Creating and initializing Map
		mapView = (MapView) findViewById(R.id.myGMap);
		doubleTapDragListener = new MapGestureTapListener(this);
		gestureDetector = new GestureDetector(this,
				(OnGestureListener) doubleTapDragListener);
		mapView.setOnDragListener(doubleTapDragListener);
		setupMap(); // setup the google map
		setUpLocationListening(); // start the Location Listening

		initFragments();
		createMapMenu();
		createMarkMenu();

		this.distOverlayList = new DistanceOverlayList(this);
		this.routeOverlayList = new RouteOverlayList(this);

	}

	@Override
	protected void onDestroy() {
		// killFragments();
		super.onDestroy();
	}

	public void editPositionText(GeoInformation geoInformation) {
		TextView positionText = (TextView) findViewById(R.id.positionText);// find
																			// Position
																			// Text

		float knots = (float) (geoInformation.getSpeed() / 0.514444);// get cur
																		// Speed
																		// m/s
																		// make
																		// it to
																		// knots

		NumberFormat numberFormat = new DecimalFormat("000.00");// init Number
																// format
		numberFormat.setRoundingMode(RoundingMode.DOWN);

		positionText.setText("COG "
				+ numberFormat.format(geoInformation.getCog())
				+ "° SOG "
				+ numberFormat.format(knots)
				+ " kn Lat "
				+ Location.convert(geoInformation.getLatitude() * 0.000001,
						Location.FORMAT_SECONDS)
				+ " Lon "
				+ Location.convert(geoInformation.getLongitude() * 0.000001,
						Location.FORMAT_SECONDS));
	}

	private void initFragments() {
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();

		dropdownFragmentLeft = new DropdownFragmentLeft();
		fragmentTransaction.add(R.id.menuDropDown, dropdownFragmentLeft);
		fragmentTransaction.hide(dropdownFragmentLeft);

		dropdownFragmentRight = new DropdownFragmentRight();
		fragmentTransaction.add(R.id.toolsDropDown, dropdownFragmentRight);
		fragmentTransaction.hide(dropdownFragmentRight);

		fragmentTransaction.commit();
	}

	private void killFragments() {
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();

		fragmentTransaction.remove(dropdownFragmentLeft);
		fragmentTransaction.remove(dropdownFragmentRight);

		fragmentTransaction.commit();
	}

	public void showMenuPopup(View v) {

		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();

		if (!dropdownFragmentRight.isHidden()) {
			fragmentTransaction.hide(dropdownFragmentRight);
		}

		if (dropdownFragmentLeft.isHidden()) {
			fragmentTransaction.show(dropdownFragmentLeft);
		} else {
			fragmentTransaction.hide(dropdownFragmentLeft);
		}

		fragmentTransaction.commit();
	}

	public void showToolsPopup(View v) {

		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();

		if (!dropdownFragmentLeft.isHidden()) {
			fragmentTransaction.hide(dropdownFragmentLeft);
		}

		if (dropdownFragmentRight.isHidden()) {
			fragmentTransaction.show(dropdownFragmentRight);
		} else {
			fragmentTransaction.hide(dropdownFragmentRight);
		}

		fragmentTransaction.commit();
	}

	public void useGoogleMap(View v) {
		List<Overlay> list = ((MapView) mapView).getOverlays();
		list.remove(osmMapTiles);
	}

	public void useOsmMap(View v) {
		osmMapTiles = new GoogleTilesOverlay(osmTileProvider,
				getApplicationContext());

		List<Overlay> list = ((MapView) mapView).getOverlays();
		list.add(0, osmMapTiles);
	}

	public void setupOsmTiles() {
		osmTileProvider = new MapTileProviderBasic(this.getApplicationContext());
		osmTileProvider.setTileSource(TileSourceFactory.MAPNIK);
	}

	public void setupMap() {

		MapTileProviderBasic seamarkTileProvider = new MapTileProviderBasic(
				this.getApplicationContext());
		XYTileSource seamarkTileSource = new XYTileSource("Seamark", null, 3,17, 256, ".png", "http://tiles.openseamap.org/seamark/");
		seamarkTileProvider.setTileSource(seamarkTileSource);

		Seamarktiles = new GoogleTilesOverlay(seamarkTileProvider,
				getApplicationContext());

		List<Overlay> list = mapView.getOverlays();
		list.add(Seamarktiles);

		mapController = mapView.getController();
		mapView.setSatellite(true);
		mapController.setZoom(17);

		GeoInformationController geoInformationController = new GeoInformationController(
				this);
		GeoInformation geoinfo = geoInformationController.getLast();

		GeoPoint geo = new GeoPoint(geoinfo.getLatitude(),
				geoinfo.getLongitude());

		mapController.setCenter(geo);

		mapView.setOnTouchListener(new MapTouchListener(gestureDetector));

		editPositionText(geoinfo);
	}

	private void setUpLocationListening() {
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		GeoInfoListener geoInfo = new GeoInfoListener(this);
		if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
			locationManager.requestLocationUpdates(
					LocationManager.NETWORK_PROVIDER, 200, 10.0f, geoInfo);
		} else if (locationManager
				.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			locationManager.requestLocationUpdates(
					LocationManager.GPS_PROVIDER, 200, 20.0f, geoInfo);
		} else if (locationManager
				.isProviderEnabled(LocationManager.PASSIVE_PROVIDER)) {
			locationManager.requestLocationUpdates(
					LocationManager.PASSIVE_PROVIDER, 200, 15.0f, geoInfo);
		}
	}

	@Override
	protected boolean isRouteDisplayed() {
		// unused
		return false;
	}

	private void initActionBar() {
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle("Map");
		actionBar.setLogo(R.drawable.actionlogo);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setCustomView(R.layout.maphead);
	}

	public MapView getMapView() {
		return this.mapView;
	}

	public void showContextMenu(Overlay gp) {
		mapMenuOverlay = gp;
		mapMenu.toggleMenu();
	}

	public void showMarkMenu(Overlay gp) {
		mapMenuOverlay = gp;
		markMenu.toggleMenu();
	}

	public void createMapMenu() {
		mapMenu = (MyMenuView) findViewById(R.id.mapMyMenu);
		mapMenu.setMenuClickCallback(new MapMenuItemClickListener(this));
		mapMenu.setMenuItems(R.menu.crosshaircontextmenu);
	}

	public void createMarkMenu() {
		markMenu = (MyMenuView) findViewById(R.id.markMyMenu);
		markMenu.setMenuClickCallback(new MarkMenuItemClickListener(this));
		markMenu.setMenuItems(R.menu.markmenu);
	}

	public DistanceOverlayList getDistanceOverlayList() {
		return this.distOverlayList;
	}

	public RouteOverlayList getRouteOverlayList() {
		return this.routeOverlayList;
	}

	public AimOverlay getAim() {
		return aim;
	}

	public void setAim(AimOverlay aim) {
		this.aim = aim;
	}

	public MyGoogleLocationOverlay getLocationOverlay() {
		return this.locationOverlay;
	}

	public void setLocationOverlay(MyGoogleLocationOverlay locationOverlay) {
		this.locationOverlay = locationOverlay;
	}

	public Overlay getMapMenuOverlay() {
		return mapMenuOverlay;
	}

	public void setMapMenuOverlay(Overlay mapMenuOverlay) {
		this.mapMenuOverlay = mapMenuOverlay;
	}

	public IMapGestureListener getDoubleTapDragListener() {
		return doubleTapDragListener;
	}
}
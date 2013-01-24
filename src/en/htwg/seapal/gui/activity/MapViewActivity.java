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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.GestureDetector.OnGestureListener;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

import en.htwg.seapal.R;
import en.htwg.seapal.controller.GeoInformationController;
import en.htwg.seapal.gui.MyMenuView;
import en.htwg.seapal.gui.listener.GeoInfoListener;
import en.htwg.seapal.gui.listener.MapTouchListener;
import en.htwg.seapal.gui.listener.mapView.IMapGestureListener;
import en.htwg.seapal.gui.listener.mapView.MainMenuOnNavigationListener;
import en.htwg.seapal.gui.listener.mapView.MapGestureTapListener;
import en.htwg.seapal.gui.listener.mapView.MapMenuItemClickListener;
import en.htwg.seapal.gui.listener.mapView.MarkMenuItemClickListener;
import en.htwg.seapal.gui.listener.mark.AddMarkClickListener;
import en.htwg.seapal.gui.overlay.AimOverlay;
import en.htwg.seapal.gui.overlay.DistanceOverlayList;
import en.htwg.seapal.gui.overlay.MyGoogleLocationOverlay;
import en.htwg.seapal.gui.overlay.RouteOverlayList;
import en.htwg.seapal.model.models.GeoInformation;

public class MapViewActivity extends MapActivity implements IMapActivity, IMapViewActivity{

	private MapView mapView = null;
	private MapController mapController = null;
	private MyGoogleLocationOverlay locationOverlay = null;
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

		// Creating and initializing Map
		mapView = (MapView) findViewById(R.id.myGMap);
		doubleTapDragListener = new MapGestureTapListener(this);
		gestureDetector = new GestureDetector(this,
				(OnGestureListener) doubleTapDragListener);
		mapView.setOnDragListener(doubleTapDragListener);
		setupMap(); // setup the google map
		setUpLocationListening(); // start the Location Listening

		createMapMenu();
		createMarkMenu();

		this.distOverlayList = new DistanceOverlayList(this);
		this.routeOverlayList = new RouteOverlayList(this);

	}

	/* (non-Javadoc)
	 * @see en.htwg.seapal.gui.activity.IMapViewActivity#editPositionText(en.htwg.seapal.model.models.GeoInformation)
	 */
	@Override
	public void editPositionText(GeoInformation geoInformation) {

		/*float knots = (float) (geoInformation.getSpeed() / 0.514444);// get cur
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
						Location.FORMAT_SECONDS));*/
	}

	/* (non-Javadoc)
	 * @see en.htwg.seapal.gui.activity.IMapViewActivity#useGoogleMap()
	 */
	@Override
	public void useGoogleMap() {
		List<Overlay> list = ((MapView) mapView).getOverlays();
		list.remove(osmMapTiles);
	}

	/* (non-Javadoc)
	 * @see en.htwg.seapal.gui.activity.IMapViewActivity#useOsmMap()
	 */
	@Override
	public void useOsmMap() {
		osmMapTiles = new GoogleTilesOverlay(osmTileProvider,
				getApplicationContext());

		List<Overlay> list = ((MapView) mapView).getOverlays();
		list.add(0, osmMapTiles);
	}
	
	public void startAim() {
		MapMenuItemClickListener mmicl = new MapMenuItemClickListener(this);
		mmicl.MyMenuItemClick(R.id.crosshairMenuSetAim);
	}

	public void addMark() {
		MapMenuItemClickListener mmicl = new MapMenuItemClickListener(this);
		mmicl.MyMenuItemClick(R.id.crosshairMenuSetMark);
	}
	
	public void addRoute() {
		MapMenuItemClickListener mmicl = new MapMenuItemClickListener(this);
		mmicl.MyMenuItemClick(R.id.crosshairMenuSetRoute);
	}
	
	public void calcDistance() {
		MapMenuItemClickListener mmicl = new MapMenuItemClickListener(this);
		mmicl.MyMenuItemClick(R.id.crosshairMenuDistance);
	}
	
	public void centerMap () {
		GeoInformationController geoInformationController = new GeoInformationController(
				this);
		GeoInformation geoinfo = geoInformationController.getLast();

		GeoPoint geo = new GeoPoint(geoinfo.getLatitude(),
				geoinfo.getLongitude());

		mapController.setCenter(geo);
	}
	
	public void discardTarget() {
		
		if(this.aim != null) {
			List<Overlay> list = mapView.getOverlays();
			list.remove(aim);
		}
	}
	
	private void setupOsmTiles() {
		osmTileProvider = new MapTileProviderBasic(this.getApplicationContext());
		osmTileProvider.setTileSource(TileSourceFactory.MAPNIK);
	}

	private void setupMap() {

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
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    
	    actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle("Map");
		actionBar.setLogo(R.drawable.actionlogo);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		
		actionBar.setListNavigationCallbacks(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.mainMenu)), 
				new MainMenuOnNavigationListener(this));
		actionBar.setSelectedNavigationItem(0);
	    
		return false;
	}

	/* (non-Javadoc)
	 * @see en.htwg.seapal.gui.activity.IMapViewActivity#getMapView()
	 */
	@Override
	public MapView getMapView() {
		return this.mapView;
	}

	/* (non-Javadoc)
	 * @see en.htwg.seapal.gui.activity.IMapViewActivity#showContextMenu(com.google.android.maps.Overlay)
	 */
	@Override
	public void showContextMenu(Overlay gp) {
		mapMenuOverlay = gp;
		mapMenu.toggleMenu();
	}

	/* (non-Javadoc)
	 * @see en.htwg.seapal.gui.activity.IMapViewActivity#showMarkMenu(com.google.android.maps.Overlay)
	 */
	@Override
	public void showMarkMenu(Overlay gp) {
		mapMenuOverlay = gp;
		markMenu.toggleMenu();
	}

	private void createMapMenu() {
		mapMenu = (MyMenuView) findViewById(R.id.mapMyMenu);
		mapMenu.setMenuClickCallback(new MapMenuItemClickListener(this));
		mapMenu.setMenuItems(R.menu.crosshaircontextmenu);
	}

	private void createMarkMenu() {
		markMenu = (MyMenuView) findViewById(R.id.markMyMenu);
		markMenu.setMenuClickCallback(new MarkMenuItemClickListener(this));
		markMenu.setMenuItems(R.menu.markmenu);
	}

	/* (non-Javadoc)
	 * @see en.htwg.seapal.gui.activity.IMapViewActivity#getDistanceOverlayList()
	 */
	@Override
	public DistanceOverlayList getDistanceOverlayList() {
		return this.distOverlayList;
	}

	/* (non-Javadoc)
	 * @see en.htwg.seapal.gui.activity.IMapViewActivity#getRouteOverlayList()
	 */
	@Override
	public RouteOverlayList getRouteOverlayList() {
		return this.routeOverlayList;
	}

	/* (non-Javadoc)
	 * @see en.htwg.seapal.gui.activity.IMapViewActivity#getAim()
	 */
	@Override
	public AimOverlay getAim() {
		return aim;
	}

	/* (non-Javadoc)
	 * @see en.htwg.seapal.gui.activity.IMapViewActivity#setAim(en.htwg.seapal.gui.overlay.AimOverlay)
	 */
	@Override
	public void setAim(AimOverlay aim) {
		this.aim = aim;
	}

	/* (non-Javadoc)
	 * @see en.htwg.seapal.gui.activity.IMapViewActivity#getLocationOverlay()
	 */
	@Override
	public MyGoogleLocationOverlay getLocationOverlay() {
		return this.locationOverlay;
	}

	/* (non-Javadoc)
	 * @see en.htwg.seapal.gui.activity.IMapViewActivity#setLocationOverlay(en.htwg.seapal.gui.overlay.MyGoogleLocationOverlay)
	 */
	@Override
	public void setLocationOverlay(MyGoogleLocationOverlay locationOverlay) {
		this.locationOverlay = locationOverlay;
	}

	/* (non-Javadoc)
	 * @see en.htwg.seapal.gui.activity.IMapViewActivity#getMapMenuOverlay()
	 */
	@Override
	public Overlay getMapMenuOverlay() {
		return mapMenuOverlay;
	}

	/* (non-Javadoc)
	 * @see en.htwg.seapal.gui.activity.IMapViewActivity#setMapMenuOverlay(com.google.android.maps.Overlay)
	 */
	@Override
	public void setMapMenuOverlay(Overlay mapMenuOverlay) {
		this.mapMenuOverlay = mapMenuOverlay;
	}

	/* (non-Javadoc)
	 * @see en.htwg.seapal.gui.activity.IMapViewActivity#getDoubleTapDragListener()
	 */
	@Override
	public IMapGestureListener getDoubleTapDragListener() {
		return doubleTapDragListener;
	}
}
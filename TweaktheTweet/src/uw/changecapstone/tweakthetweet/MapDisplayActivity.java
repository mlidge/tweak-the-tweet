package uw.changecapstone.tweakthetweet;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.maps.GeoPoint;
@SuppressLint("NewApi")
public class MapDisplayActivity extends Activity {
	private GoogleMap mMap;
	GeoPoint p;
	TextView popUpInfo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map_display);
		mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();		
		mMap.addMarker(new MarkerOptions()
		        .position(new LatLng(47.6561359, -122.3042217))
		        .title("test marker . This is where I take my bus home"));
		mMap.setMyLocationEnabled(true);
		mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		LatLng latLng = new LatLng(getMidLat(46.6561359,48.6561359), getMidLng(-121.3042217,-123.3042217));
		CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 14);
		mMap.moveCamera(cameraUpdate);
		mMap.setOnMapClickListener(getOnMapClickListener());
	}
	/*Gets the lat/long location that was touched.*/
	private OnMapClickListener getOnMapClickListener() {
		return new OnMapClickListener() {			
		public void onMapClick(LatLng point) {
			Toast.makeText(getBaseContext(), "Location: "+point.latitude + "," + point.longitude, Toast.LENGTH_SHORT).show();
		}
		
	};
	}
	public float getMidLat(double d, double e){
		return (float) ((d+e)/2);
	}
	public float getMidLng(double d, double e){
		return (float) ((d+e)/2);
	}
}


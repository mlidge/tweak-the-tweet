package uw.changecapstone.tweakthetweet;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.maps.GeoPoint;
@SuppressLint("NewApi")
public class MapDisplayActivity extends Activity {
	private GoogleMap mMap;
	GeoPoint p;
	Context context = this;
	public final static String LAT = "uw.changecapstone.tweakthetweet.latitude";
	public final static String LONG = "uw.changecapstone.tweakthetweet.longitude";
	private static SharedPreferences pref;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map_display);
		mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		mMap.setMyLocationEnabled(true);
		LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE); 	
		LocationListener locationListener = new GPSListener();
		if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)){
			Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			if (location != null){
				//Get lat/long, show using toast, and animate to the location
				double lat = location.getLatitude();
				double lng = location.getLongitude();
				/*Editor e = pref.edit();
    			((Intent) e).putExtra(LAT, lat);
    			((Intent) e).putExtra(LONG, lng);
    			e.commit();*/
				/*Intent intent = new Intent();
			    intent.putExtra(LATITUDE, lat);
			    intent.putExtra(LONGITUDE, lng);
			    startActivity(intent);*/
				Toast.makeText(getBaseContext(), "Current Location: "+lat + "," + lng, Toast.LENGTH_SHORT).show();
				mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 14));
			}
			lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListener);
		}
		else {
			showSettingsAlert();
		}
	}
	private class GPSListener implements LocationListener {
		@Override
		public void onLocationChanged(Location location) {
			if (location != null){
				//Get Lat/long, show using toast, and animate to the location
				double lat = location.getLatitude();
				double lng = location.getLongitude();
				/*Editor e = pref.edit();
    			((Intent) e).putExtra(LAT, lat);
    			((Intent) e).putExtra(LONG, lng);
    			e.commit();*/
				/*Intent intent = new Intent();
			    intent.putExtra(LATITUDE, lat);
			    intent.putExtra(LONGITUDE, lng);
			    startActivity(intent);*/
				Toast.makeText(getBaseContext(), "Current Location: "+lat + "," + lng, Toast.LENGTH_SHORT).show();
				mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 14));
			}			
		}
		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub	
		}
		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub			
		}
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub			
		}
	}
	public void showSettingsAlert(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
      
        // Set Title
        alertDialogBuilder.setTitle("GPS Settings");
  
        // Set Dialog Message
        alertDialogBuilder.setMessage("GPS is not enabled. Do you want to go to settings menu?")
        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int id) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        })
        .setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            dialog.cancel();
            }
        });
  
        // Show it
        alertDialogBuilder.show();
    }
	public float getMidLat(double d, double e){
		return (float) ((d+e)/2);
	}
	public float getMidLng(double d, double e){
		return (float) ((d+e)/2);
	}
}


package com.example.user.blind;

import android.app.Dialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;


public class MainActivity extends ActionBarActivity {

    private static final int ERROR_DIALOG_REQUEST = 9001;
    GoogleMap mMap;

    private static final double
                SEATTLE_LAT = 47.435,
                SEATTLE_LNG = -122.33207;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (servicesOK()) {
            setContentView(R.layout.activity_map);

            if(initMap()){
                Toast.makeText(this, "ready to map", Toast.LENGTH_SHORT).show();
                gotolocation(SEATTLE_LAT,SEATTLE_LNG,15);

//              non programaticl way of myloaction buton
//            mMap.setMyLocationEnabled(true);

}
            else{
                Toast.makeText(this, "map not connected", Toast.LENGTH_SHORT).show();
            }
        } else {
            setContentView(R.layout.activity_main);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean servicesOK() {

        int isAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (GooglePlayServicesUtil.isUserRecoverableError(isAvailable)) {

            Dialog dialog =
                    GooglePlayServicesUtil.getErrorDialog(isAvailable, this, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {


            Toast.makeText(this, "cant connect", Toast.LENGTH_SHORT).show();
        }
        return false;

    }

private boolean initMap(){
    if (mMap == null){
        SupportMapFragment mapFragment =
                (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        mMap = mapFragment.getMap();
    }
    return (mMap!=null);
}

    private void gotolocation (double lat,double lng,float zoom){

        LatLng latLng = new LatLng(lat,lng  );
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng,zoom);
        mMap.moveCamera(update);
    }

}
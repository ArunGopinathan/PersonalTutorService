package edu.uta.cse.personaltutorservice.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

import edu.uta.cse.personaltutorservice.Request_Objects.NearbyServicesResponse;
import edu.uta.cse.personaltutorservice.R;
import edu.uta.cse.personaltutorservice.Model_Objects.Service;

public class NearbyTutorsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    public String lattitude,longitude;
    public static String hostname = "http://personaltutor.uta.ngrok.io/PersonalTutorServiceWebService/PTSWebService/";
    public static String getNearestTutorsmethod = "GetNearestTutors/";
    public String userId;
    public NearbyServicesResponse services = new NearbyServicesResponse();
    LatLngBounds.Builder builder = new LatLngBounds.Builder();
    public HashMap<String, String> idtoUserId = new HashMap<String, String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_tutors);

        NearbyTutorsAsyncTask task = new NearbyTutorsAsyncTask();
        task.execute();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnInfoWindowClickListener(this);
        if(services!=null && services.getServices()!=null){
            //Services list = new Services();
            List<Service> list = services.getServices().getServices();
            Service[] services_list = list.toArray(new Service[list.size()]);
            for(Service s : services_list){
                if(s.getUser().getUserId() == Integer.parseInt(userId)) {
                    LatLng userLocation = new LatLng(Double.parseDouble(s.getServiceLattitude()),Double.parseDouble(s.getServiceLongitude()));

                    Marker marker = mMap.addMarker(new MarkerOptions().position(userLocation).title("My Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                  //  idtoUserId.put(marker.getId(),s.getUser().getUserId()+"");
                    builder.include(userLocation);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));
                }
                else{
                    LatLng serviceLocation = new LatLng(Double.parseDouble(s.getServiceLattitude()),Double.parseDouble(s.getServiceLongitude()));
                    DecimalFormat df = new DecimalFormat("#.##");
                    builder.include(serviceLocation);
                    String miles = df.format(s.getMiles());
                    Marker marker = mMap.addMarker(new MarkerOptions().position(serviceLocation).title(s.getServiceName() + "(" + miles + " miles)"));
                   // idtoUserId.put(marker.getId(),s.getUser().getUserId()+"");
                    idtoUserId.put(marker.getId(),s.getServiceId()+"");
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(serviceLocation));
                }
            }
            LatLngBounds bounds = builder.build();
            int padding = 0; // offset from edges of the map in pixels
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
            googleMap.animateCamera(cu);
        }





        // Add a marker in Sydney and move the camera
      //  LatLng sydney = new LatLng(-34, 151);
       // mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
    @Override
    public void onInfoWindowClick(Marker marker){
        Log.w("PTS-Android",marker.getId());

            Log.w("PTS-Android", "Service Id -->" + idtoUserId.get(marker.getId()));
        Intent intent = new Intent(getApplicationContext(), SubjectProfileActivity.class);
        intent.putExtra("SERVICE_ID", idtoUserId.get(marker.getId()));
        startActivity(intent);

    }
    private String getNearestTutors(){
        String result = "";
        DefaultHttpClient httpclient = new DefaultHttpClient();
        try {
            String url = hostname + getNearestTutorsmethod + lattitude.trim() + "/" + longitude.trim();
            HttpGet getRequest = new HttpGet(url);
            HttpResponse httpResponse = httpclient.execute(getRequest);
            HttpEntity entity = httpResponse.getEntity();
            Log.w("PTS-Android", httpResponse.getStatusLine().toString());
            if (entity != null) {
                result = EntityUtils.toString(entity);
                Log.w("PTS-Android", "Entity : " + result);
                Gson gson = new Gson();
                services = gson.fromJson(result,NearbyServicesResponse.class);

                Log.w("PTS-Android", "Services:" + services.toString());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            httpclient.getConnectionManager().shutdown();
        }


        return result;
    }
    private class NearbyTutorsAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            //  super.onPreExecute();
          //  progressBar.setVisibility(View.VISIBLE);
            SharedPreferences sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
             lattitude = sharedPreferences.getString("User-Lattitude", "NA");
             longitude = sharedPreferences.getString("User-Longitude","NA");
            userId = sharedPreferences.getString("UserId",null);

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            // super.onPostExecute(aVoid);
          //  progressBar.setVisibility(View.GONE);

            if(services.getServices().getServices().size()==1 && services.getServices().getServices().get(0).getUser().getUserId()==Integer.parseInt(userId) ){
                Toast.makeText(NearbyTutorsActivity.this, "No Services Available near your location", Toast.LENGTH_SHORT).show();
                onMapReady(mMap);
            }
            else{
                //call the on map ready because we got the service nw only
                onMapReady(mMap);
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
           String Result = getNearestTutors();
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {


        }
    }
}

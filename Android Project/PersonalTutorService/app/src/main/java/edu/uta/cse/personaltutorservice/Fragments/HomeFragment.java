package edu.uta.cse.personaltutorservice.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import  com.quinny898.library.persistentsearch.SearchBox;
import com.quinny898.library.persistentsearch.SearchResult;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.net.URLEncoder;
import java.util.ArrayList;

import edu.uta.cse.personaltutorservice.Activities.NearbyTutorsActivity;
import edu.uta.cse.personaltutorservice.Model_Objects.Services;
import edu.uta.cse.personaltutorservice.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static String hostname = "http://personaltutor.uta.ngrok.io/PersonalTutorServiceWebService/PTSWebService/";
    public static String method = "BasicSearch/";
    public Services services = new Services();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View rootView;
    Button logOutButton,mapsButton;
    TextView userNameTV;
    SearchBox searchBox;
    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_home, container, false);
        final SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String firstName =  sharedPreferences.getString("FirstName", null);
        String LastName = sharedPreferences.getString("LastName", null);
        String greetingName = LastName+", "+firstName;
        userNameTV = (TextView) rootView.findViewById(R.id.userName);
        userNameTV.setText(greetingName);
        searchBox = (SearchBox) rootView.findViewById(R.id.searchbox);
        searchBox.enableVoiceRecognition(this);
        ((EditText) searchBox.findViewById(R.id.search)).setTextColor(Color.parseColor("#000000"));
       // searchBox.setLogoTextColor(Color.parseColor("#000000"));

        searchBox.setSearchListener(new SearchBox.SearchListener() {
            @Override
            public void onSearchOpened() {

            }

            @Override
            public void onSearchCleared() {

            }

            @Override
            public void onSearchClosed() {

            }

            @Override
            public void onSearchTermChanged(String s) {

            }

            @Override
            public void onSearch(String s) {
               // Toast.makeText(rootView.getContext(), "searched->"+s, Toast.LENGTH_SHORT).show();
                getAllServicesAsyncTask task = new getAllServicesAsyncTask();
                try
                {
                    task.execute(URLEncoder.encode(s,"utf-8"));
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }


            }

            @Override
            public void onResultClick(SearchResult searchResult) {

            }
        });
        mapsButton = (Button) rootView.findViewById(R.id.btnNearbyPlaces);
        mapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(getActivity(),NearbyTutorsActivity.class);
                startActivity(intent);
            }
        });
        logOutButton = (Button)rootView.findViewById(R.id.logOutButton);
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
               getActivity().finish();
            }
        });
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /*@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (isAdded() && requestCode == SearchBox.VOICE_RECOGNITION_CODE && resultCode == getActivity().RESULT_OK) {
            ArrayList<String> matches = data
                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String resultString = "";
           if(matches.size()>=1){
               resultString = matches.get(0);
           }
            searchBox.populateEditText(resultString);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private String searchFor(String query ){
        String result = "";
        DefaultHttpClient httpclient = new DefaultHttpClient();
        try {
            String url = hostname + method + query;
            HttpGet getRequest = new HttpGet(url);
            HttpResponse httpResponse = httpclient.execute(getRequest);
            HttpEntity entity = httpResponse.getEntity();
            Log.w("PTS-Android", httpResponse.getStatusLine().toString());
            if (entity != null) {
                result = EntityUtils.toString(entity);
                Log.w("PTS-Android", "Entity : " + result);
                Gson gson = new Gson();
                JsonParser parser = new JsonParser();
                JsonObject jsonObject= (JsonObject) parser.parse(result);
                JsonElement element = jsonObject.get("Services");
                services = gson.fromJson(jsonObject.toString(),Services.class);


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

    private class getAllServicesAsyncTask extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(String... params) {
            searchFor(params[0]);
            //getAllServicesByUsername(sharedPreferences.getString("Email", null));
            return null;
        }

        protected void onPostExecute(Void aVoid) {
            Log.w(  "PTS-Android",services.toString());
        }
    }
}

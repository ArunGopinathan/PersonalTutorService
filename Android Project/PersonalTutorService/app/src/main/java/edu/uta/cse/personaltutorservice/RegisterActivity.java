package edu.uta.cse.personaltutorservice;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.net.URI;

public class RegisterActivity extends ActionBarActivity {
    public static String hostname = "http://personaltutor.uta.ngrok.io/PersonalTutorServiceWebService/PTSWebService/";
    public static String checkmethod = "CheckAvailability/";
    public static String registermethod = "Register";
    public static String address_hostname = "http://api.matchbox.io/address/";
    public static String address_addr_1 = "?addr_1=";
    public static String address_addr_2 = "?addr_2=";
    public static String address_city_state_zip = "&city_state_zip=";
    public String address_validate_request = "";
    public String registerResult= "";
    Spinner userTypeSpinner;
    EditText firstNameEditText, lastNameEditText, emailEditText, passwordEditText, confirmPasswordEditText;
    EditText addressLine1Text, addressLine2Text, cityEditText, zipEditText, phoneEditText;
    AutoCompleteTextView stateEditText;
    Button registerButton;
    String firstName, lastName, email, password, userType, addressLine1, addressLine2, city, state, zipcode, phone, lattitude, longitude;
    boolean isuserNameAvailable, isAddressValidated;
    String registerRequestJson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        String[] states = getResources().getStringArray(R.array.statearray);
        lattitude = "NA";
        longitude = "NA";
        isAddressValidated = false;
        setContentView(R.layout.activity_register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        registerButton = (Button) findViewById(R.id.SignUpButton);
        userTypeSpinner = (Spinner) findViewById(R.id.userTypeSpinner);
        firstNameEditText = (EditText) findViewById(R.id.txtFirstName);
        lastNameEditText = (EditText) findViewById(R.id.txtLastName);
        emailEditText = (EditText) findViewById(R.id.txtEmail);
        emailEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {

                    if (emailEditText.getText().length() > 0 && emailEditText.getText().toString() != "" && isValidEmail(emailEditText.getText().toString())) {
                        email = emailEditText.getText().toString();
                        CheckAvailabilityAsyncTask task = new CheckAvailabilityAsyncTask();
                        task.execute();

                    } else {
                        Toast.makeText(RegisterActivity.this, "Please Enter Valid Email", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        passwordEditText = (EditText) findViewById(R.id.txtPassword);
        confirmPasswordEditText = (EditText) findViewById(R.id.txtConfirmPassword);
        userTypeSpinner = (Spinner) findViewById(R.id.userTypeSpinner);
        addressLine1Text = (EditText) findViewById(R.id.txtAddressLine1);
        addressLine2Text = (EditText) findViewById(R.id.txtAddressLine2);
        cityEditText = (EditText) findViewById(R.id.txtCity);
        stateEditText = (AutoCompleteTextView) findViewById(R.id.txtState);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,states);
        stateEditText.setAdapter(adapter);
        zipEditText = (EditText) findViewById(R.id.txtZipCode);
        zipEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (addressLine1Text.getText().length() > 0 && addressLine1Text.getText().toString() != "") {
                        addressLine1 = addressLine1Text.getText().toString();
                        if (addressLine2Text.getText().length() > 0 && addressLine2Text.getText().toString() != "") {
                            addressLine2 = addressLine2Text.getText().toString();
                            if (cityEditText.getText().length() > 0 && cityEditText.getText().toString() != "") {
                                city = cityEditText.getText().toString();
                                if (stateEditText.getText().length() > 0 && stateEditText.getText().toString() != "") {
                                    state = stateEditText.getText().toString();
                                    if (zipEditText.getText().length() > 0 && zipEditText.getText().toString() != "") {
                                        zipcode = zipEditText.getText().toString();
                                        isAddressValidated = true;
                                        ValidateAddressAsyncTask validateTask = new ValidateAddressAsyncTask();
                                        validateTask.execute();
                                    } else {
                                        Toast.makeText(RegisterActivity.this, "Please Enter ZipCode", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(RegisterActivity.this, "Please Enter State", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(RegisterActivity.this, "Please Enter City", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(RegisterActivity.this, "Please Enter Address Line2", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(RegisterActivity.this, "Please Enter Address Line1", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        phoneEditText = (EditText) findViewById(R.id.txtPhoneNumber);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firstNameEditText.getText().length() > 0 && firstNameEditText.getText().toString() != "") {
                    firstName = firstNameEditText.getText().toString();
                    if (lastNameEditText.getText().length() > 0 && lastNameEditText.getText().toString() != "") {
                        lastName = lastNameEditText.getText().toString();

                        if (isuserNameAvailable) {
                            // emailEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.tick, 0);
                            if (passwordEditText.getText().length() > 0 && passwordEditText.getText().toString() != "") {
                                if (confirmPasswordEditText.getText().length() > 0 && confirmPasswordEditText.getText().toString() != "") {
                                    if (passwordEditText.getText().toString().equals(confirmPasswordEditText.getText().toString())) {
                                        password = LoginActivity.md5(passwordEditText.getText().toString());

                                        int selectedIndex = userTypeSpinner.getSelectedItemPosition();
                                        if (selectedIndex != 0) {
                                            userType = Integer.toString(selectedIndex);
                                            if(isAddressValidated){
                                                if(phoneEditText.getText().length()>0 && phoneEditText.getText().toString()!="" && validCellPhone(phoneEditText.getText().toString())){
                                                    phone = phoneEditText.getText().toString();

                                                    RegisterAsyncTask registerTask = new RegisterAsyncTask();
                                                    registerTask.execute();
                                                }
                                                else{
                                                    Toast.makeText(RegisterActivity.this, "Please Enter Phone Number", Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                            else{
                                                Toast.makeText(RegisterActivity.this, "Address Needs to be Validated", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Toast.makeText(RegisterActivity.this, "Please Select user Type", Toast.LENGTH_SHORT).show();
                                        }

                                        // Toast.makeText(RegisterActivity.this,selectedIndex , Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(RegisterActivity.this, "Password does not match", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(RegisterActivity.this, "Please Confirm Password", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(RegisterActivity.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(RegisterActivity.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(RegisterActivity.this, "Please Enter Last Name", Toast.LENGTH_SHORT).show();
                    }
                } else

                {
                    Toast.makeText(RegisterActivity.this, "Please Enter First Name", Toast.LENGTH_SHORT).show();
                }

            }


        });


    }
    public boolean validCellPhone(String number)
    {
        return android.util.Patterns.PHONE.matcher(number).matches();
    }
    public static boolean isValidEmail(CharSequence target) {
        return target != null && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public boolean checkAvailability(String username) {
        boolean isavailable = false;
        String result = "";
        DefaultHttpClient httpclient = new DefaultHttpClient();
        try {
            String url = hostname + checkmethod + username;
            HttpGet getRequest = new HttpGet(url);
            HttpResponse httpResponse = httpclient.execute(getRequest);
            HttpEntity entity = httpResponse.getEntity();
            Log.w("PTS-Android", httpResponse.getStatusLine().toString());
            if (entity != null) {
                result = EntityUtils.toString(entity);
                Log.w("PTS-Android", "Entity : " + result);
                if (result.equals("YES")) {
                    isavailable = false;
                } else
                    isavailable = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            httpclient.getConnectionManager().shutdown();
        }
        return isavailable;
    }
    public String validateAddress(){
        String result = "";
        DefaultHttpClient httpclient = new DefaultHttpClient();
        try {
            HttpGet getRequest = new HttpGet(address_validate_request);
            getRequest.setHeader("Cookie", "connect.sid=s%3Apxju73iJEdw8pbqqc9IYk8_HWt3owKfS.fmbhZBZR2ktoIKlAW5GHJPUYH2dMd3tb3KjzOPcRkqA");
            getRequest.setHeader("apikey", "62b245edf9b70fb5a2f88c824ddb6013");
            HttpResponse httpResponse = httpclient.execute(getRequest);
            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity);
                Log.w("PTS-Android", "Entity : " + result);
                JSONObject resultJson = new JSONObject(result);
                try {
                    lattitude = resultJson.getJSONObject("location").getString("latitude");
                    longitude = resultJson.getJSONObject("location").getString("longitude");
                    if( lattitude.equals("0") || longitude.equals("0"))
                        isAddressValidated = false;
                    else
                        isAddressValidated = true;
                }catch(Exception ex){
                    isAddressValidated = false;
                }

            }
        }
        catch(Exception ex ){
            ex.printStackTrace();
        }
        finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            httpclient.getConnectionManager().shutdown();
        }


        return result;
    }
    private class ValidateAddressAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
           // super.onPreExecute();
            address_validate_request = address_hostname +address_addr_1 +Uri.encode(addressLine1 + " "+addressLine2 )+ address_city_state_zip+ Uri.encode(city+" "+state+" "+zipcode);
            Log.w("PTS-Android",address_validate_request);
        }

        @Override
        protected Void doInBackground(Void... params) {
            validateAddress();
            return null;
        }
    }

    private class CheckAvailabilityAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            isuserNameAvailable = checkAvailability(email);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (isuserNameAvailable) {
                emailEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.tick, 0);
            } else {

                emailEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.cross, 0);
                Toast.makeText(RegisterActivity.this, "User Name Not Available", Toast.LENGTH_SHORT).show();
            }

        }
    }
    private void generateRegisterJson(){
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUserType(userType);
        user.setEmail(email);
        user.setPassword(password);

        Address address = new Address();
        address.setAddressLine1(addressLine1);
        address.setAddressLine2(addressLine2);
        address.setCity(city);
        address.setState(state);
        address.setZipCode(zipcode);
        address.setLattitude(lattitude);
        address.setLongitude(longitude);

        user.setAddress(address);
        user.setPhoneNumber(phone);

        registerRequestJson =  User.toJsonString(user);

    }
    public String registerUser(){
        String result = "";
        DefaultHttpClient httpclient = new DefaultHttpClient();
        try{
            String url = hostname +registermethod;
            HttpPost postRequest = new HttpPost(url);
            postRequest.addHeader("Content-Type", "application/json");
            StringEntity postentity = new StringEntity(registerRequestJson, "UTF-8");
            postentity.setContentType("application/json");
            postRequest.setEntity(postentity);

            HttpResponse httpResponse = httpclient.execute(postRequest);

            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity);
                Log.w("PTS-Android", "Entity : " + result);
            }

        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            httpclient.getConnectionManager().shutdown();
        }
        return result;
    }
    private class RegisterAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
          //  super.onPreExecute();

            generateRegisterJson();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
           // super.onPostExecute(aVoid);
            if(registerResult.equals("YES")){
                Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();

            }
            else{
                Toast.makeText(RegisterActivity.this, "Registration Failed, Please Try again Later", Toast.LENGTH_SHORT).show();
            }
            onBackPressed();
        }

        @Override
        protected Void doInBackground(Void... params) {
            registerResult =  registerUser();
            return null;
        }
    }

}

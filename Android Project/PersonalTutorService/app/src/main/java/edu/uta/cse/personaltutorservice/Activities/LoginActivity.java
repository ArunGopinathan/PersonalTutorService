package edu.uta.cse.personaltutorservice.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.security.MessageDigest;

import edu.uta.cse.personaltutorservice.R;
import edu.uta.cse.personaltutorservice.Model_Objects.User;

public class LoginActivity extends Activity {

    public static String hostname = "http://personaltutor.uta.ngrok.io/PersonalTutorServiceWebService/PTSWebService/";
    public static String loginmethod = "Authenticate/";
    Button registerButton;
    Button loginButton;
    EditText usernameEditText, passwordEditText;
    String username, password;
    ProgressBar loginprogress;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.RelativeLayout);
        // layout.setBackgroundColor(Color.parseColor("#03A9F4"));
        user = new User();
        registerButton = (Button) findViewById(R.id.RegisterButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
        loginprogress = (ProgressBar) findViewById(R.id.loginProgressBar);
        usernameEditText = (EditText) findViewById(R.id.txtUserName);
        passwordEditText = (EditText) findViewById(R.id.txtPassword);

        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (usernameEditText.getText().length()==0 || usernameEditText.getText().toString()=="" || !RegisterActivity.isValidEmail( usernameEditText.getText().toString()) ) {
                    Toast.makeText(LoginActivity.this, "Please Enter Valid Email", Toast.LENGTH_SHORT).show();
                } else if (passwordEditText.getText().length()==0 || passwordEditText.getText().toString()=="") {
                    Toast.makeText(LoginActivity.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                } else {
                    username = usernameEditText.getText().toString();
                    password = md5(passwordEditText.getText().toString());
                    AuthenticatorAsyncTask task = new AuthenticatorAsyncTask();
                    task.execute();

                }*/
                //username validation
                if (usernameEditText.getText().length() == 0 || usernameEditText.getText().toString() == "") {
                    usernameEditText.setError("Email Field is Required");
                } else if (!RegisterActivity.isValidEmail(usernameEditText.getText().toString())) {
                    usernameEditText.setError("Please Enter a Valid Email");
                } else {
                    usernameEditText.setError(null);
                }

                if (passwordEditText.getText().length() == 0 || passwordEditText.getText().toString() == "") {
                    passwordEditText.setError("Password Field is Required");
                } else {
                    passwordEditText.setError(null);
                }

                if(usernameEditText.getError() ==null && passwordEditText.getError() ==null){
                    username = usernameEditText.getText().toString();
                    password = md5(passwordEditText.getText().toString());
                    AuthenticatorAsyncTask task = new AuthenticatorAsyncTask();
                    task.execute();
                }


            }
        });


    }

    public static final String md5(final String toEncrypt) {
        try {
            final MessageDigest digest = MessageDigest.getInstance("md5");
            digest.update(toEncrypt.getBytes());
            final byte[] bytes = digest.digest();
            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(String.format("%02X", bytes[i]));
            }
            return sb.toString().toLowerCase();
        } catch (Exception exc) {
            return ""; // Impossibru!
        }
    }

    public User authenticateUser(String username, String password) {
        User user = new User();
        String result = "";
        DefaultHttpClient httpclient = new DefaultHttpClient();
        try {
            String url = hostname + loginmethod + username + "/" + password;
            HttpGet getRequest = new HttpGet(url);
            HttpResponse httpResponse = httpclient.execute(getRequest);
            HttpEntity entity = httpResponse.getEntity();
            Log.w("PTS-Android", httpResponse.getStatusLine().toString());
            if (entity != null) {
                result = EntityUtils.toString(entity);
                Log.w("PTS-Android", "Entity : " + result);
                user = User.parseUserjsonToJavaObject(result);
                Log.w("PTS-Android", "User:" + user.toString());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            httpclient.getConnectionManager().shutdown();
        }

        return user;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

    private class AuthenticatorAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            loginprogress.setVisibility(View.VISIBLE);

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            loginprogress.setVisibility(View.GONE);
            if (user.getEmail() == null) {
                Toast.makeText(LoginActivity.this, "Invalid User Name / Password", Toast.LENGTH_LONG).show();
            } else {
                Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                //session to store the user ID
                SharedPreferences sharedPreferences = getSharedPreferences("MyPref",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("UserId", String.valueOf(user.getUserId()));
                editor.putString("FirstName", user.getFirstName());
                editor.putString("LastName", user.getLastName());
                editor.putString("Email",user.getEmail());
                editor.putString("PhoneNumber",user.getPhoneNumber());
                String address = user.getAddress().getAddressLine1()+", ";
                if(user.getAddress().getAddressLine2()!=""){
                    address+=user.getAddress().getAddressLine2()+", ";
                }
                address+=user.getAddress().getCity()+", ";
                address+=user.getAddress().getState()+"- ";
                address+=user.getAddress().getZipCode();
                editor.putString("Address",address);
                editor.putString("User-Lattitude",user.getAddress().getLattitude());
                editor.putString("User-Longitude",user.getAddress().getLongitude());
                editor.commit();
                startActivity(mainIntent);
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            user = authenticateUser(username, password);
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            loginprogress.animate();
        }
    }
}

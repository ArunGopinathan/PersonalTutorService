package edu.uta.cse.personaltutorservice;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Mousa Almotairi & Funda Karapinar on 3/3/2016.
 */
public class registerTutorService_Fragment extends Fragment {
    DateFormat fmtDateAndTime = DateFormat.getDateTimeInstance();
    public static String hostname = "http://personaltutor.uta.ngrok.io/PersonalTutorServiceWebService/PTSWebService/";
    public static String registerTutorServiceMethod = "RegisterTutorService";
    final Calendar dateAndTime = Calendar.getInstance();
    View rootView;
    SeekBar priceSeekBar;
    TextView pricePerHour, textViewStartTime, textViewEndTime, labelAvailability;
    Button btnSetStartTime, btnSetEndTime, btnRegisterTutorService;
    AutoCompleteTextView tvCategory, tvSubCategory;
    String textCategory, textSubCategory, price, startTime, endTime, distance, advertise;
    CheckBox cbMon, cbTue, cbWed, cbThu, cbFri, cbSat, cbSun;
    TextView labelPrice;
    boolean[] days = {false, false, false, false, false, false, false};
    String[] dayNames = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
    Spinner travelInMilesSpinner;
    ProgressBar progressBar;
    String requestJson = "",registerResult="";
    int userId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String userIdint =  sharedPreferences.getString("UserId",null);
        if(userIdint==null){
            getActivity().onBackPressed();
        }
        userId =Integer.parseInt(userIdint);
        rootView = inflater.inflate(R.layout.registertutorservice_layout, container, false);
        advertise = "NO";
        pricePerHour = (TextView) rootView.findViewById(R.id.labelPrice);
        priceSeekBar = (SeekBar) rootView.findViewById(R.id.priceSeekBar);
        priceSeekBar.setProgress(0);
        // priceSeekBar.incrementProgressBy(5);
        textViewStartTime = (TextView) rootView.findViewById(R.id.textViewStartTime);
        textViewEndTime = (TextView) rootView.findViewById(R.id.textViewEndTime);
        //  btnSetStartTime = (Button) rootView.findViewById(R.id.btnSetStartTime);
        //  btnSetEndTime = (Button) rootView.findViewById(R.id.btnSetEndTime);
        btnRegisterTutorService = (Button) rootView.findViewById(R.id.registerTutorServiceBtn);
        tvCategory = (AutoCompleteTextView) rootView.findViewById(R.id.categoryAutoCompleteTextView);
        tvSubCategory = (AutoCompleteTextView) rootView.findViewById(R.id.subCategoryAutoCompleteTextView);
        labelPrice = (TextView) rootView.findViewById(R.id.labelPrice);
        travelInMilesSpinner = (Spinner) rootView.findViewById(R.id.travelInMilesSpinner);
        progressBar = (ProgressBar) rootView.findViewById(R.id.registerServiceProgress);

        cbMon = (CheckBox) rootView.findViewById(R.id.checkBoxMon);
        cbMon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                days[0] = isChecked;
                if (isAtleastOneDaySelected())
                    labelAvailability.setError(null);


            }
        });
        cbTue = (CheckBox) rootView.findViewById(R.id.checkBoxTue);
        cbTue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                days[1] = isChecked;
                if (isAtleastOneDaySelected())
                    labelAvailability.setError(null);
            }
        });
        cbWed = (CheckBox) rootView.findViewById(R.id.checkBoxWed);
        cbWed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                days[2] = isChecked;
                if (isAtleastOneDaySelected())
                    labelAvailability.setError(null);
            }
        });
        cbThu = (CheckBox) rootView.findViewById(R.id.checkBoxThu);
        cbThu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                days[3] = isChecked;
                if (isAtleastOneDaySelected())
                    labelAvailability.setError(null);
            }
        });
        cbFri = (CheckBox) rootView.findViewById(R.id.checkBoxFri);
        cbFri.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                days[4] = isChecked;
                if (isAtleastOneDaySelected())
                    labelAvailability.setError(null);
            }
        });
        cbSat = (CheckBox) rootView.findViewById(R.id.checkBoxSat);
        cbSat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                days[5] = isChecked;
                if (isAtleastOneDaySelected())
                    labelAvailability.setError(null);
            }
        });
        cbSun = (CheckBox) rootView.findViewById(R.id.checkBoxSun);
        cbSun.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                days[6] = isChecked;
                if (isAtleastOneDaySelected())
                    labelAvailability.setError(null);
            }
        });

        labelAvailability = (TextView) rootView.findViewById(R.id.labelAvailability);

        btnRegisterTutorService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tvCategory.getText().toString().equals("") || tvCategory.getText().toString() == null) {
                    tvCategory.setError("Please Enter Category");
                } else {
                    tvCategory.setError(null);
                    textCategory = tvCategory.getText().toString();
                }

                if (tvSubCategory.getText().toString().equals("") || tvSubCategory.getText().toString() == null) {
                    tvSubCategory.setError("Please Enter Sub Category");
                } else {
                    tvSubCategory.setError(null);
                    textSubCategory = tvSubCategory.getText().toString();
                }

                if (labelPrice.getText().toString().equals("0 USD")) {
                    labelPrice.setError("Price Per Hour should be greater than 0");
                } else {
                    labelPrice.setError(null);
                    price = labelPrice.getText().toString();
                }

                if (cbMon.isChecked() == false && cbTue.isChecked() == false && cbWed.isChecked() == false && cbThu.isChecked() == false && cbFri.isChecked() == false && cbSat.isChecked() == false && cbSun.isChecked() == false) {
                    labelAvailability.setError("At least one day should be selected");
                } else {
                    labelAvailability.setError(null);
                    if (cbMon.isChecked())
                        days[0] = true;

                    if (cbTue.isChecked())
                        days[1] = true;

                    if (cbWed.isChecked())
                        days[2] = true;

                    if (cbThu.isChecked())
                        days[3] = true;

                    if (cbFri.isChecked())
                        days[4] = true;

                    if (cbSat.isChecked())
                        days[5] = true;

                    if (cbSun.isChecked())
                        days[6] = true;

                }

                if (textViewStartTime.getText().toString().equals("")) {
                    textViewStartTime.setError("Start Time is Required");
                } else {
                    textViewStartTime.setError(null);
                    startTime = textViewStartTime.getText().toString();
                }

                if (textViewEndTime.getText().toString().equals("")) {
                    textViewEndTime.setError("End Time is Required");
                } else {
                    textViewEndTime.setError(null);
                    endTime = textViewEndTime.getText().toString();
                }
                View view = travelInMilesSpinner.getSelectedView();
                TextView textView = null;

                    if (view != null && view instanceof TextView) {
                        textView = (TextView) view;
                        if (travelInMilesSpinner.getSelectedItemPosition() == 0) {

                            textView.setError("Please Select Distance Willing to travel");
                        } else {
                            textView.setError(null);
                            distance = travelInMilesSpinner.getSelectedItem().toString();
                        }
                    }


                //call the async task to
                if (tvCategory.getError() == null && tvSubCategory.getError() == null && labelPrice.getError()== null && labelAvailability.getError() == null && textViewStartTime.getError() == null && textViewEndTime.getError() == null && travelInMilesSpinner.getSelectedItemPosition() > 0) {
                    registerTutorServiceAsyncTask task = new registerTutorServiceAsyncTask();
                    task.execute();
                }
            }

        });

     /*   btnSetEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                dateAndTime.set(Calendar.MINUTE, minute);
                                //  System.out.print(view.getClass().getName());
                                textViewEndTime.setText(formatTime());

                            }
                        },
                        dateAndTime.get(Calendar.HOUR_OF_DAY),
                        dateAndTime.get(Calendar.MINUTE),
                        true).show();
            }
        });*/
        textViewStartTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    textViewStartTime.callOnClick();
            }
        });
        textViewStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStartTime();
                textViewStartTime.setError(null);
            }

        });
        textViewEndTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    textViewEndTime.callOnClick();
            }
        });
        textViewEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEndTime();
                textViewEndTime.setError(null);
            }
        });
      /*  btnSetStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                dateAndTime.set(Calendar.MINUTE, minute);
                                //  System.out.print(view.getClass().getName());
                                textViewStartTime.setText(formatTime());
                            }
                        },
                        dateAndTime.get(Calendar.HOUR_OF_DAY),
                        dateAndTime.get(Calendar.MINUTE),
                        true).show();
            }
        });*/
        priceSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int currentProgress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                currentProgress = progress;
                String priceText = String.valueOf((int) currentProgress) + " USD";
                pricePerHour.setText(priceText.trim());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {


            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (currentProgress > 0) {
                    labelPrice.setError(null);
                }
                String priceText = String.valueOf((int) currentProgress) + " USD";
                pricePerHour.setText(priceText.trim());

            }
        });
        return rootView;
    }

    public String generateRegisterTutorServiceRequestJson() {
        String result = "";
        RegisterServiceRequestObject request = new RegisterServiceRequestObject();
        request.setUserId(userId);
        Category category = new Category();
        category.setCategoryName(textCategory);
        request.setCategory(category);

        SubCategory subCategory = new SubCategory();
        subCategory.setSubCategoryName(textSubCategory);
        request.setSubCategory(subCategory);
        ArrayList<Days> list = new ArrayList<Days>();
        for (int i = 0; i < days.length; i++) {
            if (days[i] == true) {
                Days day = new Days();
                day.setName(dayNames[i]);
                day.setStartTime(startTime);
                day.setEndTime(endTime);
                list.add(day);
            }
        }

        Days[] days = new Days[list.size()];
        days = list.toArray(days);

        Availability availability = new Availability();
        availability.setDays(days);

        request.setAvailability(availability);
        request.setPricePerHour(price);
        request.setWillingToTravelInMiles(distance);
        request.setAdvertise(advertise);

        result = RegisterServiceRequestObject.toJsonString(request);
        return result;
    }
public String registerTutorService(){
    String result = "";
    DefaultHttpClient httpclient = new DefaultHttpClient();
    try{
        String url = hostname + registerTutorServiceMethod;
        HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader("Content-Type", "application/json");
        StringEntity postentity = new StringEntity(requestJson, "UTF-8");
        postentity.setContentType("application/json");
        postRequest.setEntity(postentity);

        HttpResponse httpResponse = httpclient.execute(postRequest);

        HttpEntity entity = httpResponse.getEntity();
        if (entity != null) {
            result = EntityUtils.toString(entity);
            Log.w("PTS-Android", "Entity : " + result);
        }

    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        // When HttpClient instance is no longer needed,
        // shut down the connection manager to ensure
        // immediate deallocation of all system resources
        httpclient.getConnectionManager().shutdown();
    }
    return result;


}
    public String formatTime() {
        String myFormat = "hh:mm a"; // your own format
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        String formated_time = sdf.format(dateAndTime.getTime());
        return formated_time;
    }

    public void setStartTime() {
        new TimePickerDialog(getActivity(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        dateAndTime.set(Calendar.MINUTE, minute);
                        //  System.out.print(view.getClass().getName());
                        textViewStartTime.setText(formatTime());
                    }
                },
                dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE),
                true).show();
    }

    public void setEndTime() {
        new TimePickerDialog(getActivity(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        dateAndTime.set(Calendar.MINUTE, minute);
                        //  System.out.print(view.getClass().getName());
                        textViewEndTime.setText(formatTime());
                    }
                },
                dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE),
                true).show();
    }

    public boolean isAtleastOneDaySelected() {
        boolean result = false;

        for (int i = 0; i < days.length; i++)
            if (days[i] == true) {
                result = true;
                break;
            }
        return result;
    }

    private class registerTutorServiceAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            requestJson = generateRegisterTutorServiceRequestJson();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            progressBar.animate();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressBar.setVisibility(View.GONE);
            if (registerResult.equals("YES")) {
                Toast.makeText(getActivity(), "Tutor Service Registered Successfully", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(getActivity(), "Registration Failed, Please Try again Later", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected Void doInBackground(Void... params) {

          registerResult =  registerTutorService();
            return null;
        }
    }

}

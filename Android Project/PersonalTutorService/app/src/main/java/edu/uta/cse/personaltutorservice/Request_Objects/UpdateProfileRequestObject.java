package edu.uta.cse.personaltutorservice.Request_Objects;

import android.util.Log;

import com.google.gson.Gson;

import edu.uta.cse.personaltutorservice.Model_Objects.Address;

/**
 * Created by HaoKing on 3/17/2016.
 */
public class UpdateProfileRequestObject {
    private String firstname;
    private String lastname;
    private String email;
    private String phonenumber;
    private Address address;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static String toJsonString(UpdateProfileRequestObject updateProfileRequestObject){
        Gson gson = new Gson();
        String result = "";
        UpdateProfileRequest request = new UpdateProfileRequest();
        request.setUpdateProfileRequestObject(updateProfileRequestObject);
        try{
            result = gson.toJson(request, UpdateProfileRequest.class);
            Log.w("PTS-Android", result);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }

        return result;

    }
}

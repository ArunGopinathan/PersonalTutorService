package edu.uta.cse.personaltutorservice.Model_Objects;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import edu.uta.cse.personaltutorservice.Request_Objects.UserRequest;

/**
 * Created by Arun on 2/15/2016.
 */
public class User
{

    private int UserId;
    private String UserType;

    private String Email;

    private Address Address;

    private String Password;

    private String FirstName;

    private String PhoneNumber;

    private String LastName;
    private String initials;

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getUserType ()
    {
        return UserType;
    }

    public void setUserType (String UserType)
    {
        this.UserType = UserType;
    }

    public String getEmail ()
    {
        return Email;
    }

    public void setEmail (String Email)
    {
        this.Email = Email;
    }

    public edu.uta.cse.personaltutorservice.Model_Objects.Address getAddress ()
    {
        return Address;
    }

    public void setAddress (Address Address)
    {
        this.Address = Address;
    }

    public String getPassword ()
    {
        return Password;
    }

    public void setPassword (String Password)
    {
        this.Password = Password;
    }

    public String getFirstName ()
    {
        return FirstName;
    }

    public void setFirstName (String FirstName)
    {
        this.FirstName = FirstName;
    }

    public String getPhoneNumber ()
    {
        return PhoneNumber;
    }

    public void setPhoneNumber (String PhoneNumber)
    {
        this.PhoneNumber = PhoneNumber;
    }

    public String getLastName ()
    {
        return LastName;
    }

    public void setLastName (String LastName)
    {
        this.LastName = LastName;
    }
    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public static User parseUserjsonToJavaObject(String userjson) {
        // parser to parse the json object
        JsonParser parser = new JsonParser();
        JsonObject obj = (JsonObject) parser.parse(userjson);

        User user = new User();// the user object we are trying to populate
        Gson gson = new Gson();// create a gson object
        try {
            // the user json is down one level, so get that and convert to java
            // object
            user = gson.fromJson(obj.get("User").toString(), User.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return user;
    }

    public static String toJsonString(User user){
        Gson gson = new Gson();
        String result = "";
        UserRequest request = new UserRequest();
        request.setUser(user);
        try{
            result = gson.toJson(request, UserRequest.class);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }

        return result;
    }
}
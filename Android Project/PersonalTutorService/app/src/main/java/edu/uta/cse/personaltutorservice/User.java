package edu.uta.cse.personaltutorservice;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import edu.uta.cse.personaltutorservice.Address;

/**
 * Created by Arun on 2/15/2016.
 */
public class User
{

    private String UserType;

    private String Email;

    private Address Address;

    private String Password;

    private String FirstName;

    private String PhoneNumber;

    private String LastName;

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

    public edu.uta.cse.personaltutorservice.Address getAddress ()
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

    @Override
    public String toString()
    {
        return "edu.uta.cse.personaltutorservice.User [UserType = "+UserType+", Email = "+Email+", edu.uta.cse.personaltutorservice.Address = "+Address+", Password = "+Password+", FirstName = "+FirstName+", PhoneNumber = "+PhoneNumber+", LastName = "+LastName+"]";
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
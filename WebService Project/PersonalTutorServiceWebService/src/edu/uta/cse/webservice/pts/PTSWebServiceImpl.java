/**
 * 
 */
package edu.uta.cse.webservice.pts;

/**
 * @author Arun
 *
 */

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/*
 * localhost link :
 * http://localhost:8080/PersonalTutorServiceWebService/PTSWebService/Register
 */
@Path("PTSWebService")
public class PTSWebServiceImpl {
	
	@POST
	@Path("/Register/")
	@Produces(MediaType.TEXT_XML)
	public void registerUser(String request){
		//opens the connection with the personal tutor database
		MySqlHelper helper = new MySqlHelper();
		//parse json to JavaObject
		User user = parseUserjsonToJavaObject(request);
		
		String loginQuery = generateLoginQuery(user);
		int userId = helper.executeInsertQueryAndReturnId(loginQuery);
		
		String addressQuery = generateAddressQuery(user, userId);
		int addressId = helper.executeInsertQueryAndReturnId(addressQuery);
		
		String personalInfoQuery = generatePersonalInfoQuery(user, userId, addressId);
		helper.executeInsertQueryAndReturnId(personalInfoQuery);
		
		//close the connection
		helper.disposeConnection();
		
	}
	
	
	public String generateLoginQuery(User user){
		String query = "insert into login(Email,Password) values("
				+"'"+user.getEmail()+"','"
				+user.getPassword()+"')";	
		return query;
	}
	public String generateAddressQuery(User user,int userId){
	  String query="insert into address(UserId,AddressLine1, AddressLine2, City, State, ZipCode, Lattitude,Longitude) values( "
			  
			  +userId+",'"
			  +user.getAddress().getAddressLine1()+"','"+
			  user.getAddress().getAddressLine2()+"','"+
			  user.getAddress().getCity()+"','"+
			  user.getAddress().getState()+"','"+
			  user.getAddress().getZipCode()+"','"+
			  user.getAddress().getLattitude()+"','"+
			  user.getAddress().getLongitude()+"')";
	  
	  return query;
	}
	public String generatePersonalInfoQuery(User user,int userId, int addressId){
		String query = "insert into personalinfo(UserId, AddressId, FirstName, LastName, PhoneNumber) values("
				+userId+","
				+ addressId+",'"+
				user.getFirstName()+"','"+
				user.getLastName()+"','"+
				user.getPhoneNumber()+"')";
		return query;
	}
	
	/*User JSON to Java Object*/
	public User parseUserjsonToJavaObject(String userjson){
		//parser to parse the json object
		JsonParser parser = new JsonParser();
		JsonObject obj = (JsonObject) parser.parse(userjson);
		
		User user = new User();//the user object we are trying to populate
		Gson gson = new Gson();//create a gson object
		try{
			//the user json is down one level, so get that and convert to java object
			user = gson.fromJson(obj.get("User").toString(), User.class);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		
		return user;
	}

}

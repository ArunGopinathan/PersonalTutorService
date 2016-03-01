/**
 * 
 */
package edu.uta.cse.webservice.pts;

/**
 * @author Arun
 *
 */

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringEscapeUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mysql.jdbc.PreparedStatement;
import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

/*
 * localhost link :
 * http://localhost:8080/PersonalTutorServiceWebService/PTSWebService/Register
 */
@Path("PTSWebService")
public class PTSWebServiceImpl {

	@POST
	@Path("/Register/")
	@Produces(MediaType.TEXT_PLAIN)
	public String registerUser(String request) {
		// opens the connection with the personal tutor database
		try {
			MySqlHelper helper = new MySqlHelper();

			// parse json to JavaObject
			User user = parseUserjsonToJavaObject(request);

			// register the login
			int userId = registerLogin(helper, user);
			// add the address of the user
			int addressId = registerAddressOfUser(helper, user, userId);
			// Add the personal info of the user
			registerPersonalInfoOfUser(helper, user, userId, addressId);

			// close the connection
			helper.disposeConnection();
			return "YES";
		} catch (Exception ex) {
			return "NO";
		}

	}
	@POST
	@Path("RegisterTutorService")
	@Produces(MediaType.TEXT_PLAIN)
	public String RegisterService(String requestJson){
		String result ="HELLO WORLD";
		RegisterServiceRequest request = null;
		request = parseRegisterServiceRequestJsonToJavaObject(requestJson);
		return request.toString();
	}

	@Path("CheckAvailability/{username}")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String CheckAvailability(@PathParam("username") String username) {
		String result = "";
		MySqlHelper helper = new MySqlHelper();
		if (checkUserAvailable(helper, username)) {
			result = "YES";
		} else {
			result = "NO";
		}
		helper.disposeConnection();
		return result;
	}

	@Path("/Authenticate/{username}/{password}")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String Authenticate(@PathParam("username") String username,
			@PathParam("password") String password) {
		String result = "";
		try {
			MySqlHelper helper = new MySqlHelper();

			User user = getUser(helper, username, password);
			UserResult userresult = new UserResult();
			userresult.setUser(user);
			result = parseJavaObjectToUserJson(userresult);

			java.util.Date date = new java.util.Date();
			System.out.println(new Timestamp(date.getTime()));
			System.out.println("Authentication:" + result);

			helper.disposeConnection();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return result;

	}

	public boolean checkUserAvailable(MySqlHelper helper, String username) {
		boolean result = false;
		String query = "select * from login where Email=?";
		try {
			java.sql.PreparedStatement chkUserPreparedStatement = helper.conn
					.prepareStatement(query);
			chkUserPreparedStatement.setString(1, username);
			ResultSet rs = chkUserPreparedStatement.executeQuery();
			if (rs.isBeforeFirst()) {// if result set is available then the user
										// is there in database
				result = true;
			}
		} catch (Exception ex) {
			result = true; // this would prevent adding users due to error.
		}
		return result;
	}

	public User getUser(MySqlHelper helper, String userName, String password) {
		User user = new User();
		String query = "select * "
				+ "from login u inner join address a on u.UserId = a.UserId "
				+ "inner join personalinfo p on a.AddressId = p.AddressId "
				+ "where Email=? and Password= ?";
		System.out.println(query);
		System.out.println("u=" + userName + " p=" + password);
		try {
			java.sql.PreparedStatement loginPreparedStatement = helper.conn
					.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			loginPreparedStatement.setString(1, userName);
			loginPreparedStatement.setString(2, password);
			ResultSet rs = loginPreparedStatement.executeQuery();
			while (rs.next()) {
				user.setFirstName(rs.getString("FirstName"));
				user.setLastName(rs.getString("LastName"));
				user.setUserType(Integer.toString(rs.getInt("UserTypeId")));
				user.setEmail(rs.getString("Email"));

				Address address = new Address();
				address.setAddressLine1(rs.getString("AddressLine1"));
				address.setAddressLine2(rs.getString("AddressLine2"));
				address.setCity(rs.getString("City"));
				address.setState(rs.getString("State"));
				address.setZipCode(rs.getString("ZipCode"));
				address.setLattitude(rs.getString("Lattitude"));
				address.setLongitude(rs.getString("Longitude"));

				user.setAddress(address);

				user.setPhoneNumber(rs.getString("PhoneNumber"));

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return user;
	}

	public int registerLogin(MySqlHelper helper, User user) {
		String query = "insert into login(UserTypeId,Email,Password) values(?,?,?)";
		try {
			// execute and return the keys
			java.sql.PreparedStatement loginPreparedStatement = helper.conn
					.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			loginPreparedStatement.setInt(1,
					Integer.parseInt(user.getUserType()));
			loginPreparedStatement.setString(2, user.getEmail());
			loginPreparedStatement.setString(3, user.getPassword());

			loginPreparedStatement.executeUpdate();
			ResultSet rs = loginPreparedStatement.getGeneratedKeys();
			if (rs.next()) {
				int last_inserted_id = rs.getInt(1);
				return last_inserted_id;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return -1;
	}

	public int registerAddressOfUser(MySqlHelper helper, User user, int userId) {
		String query = "insert into address(UserId,AddressLine1, AddressLine2, City, State, ZipCode, Lattitude,Longitude) values(?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			java.sql.PreparedStatement addressPreparedStatement = helper.conn
					.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

			addressPreparedStatement.setInt(1, userId);
			addressPreparedStatement.setString(2, user.getAddress()
					.getAddressLine1());
			addressPreparedStatement.setString(3, user.getAddress()
					.getAddressLine2());
			addressPreparedStatement.setString(4, user.getAddress().getCity());
			addressPreparedStatement.setString(5, user.getAddress().getState());
			addressPreparedStatement.setString(6, user.getAddress()
					.getZipCode());
			addressPreparedStatement.setString(7, user.getAddress()
					.getLattitude());
			addressPreparedStatement.setString(8, user.getAddress()
					.getLongitude());

			addressPreparedStatement.executeUpdate();
			ResultSet rs = addressPreparedStatement.getGeneratedKeys();
			if (rs.next()) {
				int last_inserted_id = rs.getInt(1);
				return last_inserted_id;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return -1;
	}

	public int registerPersonalInfoOfUser(MySqlHelper helper, User user,
			int userId, int addressId) {
		String query = "insert into personalinfo(UserId, AddressId, FirstName, LastName, PhoneNumber) values(?,?,?,?,?)";
		try {
			java.sql.PreparedStatement personalInfoPreparedStatement = helper.conn
					.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

			personalInfoPreparedStatement.setInt(1, userId);
			personalInfoPreparedStatement.setInt(2, addressId);
			personalInfoPreparedStatement.setString(3, user.getFirstName());
			personalInfoPreparedStatement.setString(4, user.getLastName());
			personalInfoPreparedStatement.setString(5, user.getPhoneNumber());

			personalInfoPreparedStatement.executeUpdate();
			ResultSet rs = personalInfoPreparedStatement.getGeneratedKeys();
			if (rs.next()) {
				int last_inserted_id = rs.getInt(1);
				return last_inserted_id;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return -1;
	}
	
	public RegisterServiceRequest parseRegisterServiceRequestJsonToJavaObject(String registerServiceRequestJSON){
		RegisterServiceRequest registerServiceRequest = new RegisterServiceRequest();
		JsonParser parser = new JsonParser();
		Gson gson = new Gson();// create a gson object
		JsonObject obj = (JsonObject) parser.parse(registerServiceRequestJSON);
		try{
			registerServiceRequest = gson.fromJson(obj.get("RegisterServiceRequest").toString(), RegisterServiceRequest.class);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return registerServiceRequest;
		
	}

	/* User JSON to Java Object */
	public User parseUserjsonToJavaObject(String userjson) {
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

	public String parseJavaObjectToUserJson(UserResult user) {
		Gson gson = new Gson();
		String result = "";

		try {
			result = gson.toJson(user, UserResult.class);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

}

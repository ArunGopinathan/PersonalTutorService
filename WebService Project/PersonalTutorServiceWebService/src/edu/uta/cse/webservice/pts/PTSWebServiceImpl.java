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
import java.util.ArrayList;

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
	public String RegisterService(String requestJson) {
		String result = "NO";
		RegisterServiceRequestObject request = null;
		request = parseRegisterServiceRequestJsonToJavaObject(requestJson);
		MySqlHelper helper = new MySqlHelper();
		try {
			// check if category exists and get the category Id
			int categoryId = checkIsCategoryAvailable(helper, request
					.getCategory().getCategoryName());
			if (categoryId == -1) {
				categoryId = insertCategory(helper, request.getCategory()
						.getCategoryName());
			}
			// check if sub category exists and get the sub category Id
			int subcategoryId = checkIsSubCategoryAvailable(helper, request
					.getSubCategory().getSubCategoryName(), categoryId);
			if (subcategoryId == -1) {
				subcategoryId = insertSubCategory(helper, request
						.getSubCategory().getSubCategoryName(), categoryId);
			}
			int pricePerHour = Integer.parseInt(request.getPricePerHour().split(" ")[0]);
			int serviceId = insertService(helper,request.getUserId(), categoryId,subcategoryId,pricePerHour,Integer.parseInt(request.getWillingToTravelInMiles()),request.getAdvertise());
		//	deleteExistingAvailability(helper, request.getUserId());
			//insert availability
			for (Days day : request.getAvailability().getDays()) {
				insertAvailability(helper, day, request.getUserId(),serviceId);
			}
			
			
			result = "YES";

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			helper.disposeConnection();
		}

		return result;
	}
	@Path("/GetAllCategories")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getAllCategories(){
		String result="";
		MySqlHelper helper = new MySqlHelper();
		String query = "select CategoryId,CategoryName from category where isCategoryActive=1";
		try{
		java.sql.PreparedStatement getAllCategoriesPreparedStatement = helper.conn
				.prepareStatement(query);
		
		ResultSet rs = getAllCategoriesPreparedStatement.executeQuery();
		Categories categories = new Categories();
		
		ArrayList<Category> list = new ArrayList<Category>();
		while(rs.next()){
			Category category = new Category();
			category.setCategoryId(String.valueOf(rs.getInt("CategoryId")));
			category.setCategoryName(rs.getString("CategoryName"));
			list.add(category);
		}
		Gson gson = new Gson();
		//String result = "";
		Category[] catagories =new Category[list.size()];
		categories.setCategories(list.toArray(catagories));
	
		
			result = gson.toJson(categories, Categories.class);
			
		
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		finally{
			helper.disposeConnection();
		}
		
		return result;
	}
	
	@Path("GetNearestTutors/{Lattitude}/{Longitude}")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getNearestTutors(@PathParam("Lattitude")String lattitude, @PathParam("Longitude") String longitude ){
		String result="";
		MySqlHelper helper = new MySqlHelper();
		String query = "SELECT addressid, ( 3959 * acos( cos( radians('"+lattitude+"') ) * cos( radians( lattitude ) ) * cos( radians( longitude ) - radians('"+longitude+"') ) + sin( radians('"+lattitude+"') ) * sin( radians( lattitude ) ) )) AS distance FROM address HAVING distance < 25 ORDER BY distance LIMIT 0 , 20";
		//String query = "call getnearesttutor('"+lattitude+"','"+longitude+"')";
		try{
			System.out.println(query);
		java.sql.PreparedStatement getnearestTutorPreparedStatement = helper.conn
				.prepareStatement(query);
		
		
		ResultSet rs = getnearestTutorPreparedStatement.executeQuery();
		ArrayList<Integer> addressList = new ArrayList<Integer>();
		while(rs.next()){
			System.out.print(rs.getInt("addressid"));
			addressList.add(rs.getInt("addressid"));
			System.out.print("\t"+rs.getFloat("distance")+"\n");
		}
		
		query = "select * from address a inner join login l on a.userid=l.userid inner join service s on s.userid = l.userid where addressid in(";
		for(int addressId : addressList){
			query+="'"+addressId+"',";
		}
		rs = null;
		query =query.substring(0,query.length()-1)+")";
		System.out.println(query);
		getnearestTutorPreparedStatement = helper.conn
				.prepareStatement(query);
		 rs = getnearestTutorPreparedStatement.executeQuery();
		while(rs.next()){
			System.out.print(rs.getInt("userid"));
			
			System.out.print("\t"+rs.getString("Lattitude")+"");
			System.out.print("\t"+rs.getString("Longitude")+"");
			System.out.print("\t"+rs.getInt("ServiceId")+"\n");
		}
		//Gson gson = new Gson();
		//String result = "";
		
	
		
			
			
		
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		finally{
			helper.disposeConnection();
		}
		
		return result;
	}
	
	@Path("GetSubCategoriesForCategory/{CategoryId}")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getAllSubCategoriesForCategory(@PathParam("CategoryId")String categoryId ){
		String result="";
		MySqlHelper helper = new MySqlHelper();
		String query = "select SubCategoryId,SubCategoryName from subcategory where isSubCategoryActive=1 and CategoryId=?";
		try{
		java.sql.PreparedStatement getAllSubCategoriesPreparedStatement = helper.conn
				.prepareStatement(query);
		getAllSubCategoriesPreparedStatement.setInt(1, Integer.parseInt(categoryId));
		
		ResultSet rs = getAllSubCategoriesPreparedStatement.executeQuery();
		SubCategories subcategories = new SubCategories();
		
		ArrayList<SubCategory> list = new ArrayList<SubCategory>();
		while(rs.next()){
			SubCategory category = new SubCategory();
			category.setSubCategoryId(String.valueOf(rs.getInt("SubCategoryId")));
			category.setSubCategoryName(rs.getString("SubCategoryName"));
			list.add(category);
		}
		Gson gson = new Gson();
		//String result = "";
		SubCategory[] catagories =new SubCategory[list.size()];
		subcategories.setSubCategories(list.toArray(catagories));
	
		
			result = gson.toJson(subcategories, SubCategories.class);
			
		
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		finally{
			helper.disposeConnection();
		}
		
		return result;
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

	// first delete the availability that exists for the user.
	public void deleteExistingAvailability(MySqlHelper helper, int UserId) {
		String query = "delete from availability where UserId=?";
		try {
			java.sql.PreparedStatement deleteStatement = helper.conn
					.prepareStatement(query);
			deleteStatement.setInt(1, UserId);
			deleteStatement.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public int insertService(MySqlHelper helper,int UserId, int CategoryId, int SubCategoryId, int pricePerHour, int Distance, String isAdvertised ){
		String query = "insert into service(UserId, CategoryId, SubCategoryId, PricePerHour, DistanceWillingToTravelInMiles, isAdvertised) values(?,?,?,?,?,?)";
		try {

			
			java.sql.PreparedStatement insertServiceStatement = helper.conn
					.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			insertServiceStatement.setInt(1, UserId);
			insertServiceStatement.setInt(2, CategoryId);
			insertServiceStatement.setInt(3, SubCategoryId);
			insertServiceStatement.setInt(4, pricePerHour);
			insertServiceStatement.setInt(5, Distance);
			insertServiceStatement.setString(6, isAdvertised);
			
			insertServiceStatement.executeUpdate();
			ResultSet rs = insertServiceStatement.getGeneratedKeys();
			if (rs.next()) {
				int last_inserted_id = rs.getInt(1);
				return last_inserted_id;
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return -1;
	}
	public void insertAvailability(MySqlHelper helper, Days day, int UserId,int ServiceId) {
		String query = "";
		try {

			query = "insert into availability(UserId,Day,StartTime,EndTime,ServiceId) values(?,?,?,?,?)";
			java.sql.PreparedStatement insertAvailabilityStatement = helper.conn
					.prepareStatement(query);
			insertAvailabilityStatement.setInt(1, UserId);
			insertAvailabilityStatement.setString(2, day.getName());
			insertAvailabilityStatement.setString(3, day.getStartTime());
			insertAvailabilityStatement.setString(4, day.getEndTime());
			insertAvailabilityStatement.setInt(5, ServiceId);

			insertAvailabilityStatement.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public int insertCategory(MySqlHelper helper, String CategoryName) {

		String query = "insert into category(CategoryName, isCategoryActive) values(?,?)";
		try {
			java.sql.PreparedStatement insertCategoryPreparedStatement = helper.conn
					.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			insertCategoryPreparedStatement.setString(1, CategoryName);
			insertCategoryPreparedStatement.setInt(2, 0);// 0 for inactive
			insertCategoryPreparedStatement.executeUpdate();
			ResultSet rs = insertCategoryPreparedStatement.getGeneratedKeys();
			if (rs.next()) {
				int last_inserted_id = rs.getInt(1);
				return last_inserted_id;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return -1;
	}

	public int insertSubCategory(MySqlHelper helper, String subCategoryName,
			int CategoryId) {

		String query = "insert into subcategory(CategoryId, SubCategoryName, isSubCategoryActive) values(?,?,?)";
		try {
			java.sql.PreparedStatement insertSubCategoryPreparedStatement = helper.conn
					.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			insertSubCategoryPreparedStatement.setInt(1, CategoryId);// 0 for
																		// inactive
			insertSubCategoryPreparedStatement.setString(2, subCategoryName);
			insertSubCategoryPreparedStatement.setInt(3, 0);// 0 for inactive
			insertSubCategoryPreparedStatement.executeUpdate();
			ResultSet rs = insertSubCategoryPreparedStatement
					.getGeneratedKeys();
			if (rs.next()) {
				int last_inserted_id = rs.getInt(1);
				return last_inserted_id;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return -1;
	}

	public int checkIsCategoryAvailable(MySqlHelper helper, String CategoryName) {
		int isAvailable = -1;
		String query = "select CategoryId from category where CategoryName=? and isCategoryActive=?";
		try {
			java.sql.PreparedStatement chkUserPreparedStatement = helper.conn
					.prepareStatement(query);
			chkUserPreparedStatement.setString(1, CategoryName);
			chkUserPreparedStatement.setInt(2, 1); // 1 for active
			ResultSet rs = chkUserPreparedStatement.executeQuery();
			if (rs.next()) {// if result set is available then the category
							// is there in database
				isAvailable = rs.getInt("CategoryId");

			}
		} catch (Exception ex) {
			isAvailable = -1;
		}

		return isAvailable;

	}

	public int checkIsSubCategoryAvailable(MySqlHelper helper,
			String subCategoryName, int CategoryId) {
		int isAvailable = -1;
		String query = "select SubCategoryId from subcategory where SubCategoryName=? and isSubCategoryActive=? and CategoryId=?";
		try {
			java.sql.PreparedStatement chkUserPreparedStatement = helper.conn
					.prepareStatement(query);
			chkUserPreparedStatement.setString(1, subCategoryName);
			chkUserPreparedStatement.setInt(2, 1); // 1 for active
			chkUserPreparedStatement.setInt(3, CategoryId);
			ResultSet rs = chkUserPreparedStatement.executeQuery();
			if (rs.next()) {// if result set is available then the category
							// is there in database
				isAvailable = rs.getInt("SubCategoryId");

			}
		} catch (Exception ex) {
			ex.printStackTrace();
			isAvailable = -1;
		}

		return isAvailable;

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
				user.setUserId(rs.getInt("UserId"));
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

	public RegisterServiceRequestObject parseRegisterServiceRequestJsonToJavaObject(
			String registerServiceRequestJSON) {
		RegisterServiceRequestObject registerServiceRequest = new RegisterServiceRequestObject();
		JsonParser parser = new JsonParser();
		Gson gson = new Gson();// create a gson object
		JsonObject obj = (JsonObject) parser.parse(registerServiceRequestJSON);
		try {
			registerServiceRequest = gson.fromJson(
					obj.get("registerServiceRequestObject").toString(),
					RegisterServiceRequestObject.class);
		} catch (Exception ex) {
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

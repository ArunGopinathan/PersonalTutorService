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

/*
 * localhost link :
 * http://localhost:8080/PersonalTutorServiceWebService/PTSWebService/Register
 */
@Path("PTSWebService")
public class PTSWebServiceImpl {
	
	@GET
	@Path("/Register/")
	@Produces(MediaType.TEXT_PLAIN)
	public String registerUser(){
		return "Hello World";
	}

}

package tbd;

import java.util.Scanner;
import java.util.UUID;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

import tbd.http.AddChoiceResponse;
import tbd.http.GetChoiceRequest;
import tbd.http.LoginUserRequest;
import tbd.http.LoginUserResponse;
import tbd.http.AddResponse;
import tbd.db.ConstantsDAO;
import tbd.db.LoginUserDAO;
import tbd.model.Choice;
import tbd.model.Constant;
import tbd.model.User;

public class UserLogin implements RequestHandler<LoginUserRequest,LoginUserResponse> {

	LambdaLogger logger;
	
	User getUser(String choiceID, String userName, String password) throws Exception{
		try {
			if (logger != null) { logger.log("in getUser"); }
			LoginUserDAO dao = new LoginUserDAO();
			System.out.println("You connected!");
			User user = dao.getUser(choiceID, userName);
			
			return user;
		}
		catch (Exception e) {
			System.out.println("getUser failed!");
			return null;
		}
	}
	
	@Override
	public LoginUserResponse handleRequest(LoginUserRequest req, Context context) {
		LoginUserRequest req1 = req;
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler of RequestHandler");
		logger.log(req1.toString());
		boolean fail = false;
		String failMessage = "";

		User user = null;
		try {
			user = getUser(req1.getChoiceID(), req1.getName(), req1.getPassword());
		} catch (Exception e) {
			fail = true;
			failMessage = "Failed to read database!";
		}
		
		if(user == null) {
			fail = true;
			failMessage = "User does not exist";
		} else {
			//Checking password
			if(user.getPassword() != null && user.getPassword().length() != 0) {
				if(req1.getPassword() == null || req1.getPassword().length() == 0) {
					fail = true;
					failMessage = "Password required";
				} else {
					if(!req1.getPassword().equals(user.getPassword())) {
						fail = true;
						failMessage = "Incorrect password";
					}
				}
			}
		}
		
		// compute proper response and return. Note that the status code is internal to the HTTP response
		// and has to be processed specifically by the client code.
		LoginUserResponse response;
		if (fail) {
			response = new LoginUserResponse(400, failMessage);
		} else {
			response = new LoginUserResponse(user.getUUID(), 200);  // success
		}

		System.out.println(response);
		//System.out.println(fail);
		return response; 
	}
}
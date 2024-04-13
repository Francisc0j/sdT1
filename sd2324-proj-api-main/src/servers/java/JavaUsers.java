package servers.java;



import tukano.api.User;
import tukano.api.java.Result;
import tukano.api.java.Users;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class JavaUsers implements Users {
	private final Map<String,User> users = new HashMap<>();

	private static Logger Log = Logger.getLogger(JavaUsers.class.getName());

	@Override
	public Result<String> createUser(User user) {
		Log.info("createUser : " + user);
		
		// Check if user data is valid
		if(user.userId() == null || user.pwd() == null || user.displayName() == null || user.email() == null) {
			Log.info("User object invalid.");
			return Result.error( Result.ErrorCode.BAD_REQUEST);
		}
		
		// Insert user, checking if name already exists
		if( users.putIfAbsent(user.userId(), user) != null ) {
			Log.info("User already exists.");
			return Result.error( Result.ErrorCode.CONFLICT);
		}
		return Result.ok( user.userId() );
	}

	@Override
	public Result<User> getUser(String userId, String pwd) {
		Log.info("getUser : user = " + userId + "; pwd = " + pwd);
		
		// Check if user is valid
		if(userId == null || pwd == null) {
			Log.info("Name or Password null.");
			return Result.error( Result.ErrorCode.BAD_REQUEST);
		}
		
		User user = users.get(userId);			
		// Check if user exists 
		if( user == null ) {
			Log.info("User does not exist.");
			return Result.error( Result.ErrorCode.NOT_FOUND);
		}
		
		//Check if the password is correct
		if( !user.pwd().equals( pwd)) {
			Log.info("Password is incorrect.");
			return Result.error( Result.ErrorCode.FORBIDDEN);
		}
		
		return Result.ok(user);
	}

	@Override
	public Result<User> updateUser(String userId, String pwd, User user) {
		return Result.error( Result.ErrorCode.NOT_IMPLEMENTED);
	}

	@Override
	public Result<User> deleteUser(String userId, String pwd) {
		return Result.error( Result.ErrorCode.NOT_IMPLEMENTED);
	}

	@Override
	public Result<List<User>> searchUsers(String pattern) {
		return Result.error( Result.ErrorCode.NOT_IMPLEMENTED);
	}


}

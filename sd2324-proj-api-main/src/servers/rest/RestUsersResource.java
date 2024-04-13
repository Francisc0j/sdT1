package servers.rest;

import jakarta.inject.Singleton;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response.Status;
import servers.java.JavaUsers;
import tukano.api.User;
import tukano.api.java.Result;
import tukano.api.java.Users;
import tukano.api.rest.RestUsers;


import java.util.List;

@Singleton
public class RestUsersResource implements RestUsers {

	final Users impl;
	public RestUsersResource() {

		this.impl = new JavaUsers();
	}
	
	@Override
	public String createUser(User user) {

		return resultOrThrow( impl.createUser( user));
	}

	@Override
	public User getUser(String userId, String pwd) {
		return resultOrThrow( impl.getUser(userId, pwd));
	}
	
	@Override
	public User updateUser(String name, String pwd, User user) {
		throw new RuntimeException("Not Implemented...");
	}

	@Override
	public User deleteUser(String name, String pwd) {
		throw new RuntimeException("Not Implemented...");
	}

	@Override
	public List<User> searchUsers(String pattern) {
		throw new RuntimeException("Not Implemented...");
	}

	/**
	 * Given a Result<T>, either returns the value, or throws the JAX-WS Exception
	 * matching the error code...
	 */
	protected <T> T resultOrThrow(Result<T> result) {
		if (result.isOK())
			return result.value();
		else
			throw new WebApplicationException(statusCodeFrom(result));
	}

	/**
	 * Translates a Result<T> to a HTTP Status code
	 */
	private static Status statusCodeFrom(Result<?> result) {
		return switch (result.error()) {
			case CONFLICT -> Status.CONFLICT;
			case NOT_FOUND -> Status.NOT_FOUND;
			case FORBIDDEN -> Status.FORBIDDEN;
			case BAD_REQUEST -> Status.BAD_REQUEST;
			case NOT_IMPLEMENTED -> Status.NOT_IMPLEMENTED;
			case OK -> result.value() == null ? Status.NO_CONTENT : Status.OK;
			default -> Status.INTERNAL_SERVER_ERROR;
		};
	}
		
}

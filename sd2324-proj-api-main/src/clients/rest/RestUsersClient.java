package clients.rest;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import org.glassfish.jersey.client.ClientConfig;
import tukano.api.User;
import tukano.api.java.Result;
import tukano.api.java.Users;
import tukano.api.rest.RestUsers;

import java.net.URI;
import java.util.List;


public class RestUsersClient implements Users {

	final URI serverURI;
	final Client client;
	final ClientConfig config;

	final WebTarget target;
	
	public RestUsersClient( URI serverURI ) {
		this.serverURI = serverURI;
		this.config = new ClientConfig();
		this.client = ClientBuilder.newClient(config);

		target = client.target( serverURI ).path( RestUsers.PATH );
	}
		
	@Override
	public Result<String> createUser(User user) {
		Response r = target.request()
				.post(Entity.entity(user, MediaType.APPLICATION_JSON));

		var status = r.getStatus();
		if( status != Status.OK.getStatusCode() )
			return Result.error( getErrorCodeFrom(status));
		else
			return Result.ok( r.readEntity( String.class ));
	}

	@Override
	public Result<User> getUser(String name, String pwd) {
		Response r = target.path( name )
				.queryParam(RestUsers.PWD, pwd).request()
				.accept(MediaType.APPLICATION_JSON)
				.get();

		var status = r.getStatus();
		if( status != Status.OK.getStatusCode() )
			return Result.error( getErrorCodeFrom(status));
		else
			return Result.ok( r.readEntity( User.class ));
	}

	@Override
	public Result<User> updateUser(String userId, String password, User user) {
		throw new RuntimeException("Not Implemented...");
	}

	@Override
	public Result<User> deleteUser(String userId, String password) {
		throw new RuntimeException("Not Implemented...");
	}

	@Override
	public Result<List<User>> searchUsers(String pattern) {
		throw new RuntimeException("Not Implemented...");
	}

	public static Result.ErrorCode getErrorCodeFrom(int status) {
		return switch (status) {
		case 200, 209 -> Result.ErrorCode.OK;
		case 409 -> Result.ErrorCode.CONFLICT;
		case 403 -> Result.ErrorCode.FORBIDDEN;
		case 404 -> Result.ErrorCode.NOT_FOUND;
		case 400 -> Result.ErrorCode.BAD_REQUEST;
		//case 500 -> Result.ErrorCode.INTERNAL_ERROR;
		case 501 -> Result.ErrorCode.NOT_IMPLEMENTED;
		default -> Result.ErrorCode.INTERNAL_ERROR;
		};
	}
}

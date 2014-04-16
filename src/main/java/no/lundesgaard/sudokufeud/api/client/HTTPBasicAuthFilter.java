package no.lundesgaard.sudokufeud.api.client;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import javax.ws.rs.core.HttpHeaders;

import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientRequest;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.filter.ClientFilter;
import com.sun.jersey.core.util.Base64;

/**
 * Client filter adding HTTP Basic Authentication header to the HTTP request, if no such header is already present using UTF-8 encoding.
 */
public final class HTTPBasicAuthFilter extends ClientFilter {

	private final String authentication;
	static private final Charset CHARACTER_SET = Charset.forName("UTF-8");

	/**
	 * Creates a new HTTP Basic Authentication filter using provided username and password credentials. This constructor allows you to avoid storing plain
	 * password value in a String variable.
	 * 
	 * @param username
	 * @param password
	 */
	public HTTPBasicAuthFilter(final String username, final byte[] password) {
		try {

			final byte[] prefix = (username + ":").getBytes(CHARACTER_SET);
			final byte[] usernamePassword = new byte[prefix.length + password.length];

			System.arraycopy(prefix, 0, usernamePassword, 0, prefix.length);
			System.arraycopy(password, 0, usernamePassword, prefix.length, password.length);

			authentication = "Basic " + new String(Base64.encode(usernamePassword), "ASCII");
		} catch (UnsupportedEncodingException ex) {
			// This should never occur
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Creates a new HTTP Basic Authentication filter using provided username and password credentials.
	 * 
	 * @param username
	 * @param password
	 */
	public HTTPBasicAuthFilter(final String username, final String password) {
		this(username, password.getBytes(CHARACTER_SET));
	}

	@Override
	public ClientResponse handle(final ClientRequest cr) throws ClientHandlerException {

		if (!cr.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
			cr.getHeaders().add(HttpHeaders.AUTHORIZATION, authentication);
		}
		return getNext().handle(cr);
	}
}

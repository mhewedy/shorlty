package mhewedy.usingspark;

import java.util.UUID;

import spark.Request;
import spark.Response;

public class Util {
	private Util() {
	}

	public static String createCookie(Request request, Response response) {
		String cookie = request.cookie(Constants.COOKIE_NAME);
		if (cookie == null) {
			cookie = UUID.randomUUID().toString();
			response.cookie(Constants.COOKIE_NAME, cookie, Integer.MAX_VALUE);
		}
		return cookie;
	}

	public static String qualifyShortUrl(Request request, String shortUrlToken) {
		return request.scheme() + "://" + request.host() + "/" + shortUrlToken;
	}
}

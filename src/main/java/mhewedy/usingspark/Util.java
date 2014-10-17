package mhewedy.usingspark;

import java.util.List;
import java.util.UUID;

import mhewedy.usingspark.data.Base64Ops;
import mhewedy.usingspark.data.Data;
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

	public static String shortenUrl(Request request, Response response,
			List<Data> dataList) {
		String url = request.queryParams("url");

		if (url != null && !url.isEmpty()) {
			String shortUrl = Base64Ops.increment();
			Data.saveURL(dataList, shortUrl, url, request.ip(),
					Util.createCookie(request, response));
			shortUrl = Util.qualifyShortUrl(request, shortUrl);
			return shortUrl;
		}
		return "";
	}
}

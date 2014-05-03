package mhewedy.usingspark.service;

import spark.Request;
import spark.Response;

public class ApiShortenService extends ShortenService {

	@Override
	public String doService(Request request, Response response) {
		System.out.println("GET /api/shorten");
		response.type("application/json");

		String shortUrl = shortenUrl(request);
		if (shortUrl != null && !shortUrl.isEmpty()) {
			return jsonResponse(shortUrl);
		}
		return jsonErrorResponse();
	}

	private String jsonResponse(String shortUrl) {
		return "{\"short_url\":\"" + shortUrl + "\"}";
	}

	private String jsonErrorResponse() {
		return "{\"short_url\":\"\",\"error\":\"invalid_url\"}";
	}

}

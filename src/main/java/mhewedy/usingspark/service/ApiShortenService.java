package mhewedy.usingspark.service;

import mhewedy.usingspark.Util;
import mhewedy.usingspark.Util.Source;
import spark.Request;
import spark.Response;

@org.springframework.stereotype.Service
public class ApiShortenService extends Service {

	@Override
	public String doService(Request request, Response response) {
		System.out.println("GET /api/shorten");
		response.type("application/json");

		String shortUrl = Util.shortenUrl(request, response, dataList, Source.API);
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

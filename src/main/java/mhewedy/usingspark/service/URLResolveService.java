package mhewedy.usingspark.service;

import mhewedy.usingspark.data.Data;
import mhewedy.usingspark.data.ParseData;
import spark.Request;
import spark.Response;

public class URLResolveService extends Service {

	@Override
	public String doService(Request request, Response response) {
		System.out.println("GET /:shortUrl");
		String shortUrl = request.params("shortUrl");

		if (shortUrl != null) {
			String originalUrl = Data.getOriginalURL(new Data[] { inMemoryData, parseData }, shortUrl);
			if (originalUrl != null) {
				System.out.println("redirect to: " + originalUrl);

				ParseData.registerUrlHits(shortUrl, request.ip());
				response.redirect(originalUrl);
			} else {
				System.err.printf("short url %s not found!\n", shortUrl);
			}
		} else {
			System.err.println("shortenUrl is null");
		}
		return "";
	}
}

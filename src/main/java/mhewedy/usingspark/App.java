package mhewedy.usingspark;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import mhewedy.usingspark.UniqueSeqGenerator.GeneratorThread;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;


public class App {

	static Map<String, String> shortenUrlMap = new HashMap<>();

	public static void main(String[] args) {
		new GeneratorThread().start();
		Spark.staticFileLocation("web");

		Spark.post(new Route("/shorten") {
			@Override
			public Object handle(Request request, Response response) {
				String url = request.queryParams("url");
				String shortUrl = Base64.getEncoder().encodeToString(
						UniqueSeqGenerator.next().getBytes(StandardCharsets.UTF_8));
				shortenUrlMap.put(shortUrl, url);
				return request.scheme() + "://" + request.host() + "/" + shortUrl;
			}
		});

		Spark.get(new Route("/:url") {
			@Override
			public Object handle(Request request, Response response) {
				String shortUrl = request.params("url"); 
				if (shortUrl != null) {
					String originalUrl = shortenUrlMap.get(shortUrl);
					if (originalUrl != null) {
						System.out.println("redirect to: " + originalUrl);
						response.redirect(originalUrl);
					} else {
						System.err.printf("short url %s not found!", shortUrl);
					}
				} else {
					System.err.println("shortenUrl is null");
				}
				return "";
			}
		});

		Spark.get(new Route("/") {
			@Override
			public Object handle(Request request, Response response) {
				response.redirect("/pages/welcome.html");
				return "";
			}
		});
	}
}

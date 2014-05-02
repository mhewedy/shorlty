package mhewedy.usingspark;

import java.util.HashMap;
import java.util.Map;

import mhewedy.usingspark.data.Data;
import mhewedy.usingspark.data.InMemoryData;
import mhewedy.usingspark.data.ParseData;
import mhewedy.usingspark.data.UniqueSeqGenerator;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

public class URLHandler {

	private static Data inMemoryData = new InMemoryData();
	private static Data parseData = new ParseData();


	public static ModelAndView handlePOSTShorten(Request request, Response response, TemplateViewRoute viewRoute) {
		System.out.println("POST /shorten");
		String url = request.queryParams("url");

		if (url != null && !url.isEmpty()) {
			String shortUrl = UniqueSeqGenerator.next();

			Data.saveURL(new Data[] { inMemoryData, parseData }, shortUrl, url, request.ip());

			shortUrl = request.scheme() + "://" + request.host() + "/" + shortUrl;
			return viewRoute.modelAndView(getObjectMap(shortUrl, null), "welcome.ftl");
		}
		return viewRoute.modelAndView(null, "welcome.ftl");
	}

	public static ModelAndView handlePOSTUnShorten(Request request, Response response, TemplateViewRoute viewRoute) {
		System.out.println("POST /unshorten");
		String url = request.queryParams("url");

		if (url != null) {
			String shortUrl = url.substring(url.lastIndexOf(request.host()) + request.host().length() + 1);
			System.out.printf("try finding originalUrl for seqNum %s \n", shortUrl);

			String originalUrl = Data.getOriginalURL(new Data[] { inMemoryData, parseData }, shortUrl);
			if (originalUrl != null) {
				return viewRoute.modelAndView(getObjectMap(null, originalUrl), "welcome.ftl");
			} else {
				System.err.println("not originalUrl found!");
			}
		}
		return viewRoute.modelAndView(null, "welcome.ftl");
	}

	public static String handleURLRedirector(Request request, Response response) {
		System.out.println("GET /:shortUrl");
		String shortUrl = request.params("shortUrl");

		if (shortUrl != null) {
			String originalUrl = Data.getOriginalURL(new Data[] { inMemoryData, parseData }, shortUrl);
			if (originalUrl != null) {
				System.out.println("redirect to: " + originalUrl);
				response.redirect(originalUrl);
			} else {
				System.err.printf("short url %s not found!\n", shortUrl);
			}
		} else {
			System.err.println("shortenUrl is null");
		}
		return "";
	}

	public static String redirectToHome(Response response) {
		response.redirect("/");
		return "";
	}

	public static ModelAndView handleHome(TemplateViewRoute viewRoute) {
		System.out.println("GET /");
		return viewRoute.modelAndView(null, "welcome.ftl");
	}

	private static Map<String, Object> getObjectMap(String shortenUrl, String originalUrl) {
		Map<String, Object> attrs = new HashMap<>();
		attrs.put("shorten_url", shortenUrl);
		attrs.put("original_url", originalUrl);
		return attrs;
	}
}

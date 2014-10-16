package mhewedy.usingspark.service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import mhewedy.usingspark.Constants;
import mhewedy.usingspark.data.Data;

import org.springframework.stereotype.Service;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

@Service
public class UnShortenService extends ModelAndViewService {

	@Override
	public ModelAndView doService(Request request, Response response, TemplateViewRoute viewRoute) {
		System.out.println("POST /unshorten");
		String url = request.queryParams("url");

		if (url != null && !url.isEmpty()) {
			String shortUrl = url.substring(url.lastIndexOf(request.host()) + request.host().length() + 1);
			System.out.printf("try finding originalUrl for seqNum %s \n", shortUrl);

			String originalUrl = Data.getOriginalURL(dataList, shortUrl);

			if ("".equals(originalUrl)) {
				originalUrl = getOriginalUrlExternalService(url);
			}
			return viewRoute.modelAndView(getObjectMap(null, originalUrl), "welcome.ftl");
		}

		Map<String, String> map = new HashMap<>();
		map.put("appname", Constants.APP_NAME);
		return viewRoute.modelAndView(map, "welcome.ftl");
	}

	private String getOriginalUrlExternalService(String shortUrl) {
		System.out.printf("getting external url %s\n", shortUrl);
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection) new URL(shortUrl).openConnection();
			connection.setInstanceFollowRedirects(false);
			System.out.println(connection.getResponseCode());
			if (connection.getResponseCode() >= 300 && connection.getResponseCode() <= 400) {
				return connection.getHeaderField("Location");
			}
		} catch (IOException e) {
			System.out.println();
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
		return "";
	}

	public static void main(String[] args) {
		System.out.println(new UnShortenService().getOriginalUrlExternalService("http://bit.ly/1rMt4WW"));
	}
}

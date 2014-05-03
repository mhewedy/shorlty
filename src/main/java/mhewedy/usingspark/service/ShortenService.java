package mhewedy.usingspark.service;

import mhewedy.usingspark.data.Base64Ops;
import mhewedy.usingspark.data.Data;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

public class ShortenService extends ModelAndViewService {

	@Override
	public ModelAndView doService(Request request, Response response, TemplateViewRoute viewRoute) {
		System.out.println("POST /shorten");
		String shortUrl = shortenUrl(request);

		if (shortUrl != null && !shortUrl.isEmpty()) {
			return viewRoute.modelAndView(getObjectMap(shortUrl, null), "welcome.ftl");
		}
		return viewRoute.modelAndView(null, "welcome.ftl");
	}

	protected String shortenUrl(Request request) {
		String url = request.queryParams("url");

		if (url != null && !url.isEmpty()) {
			String shortUrl = Base64Ops.increment();
			Data.saveURL(new Data[] { inMemoryData, parseData }, shortUrl, url, request.ip());
			shortUrl = request.scheme() + "://" + request.host() + "/" + shortUrl;
			return shortUrl;
		}
		return "";
	}

}

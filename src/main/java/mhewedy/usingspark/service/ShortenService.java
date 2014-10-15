package mhewedy.usingspark.service;

import mhewedy.usingspark.Util;
import mhewedy.usingspark.data.Base64Ops;
import mhewedy.usingspark.data.Data;

import org.springframework.stereotype.Service;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

@Service
public class ShortenService extends ModelAndViewService {

	@Override
	public ModelAndView doService(Request request, Response response, TemplateViewRoute viewRoute) {
		System.out.println("POST /shorten");
		String shortUrl = shortenUrl(request, response);

		if (shortUrl != null && !shortUrl.isEmpty()) {
			return viewRoute.modelAndView(getObjectMap(shortUrl, null), "welcome.ftl");
		}
		return viewRoute.modelAndView(null, "welcome.ftl");
	}

	protected String shortenUrl(Request request, Response response) {
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

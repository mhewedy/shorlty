package mhewedy.usingspark.service;

import mhewedy.usingspark.data.Data;
import mhewedy.usingspark.data.UniqueSeqGenerator;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

public class ShortenService extends ModelAndViewService {

	@Override
	public ModelAndView doService(Request request, Response response, TemplateViewRoute viewRoute) {
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

}

package mhewedy.usingspark.service;

import mhewedy.usingspark.Util.Source;

import java.util.HashMap;
import java.util.Map;

import mhewedy.usingspark.Constants;
import mhewedy.usingspark.Util;
import mhewedy.usingspark.aspects.ValidateRequestParamURL;

import org.springframework.stereotype.Service;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

@Service
public class ShortenService extends ModelAndViewService {

	@Override
	@ValidateRequestParamURL
	public ModelAndView doService(Request request, Response response, TemplateViewRoute viewRoute) {
		System.out.println("POST /shorten");
		String shortUrl = Util.shortenUrl(request, response, dataList, Source.WEB);

		if (shortUrl != null && !shortUrl.isEmpty()) {
			return viewRoute.modelAndView(getObjectMap(shortUrl, null), "welcome.ftl");
		}

		Map<String, String> map = new HashMap<>();
		map.put("appname", Constants.APP_NAME);
		return viewRoute.modelAndView(map, "welcome.ftl");
	}

}

package mhewedy.usingspark.service;

import java.util.HashMap;
import java.util.Map;

import mhewedy.usingspark.Constants;

import org.springframework.stereotype.Service;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

@Service
public class HomeService extends ModelAndViewService {

	@Override
	public ModelAndView doService(Request request, Response response, TemplateViewRoute viewRoute) {
		System.out.println("GET /");

		Map<String, String> map = new HashMap<>();
		map.put("appname", Constants.APP_NAME);

		return viewRoute.modelAndView(map, "welcome.ftl");
	}
}

package mhewedy.usingspark.service;

import java.util.HashMap;
import java.util.Map;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

public class ApiDocService extends ModelAndViewService {

	@Override
	public ModelAndView doService(Request request, Response response, TemplateViewRoute viewRoute) {
		Map<String, String> map = new HashMap<>();
		map.put("host", request.host());

		return viewRoute.modelAndView(map, "api.ftl");
	}
}

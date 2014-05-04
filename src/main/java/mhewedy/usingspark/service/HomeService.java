package mhewedy.usingspark.service;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

public class HomeService extends ModelAndViewService {

	@Override
	public ModelAndView doService(Request request, Response response, TemplateViewRoute viewRoute) {
		System.out.println("GET /");
		return viewRoute.modelAndView(null, "welcome.ftl");
	}
}

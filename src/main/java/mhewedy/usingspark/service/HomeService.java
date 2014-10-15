package mhewedy.usingspark.service;

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
		return viewRoute.modelAndView(null, "welcome.ftl");
	}
}

package mhewedy.usingspark.service;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

public abstract class ModelAndViewService extends Service {

	public abstract ModelAndView doService(Request request, Response response, TemplateViewRoute viewRoute);

	public String doService(Request request, Response response) {
		return "";
	}
}

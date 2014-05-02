package mhewedy.usingspark;

import mhewedy.usingspark.service.HomeService;
import mhewedy.usingspark.service.ShortenService;
import mhewedy.usingspark.service.URLResolveService;
import mhewedy.usingspark.service.UnShortenService;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

public class URLHandler {

	public static ModelAndView handlePOSTShorten(Request request, Response response, TemplateViewRoute viewRoute) {
		return new ShortenService().doService(request, response, viewRoute);
	}

	public static ModelAndView handlePOSTUnShorten(Request request, Response response, TemplateViewRoute viewRoute) {
		return new UnShortenService().doService(request, response, viewRoute);
	}

	public static String handleURLRedirector(Request request, Response response) {
		return new URLResolveService().doService(request, response);
	}

	public static String redirectToHome(Response response) {
		response.redirect("/");
		return "";
	}

	public static ModelAndView handleHome(TemplateViewRoute viewRoute) {
		return new HomeService().doService(null, null, viewRoute);
	}
}

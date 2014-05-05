package mhewedy.usingspark;

import mhewedy.usingspark.data.DataBootstraper;
import mhewedy.usingspark.service.ApiDocService;
import mhewedy.usingspark.service.ApiShortenService;
import mhewedy.usingspark.service.HomeService;
import mhewedy.usingspark.service.RecentShortenService;
import mhewedy.usingspark.service.ShortenService;
import mhewedy.usingspark.service.URLResolveService;
import mhewedy.usingspark.service.UnShortenService;

import org.parse4j.Parse;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;
import spark.template.freemarker.FreeMarkerRoute;

public class App {

	public static void main(String[] args) {

		init();

		Spark.post(new FreeMarkerRoute("/shorten") {
			@Override
			public ModelAndView handle(Request request, Response response) {
				return new ShortenService().doService(request, response, this);
			}
		});

		Spark.post(new FreeMarkerRoute("/unshorten") {
			@Override
			public ModelAndView handle(Request request, Response response) {
				return new UnShortenService().doService(request, response, this);
			}
		});

		Spark.get(new Route("/shorten") {
			@Override
			public Object handle(Request request, Response response) {
				response.redirect("/");
				return "";
			}
		});

		Spark.get(new Route("/unshorten") {
			@Override
			public Object handle(Request request, Response response) {
				response.redirect("/");
				return "";
			}
		});

		Spark.get(new FreeMarkerRoute("/apidoc") {
			@Override
			public ModelAndView handle(Request request, Response response) {
				return new ApiDocService().doService(request, response, this);
			}
		});

		Spark.get(new Route("/api/shorten") {
			@Override
			public Object handle(Request request, Response response) {
				return new ApiShortenService().doService(request, response);
			}
		});

		Spark.get(new Route("/recent") {
			@Override
			public Object handle(Request request, Response response) {
				return new RecentShortenService().doService(request, response);
			}
		});

		Spark.get(new Route("/:shortUrl") {
			@Override
			public Object handle(Request request, Response response) {
				return new URLResolveService().doService(request, response);
			}
		});

		Spark.get(new FreeMarkerRoute("/") {
			@Override
			public ModelAndView handle(Request request, Response response) {
				return new HomeService().doService(request, response, this);
			}
		});
	}

	private static void init() {
		try {
			Spark.setPort(Integer.parseInt(System.getenv("PORT")));
			Parse.initialize("S0ryVHKLgL3MII7OmnIRSvzQkCkAAMtvc6BrCKQS", "iG1oZ8OTNBpIJeLzOz6Oj8sS4y5c2J6Pbd6hEsH2");
			DataBootstraper.bootstrap();
			Spark.staticFileLocation("web");
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(-1);
		}
	}
}

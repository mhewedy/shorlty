package mhewedy.usingspark;

import mhewedy.usingspark.data.ParseData;
import mhewedy.usingspark.data.UniqueSeqGenerator.GeneratorThread;

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
				return URLHandler.handlePOSTShorten(request, response, this);
			}
		});

		Spark.post(new FreeMarkerRoute("/unshorten") {

			@Override
			public ModelAndView handle(Request request, Response response) {
				return URLHandler.handlePOSTUnShorten(request, response, this);
			}
		});

		Spark.get(new Route("/shorten") {
			@Override
			public Object handle(Request request, Response response) {
				return URLHandler.redirectToHome(response);
			}
		});

		Spark.get(new Route("/unshorten") {
			@Override
			public Object handle(Request request, Response response) {
				return URLHandler.redirectToHome(response);
			}
		});

		Spark.get(new Route("/:shortUrl") {
			@Override
			public Object handle(Request request, Response response) {
				return URLHandler.handleURLRedirector(request, response);
			}
		});

		Spark.get(new FreeMarkerRoute("/") {
			@Override
			public ModelAndView handle(Request request, Response response) {
				return URLHandler.handleHome(this);
			}
		});
	}

	private static void init() {
		Spark.setPort(Integer.parseInt(System.getenv("PORT")));
		Parse.initialize("S0ryVHKLgL3MII7OmnIRSvzQkCkAAMtvc6BrCKQS", "iG1oZ8OTNBpIJeLzOz6Oj8sS4y5c2J6Pbd6hEsH2");
		new GeneratorThread().start();
		ParseData.copyToMemory();
		Spark.staticFileLocation("web");
	}
}

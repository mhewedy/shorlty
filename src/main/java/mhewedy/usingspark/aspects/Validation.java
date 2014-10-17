package mhewedy.usingspark.aspects;

import java.util.HashMap;
import java.util.Map;

import mhewedy.usingspark.Constants;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import spark.ModelAndView;
import spark.Request;
import spark.TemplateViewRoute;

@Aspect
@Component
public class Validation {

	@Around("@annotation(mhewedy.usingspark.aspects.ValidateRequestParamURL)")
	public ModelAndView validateURL(ProceedingJoinPoint joinPoint) {
		Request request = (Request) joinPoint.getArgs()[0];
		TemplateViewRoute viewRoute = (TemplateViewRoute) joinPoint.getArgs()[2];

		String url = request.queryParams("url");
		System.out.println("validating URL: " + url);

		if (url != null && url.startsWith("http")) {
			try {
				return (ModelAndView) joinPoint.proceed(joinPoint.getArgs());
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		// default just go to show home page
		Map<String, String> map = new HashMap<>();
		map.put("appname", Constants.APP_NAME);
		return viewRoute.modelAndView(map, "welcome.ftl");
	}

}

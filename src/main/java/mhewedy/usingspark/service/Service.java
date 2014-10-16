package mhewedy.usingspark.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import mhewedy.usingspark.Constants;
import mhewedy.usingspark.data.Data;
import spark.Request;
import spark.Response;

import com.google.gson.Gson;

public abstract class Service {

	@Inject
	protected List<Data> dataList;

	protected static Gson GSON = new Gson();

	public abstract String doService(Request request, Response response);

	protected static Map<String, Object> getObjectMap(String shortenUrl, String originalUrl) {
		Map<String, Object> attrs = new HashMap<>();
		attrs.put("appname", Constants.APP_NAME);
		attrs.put("shorten_url", shortenUrl);
		attrs.put("original_url", originalUrl);
		return attrs;
	}
}

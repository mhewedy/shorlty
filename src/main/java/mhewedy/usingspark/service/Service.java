package mhewedy.usingspark.service;

import java.util.HashMap;
import java.util.Map;

import mhewedy.usingspark.data.Data;
import mhewedy.usingspark.data.InMemoryData;
import mhewedy.usingspark.data.ParseData;
import spark.Request;
import spark.Response;

import com.google.gson.Gson;

public abstract class Service {

	protected static Data inMemoryData = new InMemoryData();
	protected static Data parseData = new ParseData();

	protected static Gson GSON = new Gson();

	public abstract String doService(Request request, Response response);

	protected static Map<String, Object> getObjectMap(String shortenUrl, String originalUrl) {
		Map<String, Object> attrs = new HashMap<>();
		attrs.put("shorten_url", shortenUrl);
		attrs.put("original_url", originalUrl);
		return attrs;
	}
}

package mhewedy.usingspark.service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.parse4j.ParseObject;

import spark.Request;
import spark.Response;

public abstract class JsonListService extends Service {

	private Function<ParseObject, Map<String, Object>> objectMapping;

	@Override
	public final String doService(Request request, Response response) {
		response.type("application/json");
		this.objectMapping = getObjectMapping(request);
		return GSON.toJson(convertToDto(request, doList(request, response)));
	}
	
	public abstract Function<ParseObject, Map<String, Object>> getObjectMapping(
			Request request);

	public abstract List<ParseObject> doList(Request request, Response response);

	private List<Map<String, Object>> convertToDto(Request request, List<ParseObject> list) {
		return list.stream().parallel().map(objectMapping)
				.collect(Collectors.toList());
	}

}

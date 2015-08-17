package mhewedy.usingspark.service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.parse4j.ParseObject;

import spark.Request;
import spark.Response;

public abstract class JsonListService extends JsonService<List<?>> {

	private Function<ParseObject, Map<String, Object>> objectMapping;
	
	public abstract Function<ParseObject, Map<String, Object>> getObjectMapping(
			Request request);

	public abstract List<ParseObject> doList(Request request, Response response);

	private List<Map<String, Object>> convertToDto(Request request, List<ParseObject> list) {
		return list.stream().parallel().map(objectMapping)
				.collect(Collectors.toList());
	}

	@Override
	public List<?> getObject(Request request, Response response) {
		this.objectMapping = getObjectMapping(request);
		return convertToDto(request, doList(request, response));
	}
}

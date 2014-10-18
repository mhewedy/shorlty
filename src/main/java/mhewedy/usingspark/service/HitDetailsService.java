package mhewedy.usingspark.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import mhewedy.usingspark.data.Columns;

import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;

import spark.Request;
import spark.Response;

@org.springframework.stereotype.Service
public class HitDetailsService extends JsonListService {

	@Override
	public List<ParseObject> doList(Request request, Response response) {
		String objectId = request.queryParams("objectId");

		if (objectId != null && !objectId.isEmpty()) {
			return getHitDetails(objectId);
		} else {
			return new ArrayList<ParseObject>();
		}
	}

	@Override
	public Function<ParseObject, Map<String, Object>> getObjectMapping(
			Request request) {
		return Columns.HIT_DETAILS_ROW_MAPPING;
	}
	
	private List<ParseObject> getHitDetails(String objectId) {
		List<ParseObject> list = null;
		try {
			ParseObject urlMapping = new ParseObject(Columns.URL_MAPPING_CLASS);
			urlMapping.setObjectId(objectId);

			ParseQuery<ParseObject> query = ParseQuery.getQuery(Columns.HIT_DETAILS_CLASS)
					.whereEqualTo(Columns.URL_REL_COL, urlMapping)
					.orderByAscending(Columns.CREATED_AT);
			query.limit(20);
			
			list = query.find();
		} catch (ParseException e) {
			System.err.println(e.getMessage());
		}

		if (list == null) {
			list = new ArrayList<>();
		}
		return list;
	}

}

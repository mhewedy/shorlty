package mhewedy.usingspark.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import mhewedy.usingspark.Util;
import mhewedy.usingspark.data.Columns;

import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;

import spark.Request;
import spark.Response;

@org.springframework.stereotype.Service
public class RecentShortenService extends JsonListService {

	@Override
	public List<ParseObject> doList(Request request, Response response) {
		System.out.println("GET /recent");

		String cookie = Util.createCookie(request, response);
		return getRecent(cookie);
	}

	@Override
	public Function<ParseObject, Map<String, Object>> getObjectMapping(
			Request request) {
		return Columns.URL_MAPPING_ROW_MAPPING;
	}

	private List<ParseObject> getRecent(String cookie) {
		List<ParseObject> list = null;
		try {
			ParseQuery<ParseObject> query = ParseQuery.getQuery(Columns.URL_MAPPING_CLASS)
					.whereEqualTo(Columns.OWNER_ID_COL, cookie).orderByDescending(Columns.CREATED_AT);
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

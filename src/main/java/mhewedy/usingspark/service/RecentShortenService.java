package mhewedy.usingspark.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import mhewedy.usingspark.Util;
import mhewedy.usingspark.data.Columns;

import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;

import spark.Request;
import spark.Response;

public class RecentShortenService extends Service {

	@Override
	public String doService(Request request, Response response) {
		System.out.println("GET /recent");
		response.type("application/json");
		String cookie = Util.createCookie(request, response);

		List<ParseObject> list = getRecent(cookie);
		return GSON.toJson(convertToDto(request, list));
	}

	private List<Map<String, Object>> convertToDto(Request request, List<ParseObject> list) {

		return list.stream().parallel().map(o -> {
			Map<String, Object> map = new HashMap<>();
			map.put(Columns.ORIG_URL_COL, o.get(Columns.ORIG_URL_COL));
			map.put(Columns.CREATED_AT, o.getCreatedAt());
			map.put(Columns.SHORT_URL_COL, Util.qualifyShortUrl(request, (String) o.get(Columns.SHORT_URL_COL)));
			map.put(Columns.HIT_COUNT_COL, o.getInt(Columns.HIT_COUNT_COL));
			return map;
		}).collect(Collectors.toList());
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

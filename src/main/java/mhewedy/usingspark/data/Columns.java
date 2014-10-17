package mhewedy.usingspark.data;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.parse4j.ParseObject;

public interface Columns {
	String URL_MAPPING_CLASS = "URLMapping";
	String HIT_DETAILS_CLASS = "HitDetails";

	String SHORT_URL_COL = "shortUrl";
	String ORIG_URL_COL = "originalUrl";
	String IP_COL = "ip";
	String OWNER_ID_COL = "owner_id";

	String HIT_COUNT_COL = "hitCount";
	String URL_REL_COL = "urlObj";

	String CREATED_AT = "createdAt";

	String OBJECT_ID = "objectId";
	
	public static Function<ParseObject, Map<String, Object>> URL_MAPPING_ROW_MAPPING = o -> {
		Map<String, Object> map = new HashMap<>();
		map.put(Columns.OBJECT_ID, o.getObjectId());
		map.put(Columns.ORIG_URL_COL, o.get(Columns.ORIG_URL_COL));
		map.put(Columns.CREATED_AT, o.getCreatedAt());
		map.put(Columns.SHORT_URL_COL, o.get(Columns.SHORT_URL_COL));
		map.put(Columns.HIT_COUNT_COL, o.getInt(Columns.HIT_COUNT_COL));
		return map;
	};
}

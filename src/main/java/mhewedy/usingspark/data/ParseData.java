package mhewedy.usingspark.data;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;
import org.parse4j.callback.FindCallback;
import org.parse4j.callback.SaveCallback;

public class ParseData implements Data {

	private static final String URL_MAPPING_CLASS = "URLMapping";
	private static final String HIT_DETAILS_CLASS = "HitDetails";

	private static final String SHORT_URL_COL = "shortUrl";
	private static final String ORIG_URL_COL = "originalUrl";
	private static final String IP_COL = "ip";

	private static final String HIT_COUNT_COL = "hitCount";
	private static final String URL_REL_COL = "urlObj";

	@Override
	public void saveURL(String shortUrl, String originalUrl, String clientIp) {

		ParseObject parseObject = new ParseObject(URL_MAPPING_CLASS);
		parseObject.put(SHORT_URL_COL, shortUrl);
		parseObject.put(ORIG_URL_COL, originalUrl);
		parseObject.put(IP_COL, clientIp);
		parseObject.saveInBackground(new SaveCallback() {
			@Override
			public void done(ParseException parseException) {
				if (parseException != null) {
					System.out.printf("error saving original %s short %s in Parse.com: %s\n", originalUrl, shortUrl,
							parseException.getMessage());
				}
			}
		});
	}

	@Override
	public String getOriginalURL(String shortUrl) {
		ParseQuery<ParseObject> query = ParseQuery.getQuery(URL_MAPPING_CLASS);
		try {
			List<ParseObject> list = query.whereEqualTo(SHORT_URL_COL, shortUrl).find();
			if (list != null) {
				return (String) list.stream().map(o -> o.get(ORIG_URL_COL)).findFirst().orElse("");
			}
		} catch (ParseException e) {
			System.err.println(e.getMessage());
		}
		return "";
	}

	@Override
	public int getPriority() {
		return 10;
	}

	public static void copyToMemory() {
		ParseQuery<ParseObject> query = ParseQuery.getQuery(URL_MAPPING_CLASS);

		try {
			List<ParseObject> list = query.find();

			if (list != null) {
				System.out.println("copying data from Parse.com to memory");

				Map<String, String> mapBackup = list.stream().collect(
						Collectors.toMap(o -> (String) o.get(SHORT_URL_COL), o -> (String) o.get(ORIG_URL_COL)));
				InMemoryData.copyFrom(mapBackup);
			} else {
				System.out.println("no data to copy from Parse.com to memory");
			}
		} catch (ParseException e) {
			System.err.println("cannot restore Parse.com records!");
			throw new RuntimeException(e);
		}
	}

	public static void registerUrlHits(String shortUrl, String ip) {

		ParseQuery<ParseObject> query = ParseQuery.getQuery(URL_MAPPING_CLASS).whereEqualTo(SHORT_URL_COL, shortUrl);
		query.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> list, ParseException parseException) {

				if (parseException == null && list != null) {
					ParseObject parseObject = list.get(0);

					ParseObject hitsObj = new ParseObject(HIT_DETAILS_CLASS);
					hitsObj.put(IP_COL, ip);
					hitsObj.put(URL_REL_COL, parseObject);
					hitsObj.saveInBackground();

					parseObject.increment(HIT_COUNT_COL);
					parseObject.saveInBackground();
				}
			}
		});
	}
}

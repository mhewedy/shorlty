package mhewedy.usingspark.data;

import java.util.List;

import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;
import org.parse4j.callback.SaveCallback;

// TODO: should completely encapsulate Parse.com APIs, so if need change the datastore, it should be pretty simple, so instead of PraseData it should be PersistedData
public class ParseData implements Data {

	@Override
	public void saveURL(String shortUrl, String originalUrl, String clientIp, String cookie) {

		ParseObject parseObject = new ParseObject(Columns.URL_MAPPING_CLASS);
		parseObject.put(Columns.SHORT_URL_COL, shortUrl);
		parseObject.put(Columns.ORIG_URL_COL, originalUrl);
		parseObject.put(Columns.IP_COL, clientIp);
		parseObject.put(Columns.OWNER_ID_COL, cookie);
        
		parseObject.saveInBackground(new SaveCallback() {
			@Override
			public void done(ParseException parseException) {
				if (parseException != null) {
					System.err.printf("fetal error saving original %s short %s in Parse.com: %s\n", originalUrl,
							shortUrl, parseException.getMessage());
				}
			}
		});
	}

	@Override
	public String getOriginalURL(String shortUrl) {
		System.out.println("get original url from db");

		ParseQuery<ParseObject> query = ParseQuery.getQuery(Columns.URL_MAPPING_CLASS);
		try {
			List<ParseObject> list = query.whereEqualTo(Columns.SHORT_URL_COL, shortUrl).find();
			if (list != null) {
				String origUrl = list.stream().map(o -> o.getString(Columns.ORIG_URL_COL)).findFirst().orElse("");
				InMemoryData.saveData(shortUrl, origUrl);
				return origUrl;
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
}

package mhewedy.usingspark.data;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public interface Data {

	int getPriority();

	void saveURL(String shortUrl, String originalUrl, String clientIp, String cookie);

	String getOriginalURL(String shortUrl);

	static void saveURL(List<Data> dataList, String shortUrl,
			String originalUrl, String clientIp, String cookie) {
		if (dataList != null) {
			dataList.stream().forEach(
					d -> d.saveURL(shortUrl, originalUrl, clientIp, cookie));
		}
	}

	static String getOriginalURL(List<Data> dataList, String shortUrl) {
		if (dataList != null) {
			Collections.sort(dataList,
					Comparator.comparing(d -> d.getPriority()));

			return dataList.stream()
				.map(d -> d.getOriginalURL(shortUrl))
				.filter(u -> u != null && !u.isEmpty())
				.findFirst().orElse("");
		}
		return "";
	}
}

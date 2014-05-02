package mhewedy.usingspark.data;

import java.util.Arrays;
import java.util.Comparator;

public interface Data {

	int getPriority();

	void saveURL(String shortUrl, String originalUrl, String clientIp);

	String getOriginalURL(String shortUrl);

	static void saveURL(Data[] dataArray, String shortUrl, String originalUrl, String clientIp) {
		if (dataArray != null) {
			Arrays.stream(dataArray).forEach(d -> d.saveURL(shortUrl, originalUrl, clientIp));
		}
	}

	static String getOriginalURL(Data[] dataArray, String shortUrl) {
		if (dataArray != null) {
			Arrays.sort(dataArray, Comparator.comparing(d -> d.getPriority()));
			return Arrays.stream(dataArray)
				.map(d -> d.getOriginalURL(shortUrl))
				.filter(u -> u != null && !u.isEmpty())
				.findFirst().orElse("");
		}
		return "";
	}
}

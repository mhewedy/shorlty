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
			for (Data data : dataArray) {
				String url = data.getOriginalURL(shortUrl);
				if (url != null && !url.isEmpty()) {
					System.out.println("Getting original Url from: " + data);
					return url;
				}
			}
		}
		return "";
	}
}

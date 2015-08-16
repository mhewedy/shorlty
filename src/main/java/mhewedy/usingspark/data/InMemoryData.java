package mhewedy.usingspark.data;

import mhewedy.usingspark.Util.Source;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class InMemoryData implements Data {

	private static Map<String, String> shortenUrlMap = new HashMap<>();

	@Override
	public void saveURL(String shortUrl, String originalUrl, String clientIp, String cookie, Source source) {
		shortenUrlMap.put(shortUrl, originalUrl);
	}

	@Override
	public String getOriginalURL(String shortUrl) {
		System.out.println("get original url from memory");
		return shortenUrlMap.get(shortUrl);
	}

	@Override
	public int getPriority() {
		return 1;
	}

	static void saveData(String shortUrl, String originalUrl) {
		System.out.printf("saving ref for %s\n", shortUrl);
		shortenUrlMap.put(shortUrl, originalUrl);
	}
}

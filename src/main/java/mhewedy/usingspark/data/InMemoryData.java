package mhewedy.usingspark.data;

import java.util.HashMap;
import java.util.Map;

public class InMemoryData implements Data {

	private static Map<String, String> shortenUrlMap = new HashMap<>();

	@Override
	public void saveURL(String shortUrl, String originalUrl, String clientIp) {
		shortenUrlMap.put(shortUrl, originalUrl);
	}

	@Override
	public String getOriginalURL(String shortUrl) {
		return shortenUrlMap.get(shortUrl);
	}

	@Override
	public int getPriority() {
		return 1;
	}

	public static void copyFrom(Map<String, String> map) {
		shortenUrlMap.putAll(map);
	}

	static boolean contains(String key) {
		return shortenUrlMap.keySet().contains(key);
	}
}

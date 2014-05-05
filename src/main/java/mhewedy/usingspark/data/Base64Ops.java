package mhewedy.usingspark.data;

import java.util.List;
import java.util.stream.Collectors;

public class Base64Ops {
	static final String allChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+";

	private static String currentVal = "";

	public static synchronized String increment() {
		if (currentVal == "") {
			currentVal = String.valueOf(allChars.charAt(0));
		} else {
			List<Character> chars = currentVal.chars().mapToObj(i -> (char) i).collect(Collectors.toList());
			add(chars, chars.size() - 1);
			currentVal = chars.stream().map(c -> String.valueOf(c)).collect(Collectors.joining());
		}
		return currentVal;
	}

	private static void add(List<Character> chars, int i) {
		int index = allChars.indexOf(chars.get(i));

		if (index < allChars.length() - 1) {
			chars.set(i, allChars.charAt(index + 1));
		} else {
			chars.set(i, allChars.charAt(0));
			if (i != 0) {
				add(chars, i - 1);
			} else {
				chars.add(0, allChars.charAt(0));
			}
		}
	}

	static void resotreCurrentVal(String cValue) {
		currentVal = cValue;
	}
}

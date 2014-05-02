package mhewedy.usingspark.data;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class UniqueSeqGenerator {

	// the following string takes long time to generate the unique values from,
	// but it provides 62^5
	// private static String CHARS =
	// "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHUJKLMNOPQRSTUVWXYZ";
	private static String CHARS = "abcdefghijklmnopqrst";

	private static int[] counter = new int[5];

	private static final List<String> UNIQUE_SEQ = new ArrayList<>();
	private static int nextIndex = 0;

	private static void getNextValue() {
		final int charLength = CHARS.length();

		for (int a = 0; a < charLength; a++) {
			counter[0] = counter[0] == charLength ? 1 : counter[0] + 1;
			for (int b = 0; b < charLength; b++) {
				counter[1] = counter[1] == charLength ? 1 : counter[1] + 1;
				for (int c = 0; c < charLength; c++) {
					counter[2] = counter[2] == charLength ? 1 : counter[2] + 1;
					for (int d = 0; d < charLength; d++) {
						counter[3] = counter[3] == charLength ? 1 : counter[3] + 1;
						for (int e = 0; e < charLength; e++) {
							counter[4] = counter[4] == charLength ? 1 : counter[4] + 1;
							UNIQUE_SEQ.add(composeString());
						}
					}
				}
			}
		}
		System.out.printf("Finish generating %d unique numbers\n", UNIQUE_SEQ.size());
	}

	private static String composeString() {
		String s = "";
		for (int i = 0; i < 5; i++) {
			s += CHARS.charAt(counter[i] - 1);
		}
		return s;
	}

	public synchronized static String next() {
		String ustring = null;
		while (InMemoryData.contains(ustring = base64(UNIQUE_SEQ.get(nextIndex++))))
			;
		return ustring;
	}

	private static String base64(String no) {
		return Base64.getEncoder().encodeToString(no.getBytes(StandardCharsets.UTF_8));
	}

	public static class GeneratorThread extends Thread {
		@Override
		public void run() {
			getNextValue();
		}
	}
}

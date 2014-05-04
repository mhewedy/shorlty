package mhewedy.usingspark.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;

public class DataBootstraper {
	/**
	 * Should call on server start only!
	 */
	public static void bootstrap() {
		System.out.println("start bootstraping....");

		List<ParseObject> list = getAllData();

		if (list != null && !list.isEmpty()) {
			if (assertNoDuplicateInShortUrl(list)) {
				String maxShortUrlValue = list.get(0).getString(Columns.SHORT_URL_COL);
				Base64Ops.resotreCurrentVal(maxShortUrlValue);

				System.out.println("max shourt_url value is " + maxShortUrlValue);

				copyData(list);

				System.out.println("done data bootstraping..");
			} else {
				throw new RuntimeException("problem loading data ... exiting..");
			}

		} else {
			System.out.println("data bootstrap => no data to operate on");
		}
	}

	private static boolean assertNoDuplicateInShortUrl(List<ParseObject> list) {
		System.out.println("start validating duplicate short_url values");

		int actualDataCount = 0;
		int distinctDataCount = 0;
		try {
			List<String> listOfUniqueShortUrl = list.stream().map(o -> o.getString(Columns.SHORT_URL_COL)).distinct()
					.collect(Collectors.toList());

			actualDataCount = ParseQuery.getQuery(Columns.URL_MAPPING_CLASS).count();
			distinctDataCount = listOfUniqueShortUrl.size();

			if (actualDataCount == distinctDataCount) {
				System.out.println("short_url data validated successfully");
				return true;
			}
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
		}

		System.out.printf(
				"duplicate shortUrls .. Actual object count is %d, distinct objects based on shortUrl count is %d\n",
				actualDataCount, distinctDataCount);
		System.out.flush();
		return false;

	}

	private static List<ParseObject> getAllData() {
		System.out.println("loading all data from db...");

		ParseQuery<ParseObject> query = ParseQuery.getQuery(Columns.URL_MAPPING_CLASS).orderByDescending(
				Columns.CREATED_AT);

		List<ParseObject> list = new ArrayList<>();
		try {
			final int skip = 1000;

			List<ParseObject> subList = null;
			query.limit(skip);

			int count = 1;
			while ((subList = query.find()) != null) {
				list.addAll(subList);
				query.skip(skip * count++);
			}
		} catch (Exception ex) {
			System.err.println("cannot restore Parse.com records!");
			throw new RuntimeException(ex);
		}
		System.out.printf("done loading %d items\n", list.size());

		System.out.println("Sorting descending by \"createAt\" ....");
		// list.stream().sorted(Comparator.comparing((ParseObject o) ->
		// o.getDate(Columns.CREATED_AT)).reversed());
		list.stream().sorted((o1, o2) -> o2.getDate(Columns.CREATED_AT).compareTo(o1.getDate(Columns.CREATED_AT)));

		System.out.println("done sorting");

		return list;
	}

	private static void copyData(List<ParseObject> list) {
		Map<String, String> mapBackup = list.stream()
				.collect(
						Collectors.toMap(o -> (String) o.get(Columns.SHORT_URL_COL),
								o -> (String) o.get(Columns.ORIG_URL_COL)));
		InMemoryData.copyFrom(mapBackup);
	}
}

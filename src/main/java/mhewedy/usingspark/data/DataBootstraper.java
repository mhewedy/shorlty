package mhewedy.usingspark.data;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;

public class DataBootstraper {
	/**
	 * Should call on server start only!
	 */
	public static void bootstrap() {
		ParseQuery<ParseObject> query = ParseQuery.getQuery(Columns.URL_MAPPING_CLASS).orderByDescending(
				Columns.CREATED_AT);
		try {
			List<ParseObject> list = query.find();

			if (list != null && !list.isEmpty()) {
				Base64Ops.resotreCurrentVal(list.get(0).getString(Columns.SHORT_URL_COL));
				copyData(list);

				System.out.println("done data bootstraping..");
			} else {
				System.out.println("data bootstrap => no data to operate on");
			}
		} catch (ParseException e) {
			System.err.println("cannot restore Parse.com records!");
			throw new RuntimeException(e);
		}
	}

	private static void copyData(List<ParseObject> list) {
		Map<String, String> mapBackup = list.stream()
				.collect(
						Collectors.toMap(o -> (String) o.get(Columns.SHORT_URL_COL),
								o -> (String) o.get(Columns.ORIG_URL_COL)));
		InMemoryData.copyFrom(mapBackup);
	}
}

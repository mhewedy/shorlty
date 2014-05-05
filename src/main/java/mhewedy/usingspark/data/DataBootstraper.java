package mhewedy.usingspark.data;

import java.util.List;

import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;

public class DataBootstraper {
	/**
	 * Should call on server start only!
	 * 
	 * @throws ParseException
	 */
	public static void bootstrap() throws ParseException {
		System.out.println("start bootstraping....");

		List<ParseObject> list = ParseQuery.getQuery(Columns.URL_MAPPING_CLASS).orderByDescending(Columns.CREATED_AT)
				.find();
		if (list != null && !list.isEmpty()) {
			String maxIncrValue = list.get(0).getString(Columns.SHORT_URL_COL);
			Base64Ops.resotreCurrentVal(maxIncrValue);
			System.out.println("max value " + maxIncrValue);
			System.out.println("done data bootstraping..");
		} else {
			System.out.println("data bootstrap => no data to operate on");
		}
	}
}

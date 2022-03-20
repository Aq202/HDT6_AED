package HDT6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class MapFactory {


	public static Map<String,ArrayList> getMap(int type) throws IllegalArgumentException {

		switch (type) {
		case 1:
			return new HashMap<>();
		case 2:
			return new TreeMap<>();
		case 3:
			return new LinkedHashMap<>();
		default:
			throw new IllegalArgumentException();
		}
	}
}

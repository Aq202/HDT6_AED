package HDT6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Clase MapFactory
 * @author diego
 * Programado el 20/03/2022
 */


public class MapFactory {


	/**
	 * Metodo factory
	 * @param type
	 * @return Map
	 * @throws IllegalArgumentException 
	 */
	public static Map<String,Product> getMap(int type) throws IllegalArgumentException {

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

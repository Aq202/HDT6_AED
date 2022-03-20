package Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import HDT6.MarketController;
import HDT6.NonExistentCategoryException;

class MarketTests {
	
	private MarketController market;
	
	public MarketTests() {
		try {
			market = new MarketController(1);
		} catch (Exception e) {
			fail("Error: " + e);
		} 
	}

	@Test
	void test() {
		
		String rawString = "Lácteos";
		ByteBuffer buffer = StandardCharsets.UTF_8.encode(rawString); 

		String utf8EncodedString = StandardCharsets.UTF_8.decode(buffer).toString();
		System.out.println("Lácteos");
		System.out.println(utf8EncodedString);

		assertEquals(rawString, utf8EncodedString);
	}

}

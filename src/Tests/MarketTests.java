package Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import HDT6.MarketController;
import HDT6.NonExistentException;

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
	void newProductTest() {
		
		try {

		} catch (Exception e) {
			fail(e);
		} 
	}
	
	@Test
	void getProductCategory() {
		
		try {
			market.addProductToUserCollection("Lácteos", "Leche deslactosada");
			market.getUserCollectionProducts();
		} catch (NonExistentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}

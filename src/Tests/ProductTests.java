package Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import HDT6.Product;

class ProductTests {

	@Test
	void test() {
		ArrayList<Product> products = new ArrayList<>();
		
		products.add(new Product("Proteinas", "Carne de res"));
		products.add(new Product("L�cteos", "Leche"));
		
		
		assertEquals(true, products.contains(new Product("L�cteos", "Leche")));
		assertEquals(1, products.indexOf(new Product("L�cteos", "Leche")));
	}

}

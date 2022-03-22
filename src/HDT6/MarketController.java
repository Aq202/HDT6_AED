package HDT6;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class MarketController {

	private final ArrayList<String> validCategories;
	private Map<String, Product> productsMap, userCollectionMap;

	public MarketController(int mapType)
			throws IllegalArgumentException, FileNotFoundException, IOException, NonExistentException {

		validCategories = new ArrayList<>();
		productsMap = MapFactory.getMap(mapType);
		userCollectionMap = MapFactory.getMap(mapType);
		fillProductData();

	}

	private void fillProductData() throws FileNotFoundException, IOException, NonExistentException {

		String[] productsRow = FileController.readFile();

		for (String product : productsRow) {

			// category, description
			String[] productData = product.split("\\|");
			if (!validCategories.contains(productData[0].toLowerCase().trim()))
				validCategories.add(productData[0].toLowerCase().trim()); // anadir categoria valida
			addProductToMap(productsMap, productData[0].trim(), productData[1].trim());
		}
	}

	private void addProductToMap(Map<String, Product> map, String category, String description)
			throws NonExistentException {

		Product product = new Product(category, description);

		// si el producto no existe, agregarlo
		if (!map.containsValue(product)) {

			int cont = 0;
			while (true) {

				if (!map.containsKey(getKey(category, cont))) {
					map.put(getKey(category, cont), product);
					break;
				} else
					cont++;

			}
		} else { // si existe, aumentar cantidad
			Product p = getProduct(map, category, description);
			if(p != null) p.increaseAmount();
		}

	}

	public void addProductToUserCollection(String category, String product) throws NonExistentException {
		
		category = category.trim().toLowerCase();
		product = product.trim().toLowerCase();

		Product productObj = getProduct(productsMap, category, product);

		// anadir producto a la coleccion del ususario
		if (productObj == null)
			throw new NonExistentException(
					"El producto ingresado no existe. \nPrueba con: " + getCategoryProducts(category));
		else
			addProductToMap(userCollectionMap, category, product);

	}

	private Product getProduct(Map<String, Product> map, String category, String product) throws NonExistentException {

		validateCategory(category);
		int cont = 0;

		// buscar objeto del producto
		while (map.containsKey(getKey(category, cont))) {
			Product productObj = map.get(category.toLowerCase() + String.valueOf(cont));
			if (productObj.getDescription().equalsIgnoreCase(product))
				return productObj;
			cont++;
		}

		return null; // no existe
	}

	private void validateCategory(String category) throws NonExistentException {
		if (!validCategories.contains(category.toLowerCase()))
			throw new NonExistentException("La categoria " + category + " no existe.\nPrueba con: " + getCategories());
	}

	public String getProductCategory(String product) {

		for (Product productObj : productsMap.values()) {

			if (productObj.getDescription().equalsIgnoreCase(product))
				return productObj.getCategory();
		}

		return null; // no existe

	}

	public String getUserCollectionProducts() {

		String result = "";
		int cont = 0;
		for (Product product : userCollectionMap.values()) {
			cont++;
			result += String.format(
					"Producto %s:\n\t- Categoria: %s\n\t- Descripcion: %s\n\t- Cantidad seleccionada: %s\n\n", cont,
					product.getCategory(), product.getDescription(), product.getAmount());

		}

		if (cont == 0)
			result = "La coleccion se encuentra vacia.";
		return result;

	}

	public String getUserCollectionProductsByType() {

		String result = "";
		int category_cont = 0;
		for (String category : validCategories) {
			
			int product_cont = 0;
			while (userCollectionMap.containsKey(getKey(category, product_cont))) {
				
				//titulo de categoria
				if(product_cont == 0) {
					category_cont++;
					result += String.format("%s. %s\n\n", category_cont, category);
					
				}

				Product product = userCollectionMap.get(getKey(category, product_cont));

				result += String.format("\t- Categoria: %s\n\t- Descripcion: %s\n\t- Cantidad disponible: %s\n\n",
						product.getCategory(), product.getDescription(), product.getAmount());
				product_cont++;

			}
		}

		if (category_cont == 0)
			result = "La coleccion se encuentra vacia.";
		return result;

	}

	public String getFullInventory() {

		String result = "";
		int cont = 0;

		for (Product product : productsMap.values()) {
			cont++;
			result += String.format("Producto %s:\n\t- Categoria: %s\n\t- Descripcion: %s\n\n", cont,
					product.getCategory(), product.getDescription());
		}

		if (cont == 0)
			result = "El inventario se encuentra vacio.";
		return result;

	}

	public String getCategories() {
		String result = "";
		for (String category : validCategories) {
			result += category + ". ";
		}
		return result;
	}

	public String getCategoryProducts(String category) {
		String result = "";
		System.out.println(category);
		int product_cont = 0;
		while (productsMap.containsKey(getKey(category, product_cont))) {

			Product product = productsMap.get(getKey(category, product_cont));
			result += product.getDescription() + ". ";
			product_cont++;
		}
		return result;
	}
	
	public String getKey(String category, int productCont) {
		return category.toLowerCase().trim() + String.valueOf(productCont);
	}

	public String getFullInventoryByType() {

		String result = "";
		int category_cont = 0;
		for (String category : validCategories) {

			category_cont++;
			result += String.format("%s. %s\n\n", category_cont, category);

			int product_cont = 0;
			while (productsMap.containsKey(getKey(category, product_cont))) {

				Product product = productsMap.get(getKey(category, product_cont));

				result += String.format("\t- Categoria: %s\n\t- Descripcion: %s\n\n",
						product.getCategory(), product.getDescription(), product.getAmount());
				product_cont++;

			}


		}

		if (category_cont == 0)
			result = "La coleccion se encuentra vacia.";
		return result;

	}

	public Map<String, Product> getMap() {
		return productsMap;
	}

}

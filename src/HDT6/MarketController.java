package HDT6;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class MarketController {

	private Map<String, ArrayList<Product>> productsMap, userCollectionMap;

	public MarketController(int mapType)
			throws IllegalArgumentException, FileNotFoundException, IOException, NonExistentException {

		productsMap = MapFactory.getMap(mapType);
		userCollectionMap = MapFactory.getMap(mapType);
		fillProductData();

	}

	private void fillProductData() throws FileNotFoundException, IOException, NonExistentException {

		String[] productsRow = FileController.readFile();

		for (String product : productsRow) {

			// category, description
			String[] productData = product.split("\\|");
			addProductToMap(productsMap, productData[0].trim(), productData[1].trim());
		}
	}

	private void addProductToMap(Map<String, ArrayList<Product>> map, String category, String description)
			throws NonExistentException {

		if (!map.containsKey(category.toLowerCase()))
			map.put(category.toLowerCase(), new ArrayList<>());

		ArrayList<Product> list = map.get(category.toLowerCase());
		Product product = new Product(category, description);
		int index = list.indexOf(product);

		if (index >= 0)
			list.get(index).increaseAmount(); // incrementar cantidad del producto
		else
			list.add(product); // nuevo producto

	}

	public void addProductToUserCollection(String category, String product) throws NonExistentException {

		Product productObj = getProduct(productsMap, category, product);

		// anadir producto a la coleccion del ususario
		if (productObj == null)
			throw new NonExistentException(
					"El producto ingresado no existe. \nPrueba con: " + getCategoryProducts(category));
		else
			addProductToMap(userCollectionMap, category, product);

	}

	private Product getProduct(Map<String, ArrayList<Product>> map, String category, String product)
			throws NonExistentException {

		if (!productsMap.containsKey(category.toLowerCase()))
			throw new NonExistentException("La categoria " + category + " no existe.\nPrueba con: "+getCategories());

		var productList = productsMap.get(category.toLowerCase());
		int index = productList.indexOf(new Product(null, product));

		if (index < 0)
			return null;

		return productList.get(index);
	}

	public String getProductCategory(String product) {

		for (String category : productsMap.keySet()) {
			var list = productsMap.get(category);
			int index = list.indexOf(new Product(null, product));

			if (index >= 0)
				return list.get(index).getCategory();

		}

		return null;

	}

	public String getUserCollectionProducts() {

		String result = "";
		int cont = 0;
		for (String category : userCollectionMap.keySet()) {
			var list = userCollectionMap.get(category);

			for (Product product : list) {
				cont++;
				result += String.format(
						"Producto %s:\n\t- Categoria: %s\n\t- Descripcion: %s\n\t- Cantidad disponible: %s\n\n", cont,
						product.getCategory(), product.getDescription(), product.getAmount());

			}
		}

		if (cont == 0)
			result = "La coleccion se encuentra vacia.";
		return result;

	}

	public String getUserCollectionProductsByType() {

		String result = "";
		int category_cont = 0;
		for (String category : userCollectionMap.keySet()) {

			category_cont++;
			result += String.format("%s. %s\n\n", category_cont, category);
			var list = userCollectionMap.get(category);

			for (Product product : list) {
				result += String.format("\t- Categoria: %s\n\t- Descripcion: %s\n\t- Cantidad disponible: %s\n\n",
						product.getCategory(), product.getDescription(), product.getAmount());

			}
		}

		if (category_cont == 0)
			result = "La coleccion se encuentra vacia.";
		return result;

	}

	public String getFullInventory() {

		String result = "";
		int cont = 0;
		for (String category : productsMap.keySet()) {
			var list = productsMap.get(category);

			for (Product product : list) {
				cont++;
				result += String.format("Producto %s:\n\t- Categoria: %s\n\t- Descripcion: %s\n\n", cont,
						product.getCategory(), product.getDescription());

			}
		}

		if (cont == 0)
			result = "El inventario se encuentra vacio.";
		return result;

	}

	public String getCategories() {
		String result = "";
		for (String category : productsMap.keySet()) {
			result += category + ". ";
		}
		return result;
	}

	public String getCategoryProducts(String category) {
		String result = "";
		System.out.println(category);
		for (Product product : productsMap.get(category.toLowerCase())) {
			result += product.getDescription() + ". ";
		}
		return result;
	}

	public String getFullInventoryByType() {

		String result = "";
		int category_cont = 0;
		for (String category : productsMap.keySet()) {

			category_cont++;
			result += String.format("%s. %s\n\n", category_cont, category);
			var list = productsMap.get(category);

			for (Product product : list) {
				result += String.format("\t- Categoria: %s\n\t- Descripcion: %s\n\n", product.getCategory(),
						product.getDescription());

			}
		}

		if (category_cont == 0)
			result = "La coleccion se encuentra vacia.";
		return result;

	}

	public Map<String, ArrayList<Product>> getMap() {
		return productsMap;
	}

}

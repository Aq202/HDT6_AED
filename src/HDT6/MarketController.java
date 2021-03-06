package HDT6;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * Clase MarketController
 * @author diego
 * Programado el 20/03/2022
 */


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

	/**
	 * Se encarga de obtener la informacion del archivo e ingresarlo al programa.
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws NonExistentException
	 */
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

	/**
	 * Se encarga de agregar productos nuevos a un hash deteminado
	 * @param map
	 * @param category
	 * @param description
	 * @throws NonExistentException
	 */
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

	/**
	 * Se encarga de anadir un nuevo producto a la coleccion del usuario.
	 * @param category
	 * @param product
	 * @throws NonExistentException
	 */
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

	/**
	 * Se encarga de obtener el objeto de un producto con categoria y descripcion determinado.
	 * @param map
	 * @param category
	 * @param product
	 * @return Product
	 * @throws NonExistentException
	 */
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

	/**
	 * Se encarga de validar si una categoria es considerada como valida.
	 * @param category
	 * @throws NonExistentException
	 */
	private void validateCategory(String category) throws NonExistentException {
		if (!validCategories.contains(category.toLowerCase()))
			throw new NonExistentException("La categoria " + category + " no existe.\nPrueba con: " + getCategories());
	}

	/**
	 * Se encarg de obtener la categoria correspondiente a un producto.
	 * @param product
	 * @return
	 */
	public String getProductCategory(String product) {

		for (Product productObj : productsMap.values()) {

			if (productObj.getDescription().equalsIgnoreCase(product))
				return productObj.getCategory();
		}

		return null; // no existe

	}

	/**
	 * Se encarga de obtener todos los productos de la coleccion del usuario.
	 * @return String
	 */
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

	/**
	 * Se encarga de obtener todos los productos de la coleccion del usuario ordenados por tipo.
	 * @return String.
	 */
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

	/**
	 * Se encarga de obtener todos los productos del inventario.
	 * @return String.
	 */
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

	/**
	 * Retorna las categorias disponbles.
	 * @return
	 */
	public String getCategories() {
		String result = "";
		for (String category : validCategories) {
			result += category + ". ";
		}
		return result;
	}

	/**
	 * Se encarga de obtener los productos de una categoria especifica.
	 * @param category
	 * @return
	 */
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
	
	/**
	 * Retorna la llave en formato requerido.
	 * @param category
	 * @param productCont
	 * @return
	 */
	public String getKey(String category, int productCont) {
		return category.toLowerCase().trim() + String.valueOf(productCont);
	}

	/**
	 * Obtiene todos los productos del inventario ordenados por tipo.
	 * @return
	 */
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


}

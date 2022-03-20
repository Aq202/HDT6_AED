package HDT6;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class MarketController {

	private Map<String, ArrayList<Product>> productsMap;
	private final String[] validCategories = { "Mueble de terraza", "Sillones de masaje", "Bebidas", "Condimentos",
			"Frutas", "Carnes", "Lácteos" };

	public MarketController(int mapType)
			throws IllegalArgumentException, FileNotFoundException, IOException, NonExistentCategoryException {

		productsMap = MapFactory.getMap(mapType);
		createProductCategories();
		fillProductData();

	}

	private void createProductCategories() {

		for (String category : validCategories) {
			productsMap.put(category, new ArrayList<>());
		}
	}

	private void fillProductData() throws FileNotFoundException, IOException, NonExistentCategoryException {

		String[] productsRow = FileController.readFile();

		for (String product : productsRow) {
			System.out.println(product);
			// category, description
			String[] productData = product.split("\\|");
			addProductToMap(productData[0].trim(), productData[1].trim());
		}
	}

	private void addProductToMap(String category, String description) throws NonExistentCategoryException {
		
		System.out.println(category + " ; " + description);
		if (!productsMap.containsKey(category))
			throw new NonExistentCategoryException("La categoria "+category+" no existe.");

		ArrayList<Product> list = productsMap.get(category);
		Product product = new Product(category, description);
		int index = list.indexOf(product);

		if (index >= 0)
			list.get(index).increaseAmount(); // incrementar cantidad del producto
		else
			list.add(product); // nuevo producto

	}

	public String[] getValidCategories() {
		return validCategories;
	}
	
	public Map<String, ArrayList<Product>> getMap(){
		return productsMap;
	}

}

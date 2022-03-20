package HDT6;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

	private static int getValidInt(Scanner sc, String message, boolean absoluteValues, Integer... validValues) {

		while (true) {

			try {

				System.out.println(message);
				int value = sc.nextInt();
				sc.nextLine();

				if (Arrays.asList(validValues).contains(value) || validValues.length == 0)
					return absoluteValues ? Math.abs(value) : value;
				else
					System.out.println("Por favor, ingresa un numero valido");

			} catch (Exception ex) {
				System.out.println("Por favor, ingresa un numero valido");
				sc.nextLine();
			}
		}
	}

	private static String getString(Scanner sc, String message) {
		System.out.println(message);
		return sc.nextLine();
	}

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		int mapOption = getValidInt(sc, """
				\nSelecciona la implementacion de Map a utilizar:
					1. HashMap
					2. TreeMap
					3. LinkedHashMap
				""", true, 1, 2, 3);

		try {
			boolean stop = false;
			MarketController market = new MarketController(mapOption);

			while (!stop) {

				try {

					int menuOption = getValidInt(sc, """
							\nSelecciona la implementacion de Map a utilizar:
								1. Agregar producto a la coleccion.
								2. Mostrar la categoria de un producto.
								3. Mostrar productos en la coleccion.
								4. Mostrar productos en la coleccion por tipo.
								5. Mostrar productos en el inventario.
								6. Mostrar productos en el inventario por tipo.
								7. Salir.
							""", true, 1, 2, 3, 4, 5, 6,7);

					switch (menuOption) {

					case 1: {

						String category = getString(sc, "Ingresar categoria del producto:"),
								product = getString(sc, "Ingresar el producto a agregar:");

						market.addProductToUserCollection(category, product);
						System.out.println("Producto agregado a la coleccion exitosamente!");

						break;
					}

					case 2: {
						String product = getString(sc, "Ingresar producto: ");
						String category = market.getProductCategory(product);

						if (category == null)
							System.out.println("El producto no existe.");
						else
							System.out.println("La categoria del producto es: " + category);
						break;
					}

					case 3: {
						System.out.println("***** Productos en la coleccion *****\n");
						System.out.println(market.getUserCollectionProducts());
						break;
					}

					case 4: {
						System.out.println("***** Productos en la coleccion por tipo *****\n");
						System.out.println(market.getUserCollectionProductsByType());
						break;
					}

					case 5: {
						System.out.println("***** Productos en el inventario *****\n");
						System.out.println(market.getFullInventory());
						break;
					}

					case 6: {
						System.out.println("***** Productos en el inventario por tipo *****\n");
						System.out.println(market.getFullInventoryByType());
						break;
					}

					case 7:
						System.out.println("Hasta pronto!");
						stop = true;
						break;

					}
				} catch (NonExistentException ex) {
					System.out.println("\n" + ex.getMessage());
				}
			}

		} catch (NonExistentException ex) {
			System.out.println("\n" + ex.getMessage());
		} catch (Exception ex) {
			System.out.println("Ocurrio un error." + ex);
		}

	}

}

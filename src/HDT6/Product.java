package HDT6;

/**
 * Clase Product
 * @author diego
 * Programado el 20/03/2022
 */


public class Product {

	private String category, description;
	private int amount = 1;

	public Product(String category, String description) {
		setCategory(category);
		setDescription(description);
	}

	/**
	 * Metodo get para la propiedad category.
	 * @return String
	 */
	public String getCategory() {
		return category;
	}
	
	/**
	 * Metodo set para category
	 * @param category
	 */
	public void setCategory(String category) {
		this.category = category != null ? category.trim() : null;
	}

	/**
	 * Metodo get para la propiedad description
	 * @return String
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Metodo set para description
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description != null ? description.trim() : null;
	}

	/**
	 * Incrementa la cantidad de productos seleccionados.
	 */
	public void increaseAmount() {
		amount++;
	}


	/**
	 * Metodo get para amount
	 * @return Amount.
	 */
	public int getAmount() {
		return amount;
	}

	@Override
	public boolean equals(Object object) {

		if (object instanceof Product) {

			Product product = (Product) object;
			if (product.getDescription().equalsIgnoreCase(description)
					&& product.getCategory().equalsIgnoreCase(category))
				return true;

		}

		return false;
	}

}

package HDT6;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class Product {

	private String category, description;
	private int amount = 1;

	public Product(String category, String description) {
		setCategory(category);
		setDescription(description);
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category != null ? category.trim() : null;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description != null ? description.trim() : null;
	}
	

	
	public void increaseAmount() {
		amount++;
	}
	
	public void decreaseAmount() {
		amount--;
		if(amount < 0) amount = 0;
	}
	
	public int getAmount() {
		return amount;
	}

	@Override
	public boolean equals(Object object) {

		if (object instanceof Product) {

			Product product = (Product) object;
			if (product.getDescription().toLowerCase().equalsIgnoreCase(description.toLowerCase()))
				return true;

		}

		return false;
	}

}
